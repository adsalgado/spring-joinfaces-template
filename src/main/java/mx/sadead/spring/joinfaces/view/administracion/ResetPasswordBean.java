package mx.sadead.spring.joinfaces.view.administracion;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import mx.sadead.spring.joinfaces.config.security.SSUserDetails;
import mx.sadead.spring.joinfaces.model.Usuario;
import mx.sadead.spring.joinfaces.services.UsuarioService;
import mx.sadead.spring.joinfaces.view.util.PFMessages;

/**
 *
 * @author Adrián Salgado D.
 */
@Setter
@Getter
@Component
@ViewScoped
public class ResetPasswordBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordBean.class);

    private final UsuarioService usuarioService;    
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private String username;
    private Usuario current;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String tipoActualizacion;

    protected SSUserDetails userDetails;

	public ResetPasswordBean(UsuarioService usuarioService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usuarioService = usuarioService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
    @PostConstruct
    public void init() {
        userDetails = (SSUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tipoActualizacion = "create";
    }

    public void search() {
        current = usuarioService.findOneByUserName(username);
        if (current == null) {
            PFMessages.showMessageError("No se encontró el usuario con clave: '" + username + "'");
            cleanSearch();
        }

    }

    public void cleanSearch() {
        username = null;
        current = null;
    }

    public void update() {
        try {

            if (StringUtils.isBlank(newPassword)) {
                throw new Exception("Field password is required.");
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new Exception("Passwords do not match.");
            }

            current.setPassword(bCryptPasswordEncoder.encode(newPassword));
            current.setNoIntentos(0);
            current.setCuentaBloqueada("F");
            current.setFechaUltModificacion(new Date());

            usuarioService.save(current);
            cleanSearch();
            PFMessages.showMessageInfo("LA OPERACION SE REALIZO CORRECTAMENTE.");

        } catch (Exception e) {
            LOGGER.error("Ocurri\u00f3 un error al guardar el registro. {0}", e);
            PFMessages.showMessageErrorBackEnd("Ocurrió un error al guardar el registro. ");

        }

    }

}
