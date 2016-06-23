package br.com.rp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "email")
public class Email extends BaseEntity implements Serializable{

	private String remetente;
	private String destinatario;
	private String assunto;
	private String descricao;
	private Cliente cliente;
	private Date dhEnvio;
	private SituacaoEmail situacao;
	
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
	
}
