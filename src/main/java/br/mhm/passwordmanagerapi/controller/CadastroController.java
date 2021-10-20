package br.mhm.passwordmanagerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mhm.passwordmanagerapi.model.Usuario;
import br.mhm.passwordmanagerapi.repository.UsuarioRepository;
import br.mhm.passwordmanagerapi.service.UsuarioService;

@RestController
@RequestMapping("/v1/cadastro")
public class CadastroController extends AbstractController {
	
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
		
		//TODO validacoes
		
		if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
			throw new IllegalArgumentException("User already exists");
		}
		
		String encryptedPassword = usuarioService.encryptPassword(usuario.getSenha());
		
		usuario.setSenha(encryptedPassword);
		usuario.setAtivado(true);
		
		usuarioRepository.save(usuario);
		
		return created(usuario.getId());
	}
 
}
