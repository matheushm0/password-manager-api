package br.mhm.passwordmanagerapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.mhm.passwordmanagerapi.dto.SenhaDTO;
import br.mhm.passwordmanagerapi.model.Senha;

public interface SenhaRepository extends JpaRepository<Senha, UUID> {
	
	public Page<SenhaDTO> findAllByUsuarioId(Pageable pageable, UUID usuarioId);
	
	public Optional<SenhaDTO> findByIdAndUsuarioId(UUID id, UUID usuarioID);
	
}
