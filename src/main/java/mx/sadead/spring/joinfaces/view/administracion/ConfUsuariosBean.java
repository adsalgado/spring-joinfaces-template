package mx.sadead.spring.joinfaces.view.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import mx.sadead.spring.joinfaces.config.security.SSUserDetails;
import mx.sadead.spring.joinfaces.model.Rol;
import mx.sadead.spring.joinfaces.model.Usuario;
import mx.sadead.spring.joinfaces.model.UsuarioRol;
import mx.sadead.spring.joinfaces.services.RolService;
import mx.sadead.spring.joinfaces.services.UsuarioRolService;
import mx.sadead.spring.joinfaces.services.UsuarioService;
import mx.sadead.spring.joinfaces.view.util.PFMessages;

/**
 *
 * @author Adrián Salgado
 */
@Setter
@Getter
@Component
@ViewScoped
public class ConfUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfUsuariosBean.class);

	private final UsuarioService usuarioService;
	private final RolService rolService;
	private final UsuarioRolService usuarioRolService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private List<Usuario> currents;
	private Usuario current;
	private Usuario selectedCurrent;

	private DualListModel<String> dualRoles;
	private List<String> sourceRoles;
	private List<String> targetRoles;
	private List<Rol> roles;
	private Map<String, Rol> mpRoles;

	private String confirmPassword;
	private boolean showPnlConsulta;
	private boolean showPnlModificacion;
	private String tipoActualizacion;

	private SSUserDetails userDetails;

	public ConfUsuariosBean(UsuarioService usuarioService, RolService rolService, UsuarioRolService usuarioRolService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.usuarioService = usuarioService;
		this.rolService = rolService;
		this.usuarioRolService = usuarioRolService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PostConstruct
	public void init() {
		userDetails = (SSUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		showPanelConsulta();
		search();
		current = new Usuario();
		roles = rolService.findAll();
		mpRoles = new HashMap<>();
		for (Rol role : roles) {
			mpRoles.put(role.getClaveRol(), role);
		}
		initDualList();
	}

	private void initDualList() {
		if (current != null) {
			if (current.getId() == null) {
				sourceRoles = new ArrayList<>();
				for (Rol role : roles) {
					sourceRoles.add(role.getClaveRol());
				}
				targetRoles = new ArrayList<>();
				dualRoles = new DualListModel<>(sourceRoles, targetRoles);
			} else {
				List<UsuarioRol> ur = usuarioService.getRolesByUsername(current.getUserName());
				targetRoles = new ArrayList<>();
				for (UsuarioRol usuarioRol : ur) {
					targetRoles.add(usuarioRol.getRol().getClaveRol());
				}
				sourceRoles = new ArrayList<>();
				for (Rol r : roles) {
					if (!targetRoles.contains(r.getClaveRol())) {
						sourceRoles.add(r.getClaveRol());
					}
				}
				dualRoles = new DualListModel<>(sourceRoles, targetRoles);
			}
		}
	}

	public void search() {
		currents = usuarioService.findAll();
	}

	public void onEditUser() {
		tipoActualizacion = "update";
		initDualList();
		showPanelModificacion();
	}

	public void onCreateUser() {
		current = new Usuario();
		tipoActualizacion = "create";
		initDualList();
		showPanelModificacion();
	}

	public void create() {

		try {
			current.setPassword(bCryptPasswordEncoder.encode(current.getPassword()));
			current.setFechaAlta(new Date());
			current.setEstatusId(1L);

			List<Long> idRoles = new ArrayList<>();
			for (String cveRol : dualRoles.getTarget()) {
				if (mpRoles.containsKey(cveRol)) {
					idRoles.add(mpRoles.get(cveRol).getId());
				}
			}
			usuarioService.saveUsuarioPerfiles(current, idRoles);

			/**
			 * * TRANSACTION LOGS **
			 */
			StringBuilder transactionLogs = new StringBuilder("[");
			transactionLogs.append("Id: ").append(current.getId());
			transactionLogs.append(", Username: ").append(current.getUserName());
			transactionLogs.append(", Roles: ").append(idRoles);
			transactionLogs.append("]");
			userDetails.saveTransaccion("ALTA_USUARIO", transactionLogs.toString());

			search();
			showPanelConsulta();
			PFMessages.showMessageInfo("LA OPERACIÓN SE REALIZÓ CORRECTAMENTE.");

		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al ejecutar la operación. ", e);
			PFMessages.showMessageErrorBackEnd("Ocurrió un error al ejecutar la operación. ");

		}

	}

	public void update() {
		try {
			current.setFechaUltModificacion(new Date());

			List<Long> idRoles = new ArrayList<>();
			for (String cveRol : dualRoles.getTarget()) {
				if (mpRoles.containsKey(cveRol)) {
					idRoles.add(mpRoles.get(cveRol).getId());
				}
			}
			usuarioService.saveUsuarioPerfiles(current, idRoles);

			/**
			 * * TRANSACTION LOGS **
			 */
			StringBuilder transactionLogs = new StringBuilder("[");
			transactionLogs.append("Id: ").append(current.getId());
			transactionLogs.append(", Username: ").append(current.getUserName());
			transactionLogs.append(", Roles: ").append(idRoles);
			transactionLogs.append("]");
			userDetails.saveTransaccion("MODIFICACION_USUARIO", transactionLogs.toString());

			search();
			showPanelConsulta();
			PFMessages.showMessageInfo("LA OPERACIÓN SE REALIZÓ CORRECTAMENTE.");

		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al ejecutar la operación. ", e);
			PFMessages.showMessageErrorBackEnd("Ocurrió un error al ejecutar la operación. ");

		}

	}

	public void delete() {

		try {
			current.setActivo(0);
			current.setFechaUltModificacion(new Date());
			usuarioService.update(selectedCurrent);

			search();
			showPanelConsulta();
			PFMessages.showMessageInfo("LA OPERACIÓN SE REALIZÓ CORRECTAMENTE.");

		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al ejecutar la operación. ", e);
			PFMessages.showMessageErrorBackEnd("Ocurrió un error al ejecutar la operación. ");

		}

	}

	public void showPanelConsulta() {
		showPnlConsulta = true;
		showPnlModificacion = false;
	}

	public void showPanelModificacion() {
		showPnlModificacion = true;
		showPnlConsulta = false;
	}

}
