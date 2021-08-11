package mx.sadead.spring.joinfaces.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.sadead.spring.joinfaces.model.Usuario;
import mx.sadead.spring.joinfaces.model.UsuarioRol;
import mx.sadead.spring.joinfaces.repositories.UsuarioRepository;
import mx.sadead.spring.joinfaces.repositories.UsuarioRolRepository;
import mx.sadead.spring.joinfaces.services.UsuarioService;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService  {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioRolRepository usuarioRolRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioRolRepository usuarioRolRepository) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioRolRepository = usuarioRolRepository;
	}

	@Override
	public Usuario findOneByUserName(String username) {
		return usuarioRepository.findOneByUserName(username).orElse(null);
	}

	@Override
	public List<UsuarioRol> getRolesByUsername(String userName) {
		return usuarioRolRepository.findByUserName(userName);
	}

	@Override
	public void saveUsuarioPerfiles(Usuario usuario, List<Long> idRoles) {
		usuarioRepository.save(usuario);
        List<UsuarioRol> ur = getRolesByUsername(usuario.getUserName());
        for (UsuarioRol usuarioRol : ur) {
            usuarioRolRepository.deleteById(usuarioRol.getId());
        }
        List<UsuarioRol> usuarioRoles = new ArrayList<>();
        for (Long rolId : idRoles) {
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuarioId(usuario.getId());
            usuarioRol.setRolId(rolId);
            usuarioRoles.add(usuarioRol);
        }
        usuarioRolRepository.saveAll(usuarioRoles);
	}

}
