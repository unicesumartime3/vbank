package br.com.rp.domain.usuario;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.rp.domain.funcionario.Funcionario;
import br.com.rp.domain.permissoes.AcaoUsuario;

/**
 * UsuarioFuncionario
 * 
 * Classe que identifica um usuário do tipo FUNCIONARIO. Nesta classe temos
 * métodos, atributos e definições de aceso específicas do usuário.
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_funcionario", referencedColumnName = "id", nullable = false)
	private Funcionario funcionario;
	
	private Set<AcaoUsuario> acaoUsuarios;
	
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

	public Set<AcaoUsuario> getAcaoUsuarios() {
		return acaoUsuarios;
	}

	public void setAcaoUsuarios(Set<AcaoUsuario> acaoUsuarios) {
		this.acaoUsuarios = acaoUsuarios;
	}
}
