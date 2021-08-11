package mx.sadead.spring.joinfaces.view.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import mx.sadead.spring.joinfaces.model.Opcion;
import mx.sadead.spring.joinfaces.model.Rol;
import mx.sadead.spring.joinfaces.services.OpcionService;
import mx.sadead.spring.joinfaces.services.RolService;
import mx.sadead.spring.joinfaces.view.util.PFMessages;

/**
 *
 * @author Adrián Salgado D.
 */
@Setter
@Getter
@Component
@ViewScoped
public class ConfOpcionesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordBean.class);

    private final OpcionService opcionService;
    private final RolService rolService;

    private boolean showPnlConsulta;
    private boolean showPnlModificacion;
    private String tipoActualizacion;

    private List<Opcion> currents;
    private List<Opcion> listaMenu;
    private Opcion current;
    private Opcion filterCurrent;
    private Opcion selectedCurrent;

    private List<Rol> listaPerfiles;
    private List<String> selectedPerfiles;
    private Map<String, Rol> mpRoles;


	public ConfOpcionesBean(OpcionService opcionService, RolService rolService) {
		super();
		this.opcionService = opcionService;
		this.rolService = rolService;
	}
	
    @PostConstruct
    public void init() {
        showPanelConsulta();
        search();
        current = new Opcion();
        filterCurrent = new Opcion();
        currents = opcionService.findAll();
        listaMenu = opcionService.findByOpcionIdIsNullOrderByOrden();
        listaPerfiles = rolService.findAll();
        mpRoles = new HashMap<>();
        for (Rol role : listaPerfiles) {
            mpRoles.put(role.getClaveRol(), role);
        }
    }

    public void showPanelConsulta() {
        showPnlConsulta = true;
        showPnlModificacion = false;
    }

    public void search() {
        currents = opcionService.findAll();
    }

    public void onCreateOpcion() {
        current = new Opcion();
        tipoActualizacion = "create";
        showPanelModificacion();
    }

    public void showPanelModificacion() {
        showPnlModificacion = true;
        showPnlConsulta = false;
    }

    public void create() {

        try {

            List<Long> idRoles = new ArrayList<>();
            for (String cveRol : selectedPerfiles) {
                if (mpRoles.containsKey(cveRol)) {
                    idRoles.add(mpRoles.get(cveRol).getId());
                }
            }

            opcionService.saveOpcionPerfiles(current, idRoles);

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
            opcionService.update(current);
            search();
            showPanelConsulta();
            PFMessages.showMessageInfo("LA OPERACIÓN SE REALIZÓ CORRECTAMENTE.");

        } catch (Exception e) {
            LOGGER.error("Ocurrió un error al ejecutar la operación. ", e);
            PFMessages.showMessageErrorBackEnd("Ocurrió un error al ejecutar la operación. ");

        }

    }

    public void onEditUser() {
        tipoActualizacion = "update";
        showPanelModificacion();
    }

    public void delete() {

        try {
            opcionService.deleteById(selectedCurrent.getId());

            search();
            showPanelConsulta();
            PFMessages.showMessageInfo("LA OPERACIÓN SE REALIZÓ CORRECTAMENTE.");

        } catch (Exception e) {
            LOGGER.error("Ocurrió un error al ejecutar la operación. ", e);
            PFMessages.showMessageErrorBackEnd("Ocurrió un error al ejecutar la operación. ");

        }

    }

}
