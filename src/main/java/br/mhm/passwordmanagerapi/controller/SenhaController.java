package br.mhm.passwordmanagerapi.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.mhm.passwordmanagerapi.model.Senha;
import br.mhm.passwordmanagerapi.repository.SenhaRepository;

@RestController
@RequestMapping("/v1/senhas")
public class SenhaController {
	
	@Autowired SenhaRepository senhaRepository;
	@Autowired protected ObjectMapper jsonMapper;
	
	@GetMapping
	public Page<Senha> getSenhas(Pageable pageable) {
		return senhaRepository.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getSenhaById(@PathVariable UUID id) {
        Senha senha = senhaRepository.findById(id).orElse(null);
        return senha != null ? status(HttpStatus.OK).body(senha) : status(HttpStatus.NOT_FOUND).body(("Item not found"));
	}
	
	@PostMapping
	public ResponseEntity<?> createSenha(@RequestBody Senha senha) {
		
        senha.setId(null);
        senha = senhaRepository.save(senha);
		
        ObjectNode body = jsonMapper.createObjectNode().put("status", "success");

        if (senha.getId() != null) {
            body.put("id", senha.getId().toString());
        }
		
		return status(HttpStatus.CREATED).body(body);
	}
	
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSenha(@PathVariable UUID id, @RequestBody Senha senha)
    {
        //TODO
//        if (!senhaRepository.findById(id).isPresent()) {
//            return idNotFound(id);
//        }
//        ObjectNode error = validationService.validate(modalidade);
//
//        if (error != null) {
//            return validationFailed(error);
//        }

        senha.setId(id);
        senhaRepository.save(senha);
        
        ObjectNode body = jsonMapper.createObjectNode().put("status", "success");

        if (senha.getId() != null) {
            body.put("id", senha.getId().toString());
        }

        return status(HttpStatus.CREATED).body(body);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSenha(@PathVariable UUID id)
    {
        senhaRepository.deleteById(id);
        return status(HttpStatus.OK).build();
    }	
}
