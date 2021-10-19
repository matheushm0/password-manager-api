package br.mhm.passwordmanagerapi.controller;

import static org.springframework.http.ResponseEntity.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mhm.passwordmanagerapi.model.auth.AuthenticationRequest;
import br.mhm.passwordmanagerapi.model.auth.AuthenticationResponse;
import br.mhm.passwordmanagerapi.security.JwtTokenProvider;
import br.mhm.passwordmanagerapi.security.UserCredentials;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

	@Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider jwtProvider;

	@PostMapping("/login")
	public ResponseEntity<?> generateToken(@RequestBody AuthenticationRequest request) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(auth);
		return generateToken((UserCredentials) auth.getPrincipal());
	}
	
    private ResponseEntity<?> generateToken(UserCredentials credentials) {
        AuthenticationResponse response = new AuthenticationResponse();
        
        response.setToken(jwtProvider.generateToken(credentials.getId(), credentials.getUsername()));

        return status(HttpStatus.OK).body(response);
    }	
}
