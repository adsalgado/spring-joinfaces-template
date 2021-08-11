package mx.sadead.spring.joinfaces.services;

import java.util.List;

import mx.sadead.spring.joinfaces.model.Permiso;

public interface PermisoService extends BaseService<Permiso, Long> {

	List<Permiso> findByRolId(Long rolId);

}
