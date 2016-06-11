package br.com.rp.domain.usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.rp.domain.cliente.Cliente;

@Entity
@DiscriminatorValue(value = "cliente")
public class UsuarioCliente extends Usuario {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = true)
	private Cliente cliente;

	public UsuarioCliente() {
		super();
	}

}
