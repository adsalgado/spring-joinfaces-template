package mx.sadead.spring.joinfaces.repositories;

import java.util.List;

import mx.sadead.spring.joinfaces.model.Permiso;

public interface PermisoRepository extends BaseRepository<Permiso, Long> {

	List<Permiso> findAllByRolId(Long rolId);

	List<Permiso> findAllByOpcionId(Long opcionId);

}
