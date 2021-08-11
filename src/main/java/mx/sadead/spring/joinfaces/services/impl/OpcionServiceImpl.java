package mx.sadead.spring.joinfaces.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import mx.sadead.spring.joinfaces.model.Opcion;
import mx.sadead.spring.joinfaces.model.Permiso;
import mx.sadead.spring.joinfaces.repositories.OpcionRepository;
import mx.sadead.spring.joinfaces.repositories.PermisoRepository;
import mx.sadead.spring.joinfaces.services.OpcionService;

@Service
@Transactional
public class OpcionServiceImpl extends BaseServiceImpl<Opcion, Long> implements OpcionService {
	
	private final OpcionRepository opcionRepository;
	private final PermisoRepository permisoRepository;

	public OpcionServiceImpl(OpcionRepository opcionRepository, PermisoRepository permisoRepository) {
		this.opcionRepository = opcionRepository;
		this.permisoRepository = permisoRepository;
	}

	@Override
	public List<Opcion> findByOpcionIdIsNullOrderByOrden() {
		return opcionRepository.findByOpcionIdIsNullOrderByOrden();
	}

	@Override
	public void saveOpcionPerfiles(Opcion opcion, List<Long> roles) {
		opcionRepository.save(opcion);
        List<Permiso> perm = permisoRepository.findAllByOpcionId(opcion.getId());
        for (Permiso p : perm) {
            permisoRepository.deleteById(p.getId());
        }
//        System.out.println("tes trans:" + (1/0));
        List<Permiso> permisos = new ArrayList<>();
        for (Long rolId : roles) {
            Permiso permiso = new Permiso();
            permiso.setOpcionId(opcion.getId());
            permiso.setRolId(rolId);
            permiso.setConsultar("V");
            permiso.setCrear("V");
            permiso.setEliminar("V");
            permiso.setModificar("V");
            permisos.add(permiso);
        }
        permisoRepository.saveAll(permisos);
		
	}

}
