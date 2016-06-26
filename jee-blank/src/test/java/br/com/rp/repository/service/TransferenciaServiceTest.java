package br.com.rp.repository.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Agendamento;
import br.com.rp.domain.SituacaoTransferencia;
import br.com.rp.domain.TipoConta;
import br.com.rp.domain.TipoMovimento;
import br.com.rp.domain.TipoTransacao;
import br.com.rp.domain.Transferencia;
import br.com.rp.services.AgendamentoService;
import br.com.rp.services.BancoService;
import br.com.rp.services.ClienteService;
import br.com.rp.services.IntegracaoService;
import br.com.rp.services.MovimentoService;
import br.com.rp.services.TransferenciaService;

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

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class TransferenciaServiceTest extends AbstractTest {

	@EJB
	private TransferenciaService transferenciaService;

	@EJB
	private ClienteService clienteService;

	@EJB
	private BancoService bancoService;

	@EJB
	private MovimentoService movimentoService;

	@EJB
	private IntegracaoService integracaoService;

	@EJB
	private AgendamentoService agendamentoService;

	/*
	 * Criados dois testes separados pois quando a transferencia ainda não foi
	 * finalizada não é gerado os movimentos e integracao.
	 */
	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveInserirTransferenciaComSucesso() {
		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(clienteService.findById(100001L));
		transferencia.setDtTransferencia(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		transferencia.setVlTranferencia(new BigDecimal(800.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.PENDENTE);
		transferencia.setBancoFavorecido(bancoService.findById(100001L));

		transferenciaService.save(transferencia);
		Assert.assertNotNull(transferencia.getId());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(1500.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(0, movimentoService.getAll().size());
		Assert.assertEquals(0, integracaoService.getAll().size());
	}

	/*
	 * Este teste tem o objetivo de realizar teste no cadastro de uma
	 * transferência. Caso uma transferência seja cadastrada e finalizada, é
	 * gerado um movimento, feito isso é gerado então um a integração que será
	 * consiliada com o Banco central e EUA.
	 */

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveInserirTransferenciaMovimentoEIntegracaoComSucesso() {
		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(clienteService.findById(100001L));
		transferencia.setDtTransferencia(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		transferencia.setVlTranferencia(new BigDecimal(800.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.FINALIZADA);
		transferencia.setBancoFavorecido(bancoService.findById(100001L));

		transferenciaService.save(transferencia);
		Assert.assertNotNull(transferencia.getId());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(700.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(1, movimentoService.getAll().size());
		Assert.assertEquals(1, integracaoService.getAll().size());

		movimentoService.getAll().stream().forEach(movimento -> {
			Assert.assertEquals(transferencia.getEmailFavorecido(), movimento.getEmailFavorecido());
			Assert.assertEquals(transferencia.getVlTranferencia(), movimento.getVlMovimento());
			Assert.assertEquals(movimento.getTipoMovimento(), TipoMovimento.SAIDA);
			Assert.assertNull(movimento.getAgendamento());
			Assert.assertEquals(movimento.getTipoTransacao(), TipoTransacao.TRANSFERENCIA);
		});

		integracaoService.getAll().stream().forEach(integracao -> {
			Assert.assertEquals(transferencia.getEmailFavorecido(), integracao.getEmailFavorecido());
			Assert.assertEquals(transferencia.getVlTranferencia(), integracao.getVlIntegracao());
			Assert.assertEquals(integracao.getTipoIntegracao(), TipoMovimento.SAIDA);
			Assert.assertNull(integracao.getAgendamento());
			Assert.assertEquals(integracao.getTipoTransacao(), TipoTransacao.TRANSFERENCIA);
		});

		movimentoService.getAll().stream().forEach(movimento -> movimentoService.remove(movimento.getId()));
		integracaoService.getAll().stream().forEach(integracao -> integracaoService.remove(integracao.getId()));

	}

	@Test(expected = EJBTransactionRolledbackException.class)
	@UsingDataSet("db/transferencia.xml")
	public void deveFalharAoInserirTransferencia() {
		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(clienteService.findById(100001L));
		transferencia.setDtTransferencia(Calendar.getInstance().getTime());
		transferencia.setVlTranferencia(new BigDecimal(1700.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.FINALIZADA);
		transferencia.setBancoFavorecido(bancoService.findById(100001L));

		transferenciaService.save(transferencia);
		Assert.assertNull(transferencia.getId());
	}

	@Test(expected = EJBTransactionRolledbackException.class)
	@UsingDataSet("db/transferencia.xml")
	public void deveFalharAoInserirTransferenciaDevidoAoHorario() {

		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(clienteService.findById(100001L));
		transferencia.setDtTransferencia(new GregorianCalendar(2000, 0, 1, 3, 30, 00).getTime());
		transferencia.setVlTranferencia(new BigDecimal(800.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.PENDENTE);
		transferencia.setBancoFavorecido(bancoService.findById(100001L));

		transferenciaService.save(transferencia);
		Assert.assertNull(transferencia.getId());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveInserirTransferenciaAgendamentoMovimentoIntegracaoComSucesso() {
		Agendamento agendamento = new Agendamento();
		agendamento.setCliente(clienteService.findById(100001L));
		agendamento.setDataAgendamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		agendamentoService.save(agendamento);
		Assert.assertNotNull(agendamento.getId());

		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(agendamento.getCliente());
		transferencia.setAgendamento(agendamento);
		transferencia.setVlTranferencia(new BigDecimal(800.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.PENDENTE);
		transferencia.setBancoFavorecido(bancoService.findById(100001L));
		transferenciaService.save(transferencia);

		Assert.assertNotNull(transferencia.getId());
		Assert.assertEquals(SituacaoTransferencia.PENDENTE, transferencia.getSituacaoTransferencia());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(1500.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(0, movimentoService.getAll().size());
		Assert.assertEquals(0, integracaoService.getAll().size());

		transferencia.setSituacaoTransferencia(SituacaoTransferencia.FINALIZADA);
		transferenciaService.save(transferencia);
		Assert.assertEquals(SituacaoTransferencia.FINALIZADA, transferencia.getSituacaoTransferencia());

		Assert.assertNotNull(transferencia.getId());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(700.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(1, movimentoService.getAll().size());
		Assert.assertEquals(1, integracaoService.getAll().size());

		movimentoService.getAll().stream().forEach(movimento -> {
			Assert.assertEquals(transferencia.getEmailFavorecido(), movimento.getEmailFavorecido());
			Assert.assertEquals(transferencia.getVlTranferencia(), movimento.getVlMovimento());
			Assert.assertEquals(movimento.getTipoMovimento(), TipoMovimento.SAIDA);
			Assert.assertNotNull(movimento.getAgendamento());
			Assert.assertEquals(movimento.getTipoTransacao(), TipoTransacao.TRANSFERENCIA);
		});

		integracaoService.getAll().stream().forEach(integracao -> {
			Assert.assertEquals(transferencia.getEmailFavorecido(), integracao.getEmailFavorecido());
			Assert.assertEquals(transferencia.getVlTranferencia(), integracao.getVlIntegracao());
			Assert.assertEquals(integracao.getTipoIntegracao(), TipoMovimento.SAIDA);
			Assert.assertNotNull(integracao.getAgendamento());
			Assert.assertEquals(integracao.getTipoTransacao(), TipoTransacao.TRANSFERENCIA);
		});

		movimentoService.getAll().stream().forEach(movimento -> movimentoService.remove(movimento.getId()));
		integracaoService.getAll().stream().forEach(integracao -> integracaoService.remove(integracao.getId()));
		transferenciaService.remove(transferencia.getId());
		agendamentoService.remove(agendamento.getId());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveCompararAgenciaFavorecidoTransferencia() {
		Transferencia transferencia = transferenciaService.findById(100001L);
		Assert.assertEquals("4455668", transferencia.getAgenciaFavorecido());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, transferenciaService.getAll().size());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveAtualizarNrContaFavorecidoComSucesso() {
		Transferencia transferencia = transferenciaService.findById(100001L);
		transferencia.setNrContaFavorecido("4585698");
		transferenciaService.save(transferencia);
		Assert.assertEquals(0, movimentoService.getAll().size());
		Assert.assertEquals(0, integracaoService.getAll().size());
		Assert.assertEquals("4585698", transferenciaService.findById(100001L).getNrContaFavorecido());
		transferenciaService.remove(transferencia.getId());
	}
}
