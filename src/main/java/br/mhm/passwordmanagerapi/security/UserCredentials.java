package br.mhm.passwordmanagerapi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.mhm.passwordmanagerapi.model.Usuario;

public class UserCredentials implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private final Usuario usuario;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserCredentials(Usuario usuario) {
		this.usuario = usuario;
		this.authorities = new ArrayList<>();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return usuario.getSenha();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return usuario.isAtivado();
	}
	
	public UUID getId() {
		return usuario.getId();
	}
}
