package br.com.rp.domain.usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.rp.domain.cliente.Cliente;

/**
 * UsuarioCliente
 * 
 * Classe que identifica um usuário do tipo CLIENTE. Nesta classe temos métodos,
 * atributos e definições de aceso específicas do cliente.
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = true)
	private Cliente cliente;

	public UsuarioCliente() {
		super();
	}

}
