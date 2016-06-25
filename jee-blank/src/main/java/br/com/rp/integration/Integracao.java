package br.com.rp.integration;

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

import br.com.rp.domain.Agendamento;
import br.com.rp.domain.Banco;
import br.com.rp.domain.BaseEntity;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.Movimento;
import br.com.rp.domain.TipoConta;
import br.com.rp.domain.TipoMovimento;
import br.com.rp.domain.TipoTransacao;

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
@Table(name = "integracao")
public class Integracao extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = true)
	private Cliente clienteRemetente;

	@Column(name = "codigo_barra", length = 100, nullable = true)
	private String codigoBarra;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agendamento_id", referencedColumnName = "id", nullable = true)
	private Agendamento agendamento;

	@Column(name = "vl_integracao", precision = 14, scale = 2, nullable = true)
	private BigDecimal vlIntegracao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tp_conta_debito", length = 15, nullable = true)
	private TipoConta tipoContaDebito;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_integracao", nullable = true)
	private Date dtIntegracao;

	@Column(name = "nr_conta_favorecido", length = 20, nullable = true)
	private String nrContaFavorecido;

	@Column(name = "agencia_favorecido", length = 10, nullable = true)
	private String agenciaFavorecido;

	@Column(name = "email_favorecido", length = 50, nullable = true)
	private String emailFavorecido;

	@Enumerated(EnumType.STRING)
	@Column(name = "tp_conta_favorecido", length = 15, nullable = true)
	private TipoConta tipoContaFavorecido;

	@Lob
	@Column(name = "foto_cheque_frente", nullable = true)
	private byte[] fotoChequeFrente;

	@Lob
	@Column(name = "foto_cheque_verso", nullable = true)
	private byte[] fotoChequeVerso;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "banco_id", referencedColumnName = "id", nullable = true)
	private Banco bancoFavorecido;

	@Enumerated(EnumType.STRING)
	@Column(name = "tp_integracao", length = 20, nullable = true)
	private TipoMovimento tipoIntegracao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_transacao", length = 20, nullable = true)
	private TipoTransacao tipoTransacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tp_situacao_bacen", length = 20, nullable = true)
	private SituacaoIntegracaoBancoCentral situacaoIntegracaoBancoCentral;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_situacao_eua", length = 20, nullable = true)
	private SituacaoIntegracaoEUA situacaoIntegracaoEUA;

	public Integracao() {

	}

	public Integracao addMovimento(Movimento  movimento){
		Integracao integracao = new Integracao();
		integracao.setClienteRemetente(movimento.getClienteRemetente());
		integracao.setCodigoBarra(movimento.getCodigoBarra());
		integracao.setAgendamento(movimento.getAgendamento());
		integracao.setVlIntegracao(movimento.getVlMovimento());
		integracao.setTipoContaDebito(movimento.getTipoContaDebito());
		integracao.setDtIntegracao(movimento.getDtMovimento());
		integracao.setNrContaFavorecido(movimento.getNrContaFavorecido());
		integracao.setEmailFavorecido(movimento.getEmailFavorecido());
		integracao.setTipoContaFavorecido(movimento.getTipoContaFavorecido());
		integracao.setFotoChequeFrente(movimento.getFotoChequeFrente());
		integracao.setFotoChequeVerso(movimento.getFotoChequeVerso());
		integracao.setBancoFavorecido(movimento.getBancoFavorecido());
		integracao.setTipoIntegracao(movimento.getTipoMovimento());
		integracao.setTipoTransacao(movimento.getTipoTransacao());
		integracao.setSituacaoIntegracaoBancoCentral(SituacaoIntegracaoBancoCentral.NAO_INTEGRADO);
		integracao.setSituacaoIntegracaoEUA(SituacaoIntegracaoEUA.NAO_INTEGRADO);
		return integracao;
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

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public BigDecimal getVlIntegracao() {
		return vlIntegracao;
	}

	public void setVlIntegracao(BigDecimal vlIntegracao) {
		this.vlIntegracao = vlIntegracao;
	}

	public TipoConta getTipoContaDebito() {
		return tipoContaDebito;
	}

	public void setTipoContaDebito(TipoConta tipoContaDebito) {
		this.tipoContaDebito = tipoContaDebito;
	}

	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public void setTipoIntegracao(TipoMovimento tipoIntegracao) {
		this.tipoIntegracao = tipoIntegracao;
	}

	public Date getDtIntegracao() {
		return dtIntegracao;
	}

	public void setDtIntegracao(Date dtIntegracao) {
		this.dtIntegracao = dtIntegracao;
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

	public SituacaoIntegracaoBancoCentral getSituacaoIntegracaoBancoCentral() {
		return situacaoIntegracaoBancoCentral;
	}

	public void setSituacaoIntegracaoBancoCentral(SituacaoIntegracaoBancoCentral situacaoIntegracaoBancoCentral) {
		this.situacaoIntegracaoBancoCentral = situacaoIntegracaoBancoCentral;
	}

	public TipoMovimento getTipoIntegracao() {
		return tipoIntegracao;
	}

	public SituacaoIntegracaoEUA getSituacaoIntegracaoEUA() {
		return situacaoIntegracaoEUA;
	}

	public void setSituacaoIntegracaoEUA(SituacaoIntegracaoEUA situacaoIntegracaoEUA) {
		this.situacaoIntegracaoEUA = situacaoIntegracaoEUA;
	}

}
