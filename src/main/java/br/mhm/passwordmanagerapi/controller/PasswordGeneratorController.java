package br.mhm.passwordmanagerapi.controller;

import static br.mhm.passwordmanagerapi.util.PasswordGeneratorUtil.generatePassword;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/gerar-senha")
public class PasswordGeneratorController extends AbstractController {
	
	@PostMapping
	public ResponseEntity<?> createUsuario(@RequestBody Integer size) {
				
		if (size < 8 || size > 50) {
			throw new IllegalArgumentException("Password length must be between 8 and 50");
		}
		
		String randomPassword = generatePassword(size);
		return ok(randomPassword);
	}
}