package br.com.rp.domain;

import javax.persistence.Column;
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
	 * Login do funcionário no sistema.
	 */
	@Column(name = "login", length = 30, nullable = true)
	private String login;

	/*
	 * Funcionário ao qual este usuário pertence.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario funcionario;

	public UsuarioFuncionario() {
		super();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
