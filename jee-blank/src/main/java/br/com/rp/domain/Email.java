package br.com.rp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "email")
public class Email extends BaseEntity implements Serializable{

	@Column(name = "remetente", length = 50, nullable = true)
	private String remetente;
	
	@Column(name = "destinatario", length = 50, nullable = false)
	private String destinatario;
	
	@Column(name = "assunto", length = 200, nullable = false)
	private String assunto;
	
	@Lob
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName="id", nullable = true)
	private Cliente cliente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dh_envio", nullable = false)
	private Date dhEnvio;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_situacao", length = 30, nullable = false)
	private SituacaoEmail situacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "proposta_id", referencedColumnName="id", nullable = true)
	private Proposta proposta;
	
	public Email() {

	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDhEnvio() {
		return dhEnvio;
	}

	public void setDhEnvio(Date dhEnvio) {
		this.dhEnvio = dhEnvio;
	}

	public SituacaoEmail getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoEmail situacao) {
		this.situacao = situacao;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}
	
}
