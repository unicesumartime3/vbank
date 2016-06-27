package br.com.rp.repository.service;

import java.math.BigDecimal;
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
import br.com.rp.domain.Pagamento;
import br.com.rp.domain.SituacaoPagamento;
import br.com.rp.domain.TipoConta;
import br.com.rp.domain.TipoMovimento;
import br.com.rp.domain.TipoTransacao;
import br.com.rp.services.AgendamentoService;
import br.com.rp.services.BancoService;
import br.com.rp.services.ClienteService;
import br.com.rp.services.IntegracaoService;
import br.com.rp.services.MovimentoService;
import br.com.rp.services.PagamentoService;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class PagamentoServiceTest extends AbstractTest {

	@EJB
	private PagamentoService pagamentoService;

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
	@UsingDataSet("db/pagamento.xml")
	public void deveInserirPagamentoComSucesso() {
		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(clienteService.findById(100001L));
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setDtPagamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		pagamento.setSituacaoPagamento(SituacaoPagamento.PENDENTE);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(800.00));

		pagamentoService.save(pagamento);
		Assert.assertNotNull(pagamento.getId());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(1500.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento.getClienteRemetente().getConta().getVlSaldoContaCorrente());
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
	@UsingDataSet("db/pagamento.xml")
	public void deveInserirPagamentoMovimentoEIntegracaoComSucesso() {
		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(clienteService.findById(100001L));
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setDtPagamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		pagamento.setSituacaoPagamento(SituacaoPagamento.FINALIZADO);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(800.00));

		pagamentoService.save(pagamento);
		Assert.assertNotNull(pagamento.getId());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(700.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(1, movimentoService.getAll().size());
		Assert.assertEquals(1, integracaoService.getAll().size());

		movimentoService.getAll().stream().forEach(movimento -> {
			Assert.assertEquals(pagamento.getCodigoBarra(), movimento.getCodigoBarra());
			Assert.assertEquals(pagamento.getVlPagamento(), movimento.getVlMovimento());
			Assert.assertEquals(movimento.getTipoMovimento(), TipoMovimento.SAIDA);
			Assert.assertNull(movimento.getAgendamento());
			Assert.assertEquals(movimento.getTipoTransacao(), TipoTransacao.PAGAMENTO);
		});

		integracaoService.getAll().stream().forEach(integracao -> {
			Assert.assertEquals(pagamento.getCodigoBarra(), integracao.getCodigoBarra());
			Assert.assertEquals(pagamento.getVlPagamento(), integracao.getVlIntegracao());
			Assert.assertEquals(integracao.getTipoIntegracao(), TipoMovimento.SAIDA);
			Assert.assertNull(integracao.getAgendamento());
			Assert.assertEquals(integracao.getTipoTransacao(), TipoTransacao.PAGAMENTO);
		});

		movimentoService.getAll().stream().forEach(movimento -> movimentoService.remove(movimento.getId()));
		integracaoService.getAll().stream().forEach(integracao -> integracaoService.remove(integracao.getId()));

	}

	@Test(expected = EJBTransactionRolledbackException.class)
	@UsingDataSet("db/pagamento.xml")
	public void deveFalharAoInserirPagamento() {
		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(clienteService.findById(100001L));
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setDtPagamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		pagamento.setSituacaoPagamento(SituacaoPagamento.FINALIZADO);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(1700.00));

		pagamentoService.save(pagamento);
		Assert.assertNotNull(pagamento.getId());
	}

	@Test(expected = EJBTransactionRolledbackException.class)
	@UsingDataSet("db/transferencia.xml")
	public void deveFalharAoInserirPagamentoDevidoAoHorario() {

		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(clienteService.findById(100001L));
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setDtPagamento(new GregorianCalendar(2000, 0, 1, 3, 30, 00).getTime());
		pagamento.setSituacaoPagamento(SituacaoPagamento.PENDENTE);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(800.00));

		pagamentoService.save(pagamento);
		Assert.assertNotNull(pagamento.getId());
	}

	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveInserirPagamentoAgendamentoMovimentoIntegracaoComSucesso() {
		Agendamento agendamento = new Agendamento();
		agendamento.setCliente(clienteService.findById(100001L));
		agendamento.setDataAgendamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		agendamentoService.save(agendamento);
		Assert.assertNotNull(agendamento.getId());

		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(clienteService.findById(100001L));
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setAgendamento(agendamento);
		pagamento.setSituacaoPagamento(SituacaoPagamento.PENDENTE);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(800.00));
		pagamentoService.save(pagamento);

		Assert.assertNotNull(pagamento.getId());
		Assert.assertEquals(SituacaoPagamento.PENDENTE, pagamento.getSituacaoPagamento());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(1500.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(0, movimentoService.getAll().size());
		Assert.assertEquals(0, integracaoService.getAll().size());

		pagamento.setSituacaoPagamento(SituacaoPagamento.FINALIZADO);
		pagamentoService.save(pagamento);
		Assert.assertEquals(SituacaoPagamento.FINALIZADO, pagamento.getSituacaoPagamento());

		Assert.assertNotNull(pagamento.getId());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(700.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(1, movimentoService.getAll().size());
		Assert.assertEquals(1, integracaoService.getAll().size());

		movimentoService.getAll().stream().forEach(movimento -> {
			Assert.assertEquals(pagamento.getCodigoBarra(), movimento.getCodigoBarra());
			Assert.assertEquals(pagamento.getVlPagamento(), movimento.getVlMovimento());
			Assert.assertEquals(movimento.getTipoMovimento(), TipoMovimento.SAIDA);
			Assert.assertNotNull(movimento.getAgendamento());
			Assert.assertEquals(movimento.getTipoTransacao(), TipoTransacao.PAGAMENTO);
		});

		integracaoService.getAll().stream().forEach(integracao -> {
			Assert.assertEquals(pagamento.getCodigoBarra(), integracao.getCodigoBarra());
			Assert.assertEquals(pagamento.getVlPagamento(), integracao.getVlIntegracao());
			Assert.assertEquals(integracao.getTipoIntegracao(), TipoMovimento.SAIDA);
			Assert.assertNotNull(integracao.getAgendamento());
			Assert.assertEquals(integracao.getTipoTransacao(), TipoTransacao.PAGAMENTO);
		});

		movimentoService.getAll().stream().forEach(movimento -> movimentoService.remove(movimento.getId()));
		integracaoService.getAll().stream().forEach(integracao -> integracaoService.remove(integracao.getId()));
		pagamentoService.remove(pagamento.getId());
		agendamentoService.remove(agendamento.getId());
	}

	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveCompararCodigoBarraPagamento() {
		Pagamento pagamento = pagamentoService.findById(100001L);
		Assert.assertEquals("45646465791564915460561450545915465165", pagamento.getCodigoBarra());
	}

	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, pagamentoService.getAll().size());
	}

	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveAtualizarCodigoBarraComSucesso() {
		Pagamento pagamento = pagamentoService.findById(100001L);
		pagamento.setCodigoBarra("4585698465484659814561854651458416");
		pagamentoService.save(pagamento);
		Assert.assertEquals(0, movimentoService.getAll().size());
		Assert.assertEquals(0, integracaoService.getAll().size());
		Assert.assertEquals("4585698465484659814561854651458416", pagamentoService.findById(100001L).getCodigoBarra());
		pagamentoService.remove(pagamento.getId());
	}
}
