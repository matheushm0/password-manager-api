package br.mhm.passwordmanagerapi.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Setter;

@Entity
@Setter
public class Usuario {

	private UUID id;
	private String nome;
	private String email;
	private String senha;
	private boolean ativado;
	private List<Senha> senhas;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public UUID getId() {
		return id;
	}

	@NotNull
	public String getNome() {
		return nome;
	}

	@NotNull
	@Size(min = 5, max = 80)
	public String getEmail() {
		return email;
	}

	@NotNull
	@Size(min = 8, max = 100)
	public String getSenha() {
		return senha;
	}
	
	@NotNull
	public boolean isAtivado() {
			return ativado;
	}
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
    public List<Senha> getSenhas() {
        return senhas;
    }
}
