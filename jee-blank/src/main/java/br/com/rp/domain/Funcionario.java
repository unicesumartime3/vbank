package br.com.rp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Funcionario
 * 
 * Classe que identifica os funcionários da empresa.
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
@Table(name = "funcionario")
public class Funcionario extends BaseEntity implements Serializable {

	@Column(name = "nome", length = 60, nullable = false)
	private String nome;

	@Column(name = "cpf", length = 14, nullable = false)
	private String cpf;

	/*
	 * Cargo do funcionário.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cargo", referencedColumnName = "id", nullable = false)
	private Cargo cargo;

	public Funcionario() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}
