package mx.sadead.spring.joinfaces.services;

import mx.sadead.spring.joinfaces.model.Usuario;

public interface UsuarioService extends BaseService<Usuario, Long> {
	Usuario findOneByUserName(String username);
}
