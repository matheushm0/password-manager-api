package br.mhm.passwordmanagerapi.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.mhm.passwordmanagerapi.model.Usuario;
import br.mhm.passwordmanagerapi.repository.UsuarioRepository;
import br.mhm.passwordmanagerapi.security.UserCredentials;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("User '" + email + "' not found.");
        }

        return new UserCredentials(usuario);
	}
	
    public UserCredentials loadUserById(UUID id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            throw new UsernameNotFoundException("UserId '" + id + "' not found.");
        }

        return new UserCredentials(usuario);
    }
}
