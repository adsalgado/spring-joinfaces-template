package mx.sadead.spring.joinfaces.bootstrap;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mx.sadead.spring.joinfaces.model.Rol;
import mx.sadead.spring.joinfaces.repositories.RolRepository;
import mx.sadead.spring.joinfaces.services.SqlQueryService;

@Component
public class BootstrapData implements CommandLineRunner {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(BootstrapData.class);
	
	private final RolRepository rolRepository;
	private final SqlQueryService sqlQueryService;

	public BootstrapData(RolRepository rolRepository, SqlQueryService sqlQueryService) {
		this.rolRepository = rolRepository;
		this.sqlQueryService = sqlQueryService;
	}

	@Override
	public void run(String... args) throws Exception {
		
		Rol rol1 = new Rol();
		rol1.setClaveRol("ROLE_ADMIN");
		rol1.setDescripcion("Perfil de Administrador General del Sistema");
		
		LOGGER.info("rol: {}", rol1);
		rol1 = rolRepository.save(rol1);
		LOGGER.info("rol: {}", rol1);
		
		Rol rol2 = new Rol();
		rol2.setClaveRol("ROLE_USER");
		rol2.setDescripcion("Perfil de Usuario General del Sistema");
		
		rol2 = rolRepository.save(rol2);
		
		LOGGER.info("findAll: {}", rolRepository.findAll());
		
		LOGGER.info("TESTING SQL CUSTOM QUERY");
		List<Rol> roles = sqlQueryService.findAllByQueryNativeToEntity(Rol.class, "SELECT * FROM cfg_rol WHERE id = ?1", new Object[] { 2L });
		LOGGER.info("roles: {}", roles);
		
		LOGGER.info("TESTING SQL CUSTOM QUERY TO MAP");
		List<Map<String, Object>> listMap = sqlQueryService.findAllByQueryNativeToMap("SELECT * FROM cfg_rol WHERE id = ?0", new Object[] { 2L });
		LOGGER.info("listMap: {}", listMap);
		
	}

}
