package mx.sadead.spring.joinfaces.bootstrap;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import mx.sadead.spring.joinfaces.model.Estatus;
import mx.sadead.spring.joinfaces.model.Rol;
import mx.sadead.spring.joinfaces.model.TipoEstatus;
import mx.sadead.spring.joinfaces.model.Usuario;
import mx.sadead.spring.joinfaces.model.UsuarioRol;
import mx.sadead.spring.joinfaces.services.EstatusService;
import mx.sadead.spring.joinfaces.services.RolService;
import mx.sadead.spring.joinfaces.services.SqlQueryService;
import mx.sadead.spring.joinfaces.services.TipoEstatusService;
import mx.sadead.spring.joinfaces.services.UsuarioRolService;
import mx.sadead.spring.joinfaces.services.UsuarioService;

@Component
public class BootstrapData implements CommandLineRunner {

	public static final Logger LOGGER = LoggerFactory.getLogger(BootstrapData.class);

	private final RolService rolService;
	private final TipoEstatusService tipoEstatusService;
	private final EstatusService estatusService;
	private final UsuarioService usuarioService;
	private final UsuarioRolService usuarioRolService;
	private final SqlQueryService sqlQueryService;
	private final PasswordEncoder passwordEncoder;

	public BootstrapData(RolService rolService, TipoEstatusService tipoEstatusService, EstatusService estatusService,
			UsuarioService usuarioService, UsuarioRolService usuarioRolService, SqlQueryService sqlQueryService,
			PasswordEncoder passwordEncoder) {
		this.rolService = rolService;
		this.tipoEstatusService = tipoEstatusService;
		this.estatusService = estatusService;
		this.usuarioService = usuarioService;
		this.usuarioRolService = usuarioRolService;
		this.sqlQueryService = sqlQueryService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {

		LOGGER.info("CREATING ROLES");
		Rol rol1 = new Rol();
		rol1.setClaveRol("ROLE_ADMIN");
		rol1.setDescripcion("Perfil de Administrador General del Sistema");

		LOGGER.info("rol: {}", rol1);
		rol1 = rolService.save(rol1);
		LOGGER.info("rol: {}", rol1);

		Rol rol2 = new Rol();
		rol2.setClaveRol("ROLE_USER");
		rol2.setDescripcion("Perfil de Usuario General del Sistema");

		rol2 = rolService.save(rol2);

		LOGGER.info("findAll: {}", rolService.findAll());

		LOGGER.info("CREATING TYPE STATUS");
		TipoEstatus tipoUsuario = new TipoEstatus();
		tipoUsuario.setNombre("TIPO_USUARIO");
		tipoEstatusService.save(tipoUsuario);

		LOGGER.info("CREATING ESTATUS");
		Estatus estatusActivo = new Estatus();
		estatusActivo.setNombre("ACTIVO");
		estatusActivo.setTipoEstatusId(tipoUsuario.getId());
		estatusService.save(estatusActivo);

		LOGGER.info("CREATING USERS");
		Usuario admin = new Usuario();
		admin.setActivo(1);
		admin.setEmail("admin@gmail.com");
		admin.setUserName("admin");
		admin.setPassword(passwordEncoder.encode("4dm1n"));
		admin.setNombre("ADMINISTRADOR");
		admin.setPaterno("GENERAL");
		admin.setEstatusId(estatusActivo.getId());
		
		usuarioService.save(admin);
		LOGGER.info("admin: {}", admin.getPassword());
		
		UsuarioRol adminRol1 = new UsuarioRol();
		adminRol1.setUsuarioId(admin.getId());
		adminRol1.setRolId(rol1.getId());
		
		usuarioRolService.save(adminRol1);
		
		LOGGER.info("Users findAll: {}", usuarioService.findAll());

		LOGGER.info("TESTING SQL CUSTOM QUERY");
		List<Rol> roles = sqlQueryService.findAllByQueryNativeToEntity(Rol.class, "SELECT * FROM cfg_rol WHERE id = ?1",
				new Object[] { 2L });
		LOGGER.info("roles: {}", roles);

		LOGGER.info("TESTING SQL CUSTOM QUERY TO MAP");
		List<Map<String, Object>> listMap = sqlQueryService
				.findAllByQueryNativeToMap("SELECT * FROM cfg_rol WHERE id = ?0", new Object[] { 2L });
		LOGGER.info("listMap: {}", listMap);

	}

}
