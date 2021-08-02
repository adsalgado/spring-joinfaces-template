package mx.sadead.spring.joinfaces.config.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import mx.sadead.spring.joinfaces.model.Transaccion;
import mx.sadead.spring.joinfaces.model.dto.UsuarioSecurityDTO;
import mx.sadead.spring.joinfaces.services.TransaccionService;

/**
 *
 * @author asalgado
 */
public class SSUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger LOGGER = LoggerFactory.getLogger(SSUserDetails.class);
    
    private TransaccionService transaccionService;
    
    private final UsuarioSecurityDTO user;
    private final List<GrantedAuthority> grantedAuthorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    
    public SSUserDetails(UsuarioSecurityDTO usuario, List<GrantedAuthority> grantedAuthorities) {
        this.user = usuario;
        this.grantedAuthorities = grantedAuthorities;
    }
    
    public SSUserDetails(UsuarioSecurityDTO usuario, List<GrantedAuthority> grantedAuthorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this(usuario, grantedAuthorities);
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    SSUserDetails(UsuarioSecurityDTO usuario, List<GrantedAuthority> authorities, TransaccionService transaccionService, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this(usuario, authorities, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);
        this.transaccionService = transaccionService;
    }
        
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public UsuarioSecurityDTO getUser() {
        return user;
    }

    public void setTransaccionService(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }
    
    public void saveTransaccion(final String claveTransaccion, final String detalle) {
        Transaccion tw = new Transaccion();
        tw.setFechaTransaccion(new Date());
        tw.setUserName(this.getUsername());
        tw.setClaveTransaccion(claveTransaccion);
        tw.setDetalle(detalle);
        tw.setAplicacionId(0);
        transaccionService.save(tw);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SSUserDetails other = (SSUserDetails) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }
    
}
