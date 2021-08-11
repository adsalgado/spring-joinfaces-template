package mx.sadead.spring.joinfaces.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import mx.sadead.spring.joinfaces.model.Opcion;

public interface OpcionRepository extends BaseRepository<Opcion, Long> {

	@Query(value
            = "SELECT	distinct op.*\n"
            + "FROM	cfg_opcion op\n"
            + "INNER JOIN cfg_permiso pe\n"
            + "ON pe.opcion_id = op.id\n"
            + "\n"
            + "WHERE pe.rol_id IN (?1)\n"
            + "ORDER BY  op.orden", nativeQuery = true)
    List<Opcion> findOpcionesByRolesId(List<Long> roles);

    List<Opcion> findByOpcionIdIsNullOrderByOrden();

}
