package br.mhm.passwordmanagerapi.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.mhm.passwordmanagerapi.dto.SenhaDTO;
import br.mhm.passwordmanagerapi.model.Senha;
import br.mhm.passwordmanagerapi.model.Usuario;
import br.mhm.passwordmanagerapi.repository.SenhaRepository;
import br.mhm.passwordmanagerapi.repository.UsuarioRepository;
import br.mhm.passwordmanagerapi.service.ValidationService;

@RestController
@RequestMapping("/v1/{userId}/senhas")
public class SenhaController extends AbstractController {
	
	@Autowired SenhaRepository senhaRepository;
	@Autowired UsuarioRepository usuarioRepository;
	@Autowired private ValidationService validationService;
	
	@GetMapping
	public Page<SenhaDTO> getSenhas(@PathVariable UUID userId, Pageable pageable) {
		return senhaRepository.findAllByUsuarioId(pageable, userId);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getSenhaById(@PathVariable UUID userId, @PathVariable UUID id) {
        SenhaDTO senha = senhaRepository.findByIdAndUsuarioId(id, userId).orElse(null);
        return senha != null ? ok(senha) : notFound("Item not found");
	}
	
	//TODO return caso userId null 
	@PostMapping
	public ResponseEntity<?> createSenha(@PathVariable UUID userId, @RequestBody Senha senha) {
        Usuario usuario = usuarioRepository.findById(userId).orElse(null);
	    senha.setUsuario(usuario);
	    
		ObjectNode error = validationService.validate(senha);

        if (error != null) {
            return validationFailed(error);
        }
        		
        senha.setId(null);
        senha = senhaRepository.save(senha);
		
		return created(senha.getId());
	}
	
	//TODO validacao usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSenha(@PathVariable UUID userId, @PathVariable UUID id, @RequestBody Senha senha)
    {
        if (!senhaRepository.findById(id).isPresent()) {
            return idNotFound(id);
        }
        
        ObjectNode error = validationService.validate(senha);

        if (error != null) {
            return validationFailed(error);
        }

        senha.setId(id);
        senhaRepository.save(senha);

        return ok(senha);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSenha(@PathVariable UUID userId, @PathVariable UUID id)
    {
        if (!senhaRepository.findById(id).isPresent()) {
            return idNotFound(id);
        }

        senhaRepository.deleteById(id);
        return ok();
    }	
}
