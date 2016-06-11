package br.com.rp.domain.conta;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.rp.domain.BaseEntity;


@Entity
@Table(name = "conta")
public class Conta extends BaseEntity implements Serializable {

	@Column(name = "nr_conta", length = 20, nullable = false)
	private String nrConta;

	public Conta() {

	}

	public String getNrConta() {
		return nrConta;
	}

	public void setNrConta(String nrConta) {
		this.nrConta = nrConta;
	}
}
