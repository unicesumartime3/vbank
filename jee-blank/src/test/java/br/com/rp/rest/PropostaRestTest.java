package br.com.rp.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Proposta;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class PropostaRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	public void deveSalvarPropostaComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/save/09006848956/2016-06-20/Rafael/Nordeste/800.00");
		Response response = target.request().post(null);
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = (Proposta) response.readEntity(Proposta.class);
		Assert.assertNotNull(proposta);
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRetornarProposta() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/get/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = (Proposta) response.readEntity(Proposta.class);
		Assert.assertNotNull(proposta);
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveCompararPropostaCpf() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/get/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = (Proposta) response.readEntity(Proposta.class);
		Assert.assertEquals("25283798143", proposta.getCpf());
	}
}
