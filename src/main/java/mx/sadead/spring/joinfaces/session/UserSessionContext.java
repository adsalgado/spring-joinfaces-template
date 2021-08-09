package mx.sadead.spring.joinfaces.session;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import mx.sadead.spring.joinfaces.config.security.SSUserDetails;

@Named
@SessionScoped
public class UserSessionContext implements IUserSessionContext {

	private SSUserDetails userDetails;

	@PostConstruct
	public void init() {
		validateUserSession();
	}

	private void validateUserSession() {

		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof SSUserDetails) {
			userDetails = (SSUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} else {
			userDetails = null;
		}

	}

	@Override
	public boolean isUserAuthenticated() {
		if (userDetails == null) {
			validateUserSession();			
		}
		return userDetails != null;
	}

	@Override
	public UserDetails getUserDetails() {
		if (userDetails == null) {
			validateUserSession();			
		}
		return userDetails;
	}

	@Override
	public String getNombreUsuario() {
		if (userDetails == null) {
			validateUserSession();			
		}
		if (userDetails != null) {
			return userDetails.getUser().getNombre() + " " + userDetails.getUser().getPaterno() + " "
					+ userDetails.getUser().getMaterno();
		} else {
			return "";
		}
	}

	@Override
	public void keepSessionAlive() {
		// do nothing
	}

	@Override
	public boolean hasAnyRol(String... roles) {
		if (userDetails == null) {
			validateUserSession();			
		}
		Long count = 0L;
		if (userDetails != null) {
			count = userDetails.getAuthorities().stream().filter(auth -> {
				for (int i = 0; i < roles.length; i++) {
					if (auth.getAuthority().equals(roles[i])) {
						return true;
					}
				}
				return false;
			}).count();
		}
		return (count > 0);
	}
	

}
