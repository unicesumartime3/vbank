package br.com.rp.domain.conta;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.rp.domain.BaseEntity;

/**
 * Conta
 * 
 * Classe que identifica as contas a serem usadas em clientes.
 * 
 * @author Rafael Suzin
 * @email rafaelsuzin1@gmail.com
 *
 */
@Entity
@Table(name = "conta")
public class Conta extends BaseEntity implements Serializable {

	@Column(name = "nr_conta", length = 20, nullable = false)
	private String nrConta;

	@Column(name = "is_contacorrente", length = 1, nullable = false)
	private Boolean isContaCorrente;

	@Column(name = "is_contapoupanca", length = 1, nullable = false)
	private Boolean isContaPoupanca;

	public Conta() {

	}

	public String getNrConta() {
		return nrConta;
	}

	public void setNrConta(String nrConta) {
		this.nrConta = nrConta;
	}

	public Boolean getIsContaCorrente() {
		return isContaCorrente;
	}

	public void setIsContaCorrente(Boolean isContaCorrente) {
		this.isContaCorrente = isContaCorrente;
	}

	public Boolean getIsContaPoupanca() {
		return isContaPoupanca;
	}

	public void setIsContaPoupanca(Boolean isContaPoupanca) {
		this.isContaPoupanca = isContaPoupanca;
	}

}
