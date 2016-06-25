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
import javax.persistence.Lob;
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
@Table(name = "deposito")
public class Deposito extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
	private Cliente clienteRemetente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_deposito", nullable = true)
	private Date dtDeposito;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agendamento_id", referencedColumnName = "id", nullable = true)
	private Agendamento agendamento;

	@Column(name = "vl_deposito", precision = 14, scale = 2, nullable = false)
	private BigDecimal vlDeposito;

	@Column(name = "nr_conta_favorecido", length = 20, nullable = false)
	private String nrContaFavorecido;

	@Column(name = "agencia_favorecido", length = 10, nullable = true)
	private String agenciaFavorecido;

	@Column(name = "email_favorecido", length = 50, nullable = true)
	private String emailFavorecido;

	@Enumerated(EnumType.STRING)
	@Column(name = "tp_conta_favorecido", length = 15, nullable = false)
	private TipoConta tipoContaFavorecido;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_conta_debito", length = 15, nullable = false)
	private TipoConta tipoContaDebito;

	@Enumerated(EnumType.STRING)
	@Column(name = "tp_situacao", length = 15, nullable = false)
	private SituacaoDeposito situacaoDeposito;

	@Lob
	@Column(name = "foto_cheque_frente", nullable = false)
	private byte[] fotoChequeFrente;

	@Lob
	@Column(name = "foto_cheque_verso", nullable = false)
	private byte[] fotoChequeVerso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "banco_id", referencedColumnName = "id", nullable = false)
	private Banco bancoFavorecido;

	public Deposito() {

	}

	public Cliente getClienteRemetente() {
		return clienteRemetente;
	}

	public void setClienteRemetente(Cliente clienteRemetente) {
		this.clienteRemetente = clienteRemetente;
	}

	public Date getDtDeposito() {
		return dtDeposito;
	}

	public void setDtDeposito(Date dtDeposito) {
		this.dtDeposito = dtDeposito;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public BigDecimal getVlDeposito() {
		return vlDeposito;
	}

	public void setVlDeposito(BigDecimal vlDeposito) {
		this.vlDeposito = vlDeposito;
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

	public SituacaoDeposito getSituacaoDeposito() {
		return situacaoDeposito;
	}

	public void setSituacaoDeposito(SituacaoDeposito situacaoDeposito) {
		this.situacaoDeposito = situacaoDeposito;
	}

	public byte[] getFotoChequeFrente() {
		return fotoChequeFrente;
	}

	public void setFotoChequeFrente(byte[] fotoChequeFrente) {
		this.fotoChequeFrente = fotoChequeFrente;
	}

	public byte[] getFotoChequeVerso() {
		return fotoChequeVerso;
	}

	public void setFotoChequeVerso(byte[] fotoChequeVerso) {
		this.fotoChequeVerso = fotoChequeVerso;
	}

	public Banco getBancoFavorecido() {
		return bancoFavorecido;
	}

	public void setBancoFavorecido(Banco bancoFavorecido) {
		this.bancoFavorecido = bancoFavorecido;
	}

}
