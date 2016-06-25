package br.com.rp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Conta
 * 
 * Classe que identifica as contas a serem usadas em clientes.
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
@Table(name = "conta")
public class Conta extends BaseEntity implements Serializable {

	@Column(name = "nr_conta", length = 20, nullable = false)
	private String nrConta;

	@Column(name = "is_contacorrente", length = 1, nullable = false)
	private Boolean isContaCorrente;

	@Column(name = "is_contapoupanca", length = 1, nullable = false)
	private Boolean isContaPoupanca;

	@Column(name = "vl_saldo_conta_poupanca", precision = 14, scale = 2, nullable = true)
	private BigDecimal vlSaldoContaPoupanca;
	
	@Column(name = "vl_saldo_conta_corrente", precision = 14, scale = 2, nullable = true)
	private BigDecimal vlSaldoContaCorrente;
	
	public Conta() {

	}
	
	public Conta(String nrConta, Boolean isContaCorrente, Boolean isContaPoupanca) {
		this.nrConta = nrConta;
		this.isContaCorrente = isContaCorrente;
		this.isContaPoupanca = isContaPoupanca;
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

	public BigDecimal getVlSaldoContaPoupanca() {
		return vlSaldoContaPoupanca;
	}

	public void setVlSaldoContaPoupanca(BigDecimal vlSaldoContaPoupanca) {
		this.vlSaldoContaPoupanca = vlSaldoContaPoupanca;
	}

	public BigDecimal getVlSaldoContaCorrente() {
		return vlSaldoContaCorrente;
	}

	public void setVlSaldoContaCorrente(BigDecimal vlSaldoContaCorrente) {
		this.vlSaldoContaCorrente = vlSaldoContaCorrente;
	}

}
