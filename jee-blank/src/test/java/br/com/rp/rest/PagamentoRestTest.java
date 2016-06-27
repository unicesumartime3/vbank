package br.com.rp.rest;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.Movimento;
import br.com.rp.domain.Pagamento;
import br.com.rp.domain.SituacaoPagamento;
import br.com.rp.domain.TipoConta;
import br.com.rp.domain.TipoMovimento;
import br.com.rp.domain.TipoTransacao;
import br.com.rp.integration.Integracao;
import br.com.rp.services.IntegracaoService;
import br.com.rp.services.MovimentoService;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class PagamentoRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@EJB
	private IntegracaoService integracaoService;

	@EJB
	private MovimentoService movimentoService;

	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveSalvarPagamentoComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);

		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(cliente);
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setDtPagamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		pagamento.setSituacaoPagamento(SituacaoPagamento.PENDENTE);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(800.00));

		target = client.target(URL_BASE + "/pagamento/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(pagamento, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Pagamento pagamento2 = response.readEntity(Pagamento.class);
		Assert.assertNotNull(pagamento2.getId());

		target = client.target(URL_BASE + "/movimento/getAll");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Movimento> movimentos = (List<Movimento>) response.readEntity(List.class);
		Assert.assertEquals(0, movimentos.size());

		target = client.target(URL_BASE + "/integracao/getAll");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Integracao> integracoes = (List<Integracao>) response.readEntity(List.class);
		Assert.assertEquals(0, integracoes.size());

	}

	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveInserirPagamentoMovimentoEIntegracaoComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);

		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(cliente);
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setDtPagamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		pagamento.setSituacaoPagamento(SituacaoPagamento.FINALIZADO);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(800.00));

		target = client.target(URL_BASE + "/pagamento/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(pagamento, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Pagamento pagamento2 = response.readEntity(Pagamento.class);
		Assert.assertNotNull(pagamento2.getId());
		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento2.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(700.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				pagamento2.getClienteRemetente().getConta().getVlSaldoContaCorrente());

		target = client.target(URL_BASE + "/movimento/getAll");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Movimento> movimentos = (List<Movimento>) response.readEntity(List.class);
		Assert.assertEquals(1, movimentos.size());

		target = client.target(URL_BASE + "/integracao/getAll");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Integracao> integracoes = (List<Integracao>) response.readEntity(List.class);
		Assert.assertEquals(1, integracoes.size());

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

	@Test(expected = Exception.class)
	@UsingDataSet("db/pagamento.xml")
	public void deveFalharAoInserirPagamento() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);

		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(cliente);
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setDtPagamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		pagamento.setSituacaoPagamento(SituacaoPagamento.FINALIZADO);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(1700.00));

		target = client.target(URL_BASE + "/pagamento/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(pagamento, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(500), Integer.valueOf(response.getStatus()));
		Pagamento pagamento2 = response.readEntity(Pagamento.class);
		Assert.assertNotNull(pagamento2.getId());
	}
	
	@Test(expected = Exception.class)
	@UsingDataSet("db/transferencia.xml")
	public void deveFalharAoInserirPagamentoDevidoAoHorario() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);
		
		Pagamento pagamento = new Pagamento();
		pagamento.setClienteRemetente(cliente);
		pagamento.setCodigoBarra("1564651654056406501651654615640606");
		pagamento.setDtPagamento(new GregorianCalendar(2000, 0, 1, 3, 30, 00).getTime());
		pagamento.setSituacaoPagamento(SituacaoPagamento.PENDENTE);
		pagamento.setTipoContaDebito(TipoConta.CORRENTE);
		pagamento.setVlPagamento(new BigDecimal(800.00));

		target = client.target(URL_BASE + "/pagamento/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(pagamento, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(500), Integer.valueOf(response.getStatus()));
		Pagamento pagamento2 = response.readEntity(Pagamento.class);
		Assert.assertNotNull(pagamento2.getId());
	}
	
	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveCompararCodigoBarraPagamento() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/pagamento/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Pagamento pagamento = response.readEntity(Pagamento.class);
		Assert.assertEquals("45646465791564915460561450545915465165", pagamento.getCodigoBarra());
	}
	
	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveRetornarDoisRegistros() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/pagamento/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Pagamento> pagamentos = (List<Pagamento>) response.readEntity(List.class);
		Assert.assertEquals(2, pagamentos.size());
	}
	
	@Test
	@UsingDataSet("db/pagamento.xml")
	public void deveAtualizarCodigoBarraComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/pagamento/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Pagamento pagamento = response.readEntity(Pagamento.class);
		
		pagamento.setCodigoBarra("4585698465484659814561854651458416");
		
		target = client.target(URL_BASE + "/pagamento/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(pagamento, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Pagamento pagamento2 = response.readEntity(Pagamento.class);
		Assert.assertNotNull(pagamento2.getId());
		
		target = client.target(URL_BASE + "/movimento/getAll");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Movimento> movimentos = (List<Movimento>) response.readEntity(List.class);
		Assert.assertEquals(0, movimentos.size());

		target = client.target(URL_BASE + "/integracao/getAll");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Integracao> integracoes = (List<Integracao>) response.readEntity(List.class);
		Assert.assertEquals(0, integracoes.size());
		
		Assert.assertEquals("4585698465484659814561854651458416", pagamento.getCodigoBarra());
		
		client = ClientBuilder.newClient();
		target = client.target(URL_BASE + "/pagamento/remove/100001");
		response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
	}

}
