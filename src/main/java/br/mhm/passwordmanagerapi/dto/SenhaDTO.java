package br.mhm.passwordmanagerapi.dto;

import java.util.UUID;

public interface SenhaDTO {
	public UUID getId();
	public String getNome();
	public String getDescricao();
	public String getUsername();
	public String getSenha();
	public String getUrl();
}
