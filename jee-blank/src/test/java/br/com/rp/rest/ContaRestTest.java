package br.com.rp.rest;

import java.text.ParseException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Conta;

public class ContaRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	public void deveSalvarContaComSucesso() throws ParseException {
		Conta contaParan = new Conta();
		contaParan.setIsContaCorrente(true);
		contaParan.setIsContaPoupanca(false);
		contaParan.setNrConta("15648-2");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/conta/save");
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(contaParan, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Conta conta = response.readEntity(Conta.class);
		Assert.assertNotNull(conta);
		Assert.assertEquals("15648-2", conta.getNrConta());
	}

	@Test
	@UsingDataSet("db/conta.xml")
	public void deveAlterarContaComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/conta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Conta conta = response.readEntity(Conta.class);
		Assert.assertEquals("12345", conta.getNrConta());

		Conta contaParan = new Conta();
		contaParan.setIsContaCorrente(true);
		contaParan.setIsContaPoupanca(false);
		contaParan.setNrConta("15648-2");

		target = client.target(URL_BASE + "/conta/update/100001");
		response = target.request().put(Entity.entity(conta, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Conta contaResult = response.readEntity(Conta.class);
		Assert.assertNotNull(contaResult);
		Assert.assertEquals("12345", contaResult.getNrConta());
	}

	@Test
	@UsingDataSet("db/conta.xml")
	public void deveRemoverContaComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/conta/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/conta/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		Conta conta = response.readEntity(Conta.class);
		Assert.assertNull(conta);
	}

	@Test
	@UsingDataSet("db/conta.xml")
	public void deveRetornarConta() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/conta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Conta conta = response.readEntity(Conta.class);
		Assert.assertNotNull(conta);
	}

	@Test
	@UsingDataSet("db/conta.xml")
	public void deveCompararContaNrConta() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/conta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Conta conta = response.readEntity(Conta.class);
		Assert.assertEquals("12345", conta.getNrConta());
	}

	@Test
	@UsingDataSet("db/conta.xml")
	public void deveRetornaDuasContas() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/conta/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Conta> contas = (List<Conta>) response.readEntity(List.class);
		Assert.assertEquals(2, contas.size());
	}
}
