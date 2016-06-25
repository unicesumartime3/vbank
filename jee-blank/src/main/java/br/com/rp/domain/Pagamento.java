package br.com.rp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "pagamento")
public class Pagamento extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
	private Cliente clienteRemetente;
	
	@Column(name = "codigo_barra", length = 100, nullable = false)
	private String codigoBarra;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_pagamento", nullable = true)
	private Date dtPagamento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agendamento_id", referencedColumnName="id", nullable = true)
	private Agendamento agendamento;
	
	@Column(name = "vl_pagamento", precision = 14, scale = 2, nullable = false)
	private BigDecimal vlPagamento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_conta_debito", length = 15, nullable = false)
	private TipoConta tipoContaDebito;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_situacao", length = 15, nullable = false)
	private SituacaoPagamento situacaoPagamento;

	public Pagamento() {

	}

	public Cliente getClienteRemetente() {
		return clienteRemetente;
	}

	public void setClienteRemetente(Cliente clienteRemetente) {
		this.clienteRemetente = clienteRemetente;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public BigDecimal getVlPagamento() {
		return vlPagamento;
	}

	public void setVlPagamento(BigDecimal vlPagamento) {
		this.vlPagamento = vlPagamento;
	}

	public TipoConta getTipoContaDebito() {
		return tipoContaDebito;
	}

	public void setTipoContaDebito(TipoConta tipoContaDebito) {
		this.tipoContaDebito = tipoContaDebito;
	}

	public SituacaoPagamento getSituacaoPagamento() {
		return situacaoPagamento;
	}

	public void setSituacaoPagamento(SituacaoPagamento situacaoPagamento) {
		this.situacaoPagamento = situacaoPagamento;
	}
	
}
