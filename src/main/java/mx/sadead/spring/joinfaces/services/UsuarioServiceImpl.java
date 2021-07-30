package mx.sadead.spring.joinfaces.services;

import org.springframework.stereotype.Service;

import mx.sadead.spring.joinfaces.model.Usuario;
import mx.sadead.spring.joinfaces.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService  {

	private final UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario findOneByUserName(String username) {
		return usuarioRepository.findOneByUserName(username).orElse(null);
	}

}
