package br.mhm.passwordmanagerapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mhm.passwordmanagerapi.model.Senha;

public interface SenhaRepository extends JpaRepository<Senha, UUID> {}
