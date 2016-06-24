package br.com.rp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * UsuarioCliente
 * 
 * Classe que identifica um usuário do tipo CLIENTE. Nesta classe temos métodos,
 * atributos e definições de aceso específicas do cliente.
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
@DiscriminatorValue(value = "cliente")
public class UsuarioCliente extends Usuario {

	/*
	 * Cliente a qual este usuario pertence. Importante lembrar que no caso de
	 * usuário de clientes, o login de acesso será feito pelo número da conta do
	 * cliente.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", nullable = true)
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public UsuarioCliente(String nome, String login, String senha, Cliente cliente) {
		super(nome, login, senha);
		this.cliente = cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public UsuarioCliente() {
		super();
	}

}
