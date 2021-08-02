package mx.sadead.spring.joinfaces.services.impl;

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

}
