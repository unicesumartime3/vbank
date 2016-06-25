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
import br.com.rp.domain.Banco;

public class BancoRestTest extends AbstractTest {
	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	public void deveSalvarBancoComSucesso() throws ParseException {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/banco/save");
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new Banco("Banco do Brasil"), MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Banco banco = response.readEntity(Banco.class);
		Assert.assertNotNull(banco);
		Assert.assertEquals("Banco do Brasil", banco.getDescricao());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveAlterarBancoComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/banco/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Banco banco = response.readEntity(Banco.class);
		Assert.assertEquals("Banco do Brasil", banco.getDescricao());

		target = client.target(URL_BASE + "/banco/update/100001");
		response = target.request().put(Entity.entity(new Banco("HSBC"), MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Banco bancoResult = response.readEntity(Banco.class);
		Assert.assertNotNull(bancoResult);
		Assert.assertEquals("HSBC", bancoResult.getDescricao());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveRemoverBancoComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/banco/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/banco/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		Banco banco = response.readEntity(Banco.class);
		Assert.assertNull(banco);
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveRetornarBanco() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/banco/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Banco banco = response.readEntity(Banco.class);
		Assert.assertNotNull(banco);
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveCompararBancoDescricao() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/banco/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Banco banco = response.readEntity(Banco.class);
		Assert.assertEquals("Banco do Brasil", banco.getDescricao());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveRetornaDoisBancos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/banco/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Banco> bancos = (List<Banco>) response.readEntity(List.class);
		Assert.assertEquals(2, bancos.size());
	}
}
