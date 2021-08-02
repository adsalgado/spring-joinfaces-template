package mx.sadead.spring.joinfaces.config.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import mx.sadead.spring.joinfaces.model.Usuario;
import mx.sadead.spring.joinfaces.model.UsuarioRol;
import mx.sadead.spring.joinfaces.model.dto.UsuarioSecurityDTO;
import mx.sadead.spring.joinfaces.services.TransaccionService;
import mx.sadead.spring.joinfaces.services.UsuarioService;

/**
 *
 * @author Adrián Salgado
 */
@Component
public class SSUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SSUserDetailsService.class);

	@Autowired
	private final UsuarioService usuarioService;

	@Autowired
	private final TransaccionService transaccionService;

	public SSUserDetailsService(UsuarioService usuarioService, TransaccionService transaccionService) {
		super();
		this.usuarioService = usuarioService;
		this.transaccionService = transaccionService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			Usuario usuario = usuarioService.findOneByUserName(username);
			if (usuario == null) {
				LOGGER.info("user not found with the provided username");
				throw new UsernameNotFoundException("User not found");
			}
			LOGGER.info(" user from username " + usuario.getUserName());

			UsuarioSecurityDTO usuarioSecurityDTO = new UsuarioSecurityDTO();
			BeanUtils.copyProperties(usuario, usuarioSecurityDTO);

			boolean accountNonExpired = true;
			boolean accountNonLocked = true;
			boolean credentialsNonExpired = true;
			boolean enabled = true;

			return new SSUserDetails(usuarioSecurityDTO, getAuthorities(usuario), transaccionService, accountNonExpired,
					accountNonLocked, credentialsNonExpired, enabled);

		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}

	}

	private List<GrantedAuthority> getAuthorities(Usuario user) {
		List<UsuarioRol> lur = usuarioService.getRolesByUsername(user.getUserName());
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UsuarioRol ur : lur) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ur.getRol().getClaveRol());
			authorities.add(grantedAuthority);
		}
		LOGGER.info("user authorities are " + authorities.toString());
		return authorities;
	}

}
