package mx.sadead.spring.joinfaces.services;

import java.util.List;

import mx.sadead.spring.joinfaces.model.Opcion;

public interface OpcionService extends BaseService<Opcion, Long> {

	List<Opcion> findByOpcionIdIsNullOrderByOrden();

	void saveOpcionPerfiles(Opcion opcion, List<Long> idRoles);

}
