package br.mhm.passwordmanagerapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.mhm.passwordmanagerapi.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
	
    public Usuario findByEmail(String email);
    
}
