package mx.sadead.spring.joinfaces.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import mx.sadead.spring.joinfaces.model.Permiso;
import mx.sadead.spring.joinfaces.repositories.PermisoRepository;
import mx.sadead.spring.joinfaces.services.PermisoService;

@Service
public class PermisoServiceImpl extends BaseServiceImpl<Permiso, Long> implements PermisoService {

	private final PermisoRepository permisoRepository;

	public PermisoServiceImpl(PermisoRepository permisoRepository) {
		this.permisoRepository = permisoRepository;
	}

	@Override
	public List<Permiso> findByRolId(Long rolId) {
		return permisoRepository.findAllByRolId(rolId);
	}

}
