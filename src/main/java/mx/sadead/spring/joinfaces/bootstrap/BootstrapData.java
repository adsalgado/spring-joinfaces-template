package mx.sadead.spring.joinfaces.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mx.sadead.spring.joinfaces.model.Rol;
import mx.sadead.spring.joinfaces.repositories.RolRepository;

@Component
public class BootstrapData implements CommandLineRunner {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(BootstrapData.class);
	
	private final RolRepository rolRepository;

	public BootstrapData(RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		Rol rol1 = new Rol();
		rol1.setClaveRol("ROLE_ADMIN");
		rol1.setClaveRol("Perfil de Administrador General del Sistema");
		
		LOGGER.info("rol: {}", rol1);
		rol1 = rolRepository.save(rol1);
		LOGGER.info("rol: {}", rol1);
		
		LOGGER.info("findAll: {}", rolRepository.findAll());
		
	}

}
