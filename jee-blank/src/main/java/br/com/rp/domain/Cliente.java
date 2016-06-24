package br.com.rp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
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
@Table(name = "cliente")
public class Cliente extends BaseEntity implements Serializable {

	@Column(name = "nome", length = 60, nullable = false)
	private String nome;

	@Column(name = "cpf", length = 15, nullable = false)
	private String cpf;

	@Column(name = "email", length = 200, nullable = false)
	private String email;
	
	@Column(name = "vl_renda", precision = 14, scale = 2, nullable = false)
	private BigDecimal vlRenda;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_conta", referencedColumnName = "id", nullable = true)
	private Conta conta;

	public Cliente() {

	}

	public Cliente(String nome, String cpf,String email, BigDecimal vlRenda, Conta conta) {
		this.nome = nome;
		this.cpf = cpf;
		this.vlRenda = vlRenda;
		this.conta = conta;
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public BigDecimal getVlRenda() {
		return vlRenda;
	}

	public void setVlRenda(BigDecimal vlRenda) {
		this.vlRenda = vlRenda;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
