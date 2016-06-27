package br.com.rp.rest;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Agendamento;
import br.com.rp.domain.Banco;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.Movimento;
import br.com.rp.domain.SituacaoTransferencia;
import br.com.rp.domain.TipoConta;
import br.com.rp.domain.TipoMovimento;
import br.com.rp.domain.TipoTransacao;
import br.com.rp.domain.Transferencia;
import br.com.rp.integration.Integracao;
import br.com.rp.services.IntegracaoService;
import br.com.rp.services.MovimentoService;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class TransferenciaRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";
	
	@EJB
	private MovimentoService movimentoService;

	@EJB
	private IntegracaoService integracaoService;
	
	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveInserirTransferenciaComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target;

		target = client.target(URL_BASE + "/cliente/findById/100001");
		Response responseGetCliente = target.request().get();
		Cliente cliente = responseGetCliente.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);
		Assert.assertEquals("Julio Serra", cliente.getNome());

		target = client.target(URL_BASE + "/banco/findById/100001");
		Response responseGetBanco = target.request().get();
		Banco banco = responseGetBanco.readEntity(Banco.class);
		Assert.assertNotNull(banco);
		Assert.assertEquals("Banco do Brasil", banco.getDescricao());

		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(cliente);
		transferencia.setDtTransferencia(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		transferencia.setVlTranferencia(new BigDecimal(800.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.PENDENTE);
		transferencia.setBancoFavorecido(banco);

		target = client.target(URL_BASE + "/transferencia/save");
		Response responseSave = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(transferencia, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(responseSave.getStatus()));

		Assert.assertNotNull(responseSave.readEntity(Transferencia.class).getId());

		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(1500.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());

		target = client.target(URL_BASE + "/movimento/getAll");
		Response responseGetAllMovimento = target.request().get();
		List<Movimento> listaMovimento = (List<Movimento>) responseGetAllMovimento.readEntity(List.class);
		Assert.assertEquals(0, listaMovimento.size());

		target = client.target(URL_BASE + "/integracao/getAll");
		Response responseGetAllIntegracao = target.request().get();
		List<Integracao> listaIntegracao = (List<Integracao>) responseGetAllIntegracao.readEntity(List.class);
		Assert.assertEquals(0, listaIntegracao.size());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveInserirTransferenciaMovimentoEIntegracaoComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target;

		target = client.target(URL_BASE + "/cliente/findById/100001");
		Response responseGetCliente = target.request().get();
		Cliente cliente = responseGetCliente.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);
		Assert.assertEquals("Julio Serra", cliente.getNome());

		target = client.target(URL_BASE + "/banco/findById/100001");
		Response responseGetBanco = target.request().get();
		Banco banco = responseGetBanco.readEntity(Banco.class);
		Assert.assertNotNull(banco);
		Assert.assertEquals("Banco do Brasil", banco.getDescricao());

		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(cliente);
		transferencia.setDtTransferencia(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		transferencia.setVlTranferencia(new BigDecimal(800.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.FINALIZADA);
		transferencia.setBancoFavorecido(banco);

		target = client.target(URL_BASE + "/transferencia/save");
		Response responseSave = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(transferencia, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(responseSave.getStatus()));

		Assert.assertNotNull(responseSave.readEntity(Transferencia.class).getId());

		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(1500.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());

		target = client.target(URL_BASE + "/movimento/getAll");
		Response responseGetAllMovimento = target.request().get();
		List<Movimento> listaMovimento = (List<Movimento>) responseGetAllMovimento.readEntity(List.class);
		Assert.assertEquals(1, listaMovimento.size());

		target = client.target(URL_BASE + "/integracao/getAll");
		Response responseGetAllIntegracao = target.request().get();
		List<Integracao> listaIntegracao = (List<Integracao>) responseGetAllIntegracao.readEntity(List.class);
		Assert.assertEquals(1, listaIntegracao.size());
					
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
	
	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveInserirTransferenciaAgendamentoComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);
		
		Agendamento agendamento = new Agendamento();
		agendamento.setCliente(cliente);
		agendamento.setDataAgendamento(new GregorianCalendar(2000, 0, 1, 15, 30, 00).getTime());
		target = client.target(URL_BASE + "/agendamento/save");
		response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(agendamento, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		agendamento = response.readEntity(Agendamento.class);
		Assert.assertNotNull(agendamento.getId());
		
		target = client.target(URL_BASE + "/banco/findById/100001");
		Response responseGetBanco = target.request().get();
		Banco banco = responseGetBanco.readEntity(Banco.class);
		Assert.assertNotNull(banco);
		Assert.assertEquals("Banco do Brasil", banco.getDescricao());
		
		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(cliente);
		transferencia.setAgendamento(agendamento);
		transferencia.setVlTranferencia(new BigDecimal(800.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.PENDENTE);
		transferencia.setBancoFavorecido(banco);

		target = client.target(URL_BASE + "/transferencia/save");
		Response responseSave = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(transferencia, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(responseSave.getStatus()));	
		Assert.assertNotNull(responseSave.readEntity(Transferencia.class).getId());

		Assert.assertNotEquals(new BigDecimal(1000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		Assert.assertEquals(new BigDecimal(1500.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				transferencia.getClienteRemetente().getConta().getVlSaldoContaCorrente());
		
		target = client.target(URL_BASE + "/movimento/getAll");
		Response responseGetAllMovimento = target.request().get();
		List<Movimento> listaMovimento = (List<Movimento>) responseGetAllMovimento.readEntity(List.class);
		Assert.assertEquals(0, listaMovimento.size());

		target = client.target(URL_BASE + "/integracao/getAll");
		Response responseGetAllIntegracao = target.request().get();
		List<Integracao> listaIntegracao = (List<Integracao>) responseGetAllIntegracao.readEntity(List.class);
		Assert.assertEquals(0, listaIntegracao.size());
		
	}
	
	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveRetornarDoisRegistros() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/transferencia/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Transferencia> transferencias = (List<Transferencia>) response.readEntity(List.class);
		Assert.assertEquals(2, transferencias.size());
	}

}
