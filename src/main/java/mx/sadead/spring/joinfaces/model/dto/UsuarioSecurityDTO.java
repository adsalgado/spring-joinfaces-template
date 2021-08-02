package mx.sadead.spring.joinfaces.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UsuarioSecurityDTO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
    private String paterno;
    private String materno;
    private String email;
    private String userName;
    private String password;
    
}
