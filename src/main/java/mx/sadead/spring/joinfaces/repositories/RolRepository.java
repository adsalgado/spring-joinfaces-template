package mx.sadead.spring.joinfaces.repositories;

import java.util.Optional;

import mx.sadead.spring.joinfaces.model.Rol;

public interface RolRepository extends BaseRepository<Rol, Long> {
	Optional<Rol> findByClaveRol(String claveRol);
}
