package mx.sadead.spring.joinfaces.services;

import java.util.List;

import org.springframework.stereotype.Service;

import mx.sadead.spring.joinfaces.model.Rol;
import mx.sadead.spring.joinfaces.repositories.RolRepository;

@Service
public class RolService implements BaseService<Rol, Long> {
	
	private final RolRepository rolRepository;

	public RolService(RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}

	@Override
	public List<Rol> findAll() {
		return rolRepository.findAll();
	}

	@Override
	public Rol findById(Long id) {
		return rolRepository.findById(id).orElseGet(null);
	}

	@Override
	public Rol save(Rol object) {
		return rolRepository.save(object);
	}

	@Override
	public Rol update(Rol object) {
		return rolRepository.save(object);
	}

	@Override
	public void deleteById(Long id) {
		rolRepository.deleteById(id);
	}

	@Override
	public void delete(Rol object) {
		rolRepository.delete(object);
	}

}
