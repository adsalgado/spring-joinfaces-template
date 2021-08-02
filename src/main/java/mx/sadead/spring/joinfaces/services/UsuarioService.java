package mx.sadead.spring.joinfaces.services;

import java.util.List;

import mx.sadead.spring.joinfaces.model.Usuario;
import mx.sadead.spring.joinfaces.model.UsuarioRol;

public interface UsuarioService extends BaseService<Usuario, Long> {
	Usuario findOneByUserName(String username);
	List<UsuarioRol> getRolesByUsername(String userName);
}
