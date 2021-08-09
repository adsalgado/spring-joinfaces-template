package mx.sadead.spring.joinfaces.session;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserSessionContext {

	boolean isUserAuthenticated();
	UserDetails getUserDetails();
	String getNombreUsuario();
	void keepSessionAlive();
	boolean hasAnyRol(String...roles);
	
}
