package mx.sadead.spring.joinfaces.repositories;

import java.util.Optional;

import mx.sadead.spring.joinfaces.model.Usuario;

public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
	Optional<Usuario> findOneByUserName(String username);
}
