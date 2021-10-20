package br.mhm.passwordmanagerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.mhm.passwordmanagerapi.model.Usuario;
import br.mhm.passwordmanagerapi.repository.UsuarioRepository;
import br.mhm.passwordmanagerapi.service.UsuarioService;
import br.mhm.passwordmanagerapi.service.ValidationService;

@RestController
@RequestMapping("/v1/cadastro")
public class CadastroController extends AbstractController {
	
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private UsuarioService usuarioService;
	@Autowired private ValidationService validationService;
	
	@PostMapping
	public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        ObjectNode error = validationService.validate(usuario);

        if (error != null) {
            return validationFailed(error);
        }
		
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
