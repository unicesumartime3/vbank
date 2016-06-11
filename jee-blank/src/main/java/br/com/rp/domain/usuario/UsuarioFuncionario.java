package br.com.rp.domain.usuario;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "funcionario")
public class UsuarioFuncionario extends Usuario {

	@Column(name = "login", length = 30, nullable = true)
	private String login;
	
	public UsuarioFuncionario() {
		super();
	}
}
