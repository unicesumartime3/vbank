package br.com.rp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Proposta
 * 
 * Prosta de abertura de conta.
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
@Table(name = "proposta")
public class Proposta extends BaseEntity implements Serializable {

	@Column(name = "cpf", length = 14, nullable = false)
	private String cpf;

	@Column(name = "nome", length = 60, nullable = false)
	private String nome;

	@Column(name = "regiao", length = 20, nullable = false)
	private String regiao;

	@Column(name = "vl_renda", precision = 14, scale = 2, nullable = false)
	private BigDecimal renda;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_proposta", nullable = false)
	private Date dataProposta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioanalise_id", referencedColumnName = "id", nullable = true)
	private UsuarioFuncionario usuarioAnalise;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "motivorejeicao_id", referencedColumnName = "id", nullable = true)
	private MotivoRejeicao motivoRejeicao;

	public Proposta() {

	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public BigDecimal getRenda() {
		return renda;
	}

	public void setRenda(BigDecimal renda) {
		this.renda = renda;
	}

	public Date getDataProposta() {
		return dataProposta;
	}

	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}

	public UsuarioFuncionario getUsuarioAnalise() {
		return usuarioAnalise;
	}

	public void setUsuarioAnalise(UsuarioFuncionario usuarioAnalise) {
		this.usuarioAnalise = usuarioAnalise;
	}

	public MotivoRejeicao getMotivoRejeicao() {
		return motivoRejeicao;
	}

	public void setMotivoRejeicao(MotivoRejeicao motivoRejeicao) {
		this.motivoRejeicao = motivoRejeicao;
	}

}
