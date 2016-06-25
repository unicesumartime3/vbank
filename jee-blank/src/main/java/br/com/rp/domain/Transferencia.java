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
@Table(name = "transferencia")
public class Transferencia extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
	private Cliente clienteRemetente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_transferencia", nullable = true)
	private Date dtTransferencia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agendamento_id", referencedColumnName = "id", nullable = true)
	private Agendamento agendamento;

	@Column(name = "vl_tranferencia", precision = 14, scale = 2, nullable = false)
	private BigDecimal vlTranferencia;
	
	@Column(name = "nr_conta_favorecido", length = 20, nullable = false)
	private String nrContaFavorecido;
	
	@Column(name = "agencia_favorecido", length = 10, nullable = true)
	private String agenciaFavorecido;
	
	@Column(name = "email_favorecido", length = 10, nullable = true)
	private String emailFavorecido;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_conta_favorecido", length = 15, nullable = false)
	private TipoConta tipoContaFavorecido;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_conta_debito", length = 15, nullable = false)
	private TipoConta tipoContaDebito;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_situacao", length = 15, nullable = false)
	private SituacaoTransferencia situacaoTransferencia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "banco_id", referencedColumnName = "id", nullable = false)
	private Banco bancoFavorecido;
	
	public Transferencia() {

	}

	public Cliente getClienteRemetente() {
		return clienteRemetente;
	}

	public void setClienteRemetente(Cliente clienteRemetente) {
		this.clienteRemetente = clienteRemetente;
	}

	public Date getDtTransferencia() {
		return dtTransferencia;
	}

	public void setDtTransferencia(Date dtTransferencia) {
		this.dtTransferencia = dtTransferencia;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public BigDecimal getVlTranferencia() {
		return vlTranferencia;
	}

	public void setVlTranferencia(BigDecimal vlTranferencia) {
		this.vlTranferencia = vlTranferencia;
	}

	public String getNrContaFavorecido() {
		return nrContaFavorecido;
	}

	public void setNrContaFavorecido(String nrContaFavorecido) {
		this.nrContaFavorecido = nrContaFavorecido;
	}

	public String getAgenciaFavorecido() {
		return agenciaFavorecido;
	}

	public void setAgenciaFavorecido(String agenciaFavorecido) {
		this.agenciaFavorecido = agenciaFavorecido;
	}

	public String getEmailFavorecido() {
		return emailFavorecido;
	}

	public void setEmailFavorecido(String emailFavorecido) {
		this.emailFavorecido = emailFavorecido;
	}

	public TipoConta getTipoContaFavorecido() {
		return tipoContaFavorecido;
	}

	public void setTipoContaFavorecido(TipoConta tipoContaFavorecido) {
		this.tipoContaFavorecido = tipoContaFavorecido;
	}

	public TipoConta getTipoContaDebito() {
		return tipoContaDebito;
	}

	public void setTipoContaDebito(TipoConta tipoContaDebito) {
		this.tipoContaDebito = tipoContaDebito;
	}

	public SituacaoTransferencia getSituacaoTransferencia() {
		return situacaoTransferencia;
	}

	public void setSituacaoTransferencia(SituacaoTransferencia situacaoTransferencia) {
		this.situacaoTransferencia = situacaoTransferencia;
	}

	public Banco getBancoFavorecido() {
		return bancoFavorecido;
	}

	public void setBancoFavorecido(Banco bancoFavorecido) {
		this.bancoFavorecido = bancoFavorecido;
	}

}
