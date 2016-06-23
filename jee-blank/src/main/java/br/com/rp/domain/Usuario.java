package br.com.rp.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * @author Christian Marchiori
 * @email cmxk@live.com
 *
 * @author Flávia Ferreira
 * @email flaviahferreirah@gmail.com
 *
 * @author Júlio Serra
 * @email julioserraaraujo@gmail.com
 * 
 * @author Rafael Suzin
 * @email rafaelsuzin1@gmail.com
 *
 */

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tp_usuario", discriminatorType = DiscriminatorType.STRING, length = 15)
public abstract class Usuario extends BaseEntity implements Serializable {

	@Column(name = "nome", length = 60, nullable = false)
	private String nome;

	@Size(min = 6, max = 8)
	@Column(name = "senha", length = 8, nullable = false)
	private String senha;

	/*
	 * Lista de ações que este usuário possui.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "acao_usuario", joinColumns = {
			@JoinColumn(referencedColumnName = "id", name = "usuario_id") }, inverseJoinColumns = @JoinColumn(referencedColumnName = "id", name = "acao_id"))
	private List<Acao> acoes;

	public Usuario() {

	}
	
	public Usuario(String nome, String senha) {
		super();
		this.nome = nome;
		this.senha = senha;
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
