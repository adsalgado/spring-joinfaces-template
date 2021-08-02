package mx.sadead.spring.joinfaces.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import mx.sadead.spring.joinfaces.model.UsuarioRol;

public interface UsuarioRolRepository extends BaseRepository<UsuarioRol, Long> {
	
	@Query("select ur from UsuarioRol ur where ur.usuario.userName = ?1")
	List<UsuarioRol> findByUserName(String userName);
	
}
