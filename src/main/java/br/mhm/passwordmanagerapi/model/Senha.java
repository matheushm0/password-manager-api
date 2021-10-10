package br.mhm.passwordmanagerapi.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Setter;

@Entity
@Setter
public class Senha {

	private UUID id;
	private String nome;
	private String descricao;
	private String username;
	private String senha;
	private String url;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public UUID getId() {
		return id;
	}

    @Size(min = 1, max = 50)
    @NotNull
	public String getNome() {
		return nome;
	}

    @Size(min = 1, max = 250)
	public String getDescricao() {
		return descricao;
	}

    @Size(min = 1, max = 50)
    @NotNull
	public String getUsername() {
		return username;
	}

    @Size(min = 8, max = 100)
    @NotNull
	public String getSenha() {
		return senha;
	}

	public String getUrl() {
		return url;
	}
}
