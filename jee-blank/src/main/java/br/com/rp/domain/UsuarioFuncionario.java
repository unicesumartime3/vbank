package br.com.rp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * UsuarioFuncionario
 * 
 * Classe que identifica um usuário do tipo FUNCIONARIO. Nesta classe temos
 * métodos, atributos e definições de aceso específicas do usuário.
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
@DiscriminatorValue(value = "funcionario")
public class UsuarioFuncionario extends Usuario {

	/*
	 * Funcionário ao qual este usuário pertence.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funcionario_id", nullable = true)
	private Funcionario funcionario;

	public UsuarioFuncionario() {
		super();
	}

	public UsuarioFuncionario(String nome, String login, String senha, Funcionario funcionario) {
		super(nome, login, senha);
		this.funcionario = funcionario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
