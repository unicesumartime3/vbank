package br.com.rp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tp_usuario", discriminatorType = DiscriminatorType.STRING, length = 15)
public abstract class Usuario extends BaseEntity implements Serializable{


	@Column(name = "nome", length = 60, nullable = false)
	private String nome;
	
	@Size(min = 6, max = 8)
	@Column(name = "senha", length = 8, nullable = false)
	private String senha;
	
	public Usuario() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
