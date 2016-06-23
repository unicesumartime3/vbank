package br.com.rp.rest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
import br.com.rp.domain.Proposta;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class PropostaRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	public void deveSalvarPropostaComSucesso() throws ParseException {
		Proposta proposta = new Proposta();
		proposta.setCpf("74912901164");
		proposta.setDataProposta(new SimpleDateFormat("yyyy-MM-dd").parse("2016-06-20"));
		proposta.setNome("Rafael");
		proposta.setRegiao("Nordeste");
		proposta.setRenda(new BigDecimal(8000.00));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/save");
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(proposta, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta prop = response.readEntity(Proposta.class);
		Assert.assertNotNull(prop);
		Assert.assertEquals("74912901164", prop.getCpf());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveAlterarPropostaComSucesso() throws ParseException {
		Client client2 = ClientBuilder.newClient();
		WebTarget target2 = client2.target(URL_BASE + "/proposta/findById/100001");
		Response response2 = target2.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response2.getStatus()));
		Proposta proposta2 = response2.readEntity(Proposta.class);
		Assert.assertEquals("João", proposta2.getNome());

		Proposta proposta = new Proposta();
		proposta.setCpf("25283798143");
		proposta.setDataProposta(new SimpleDateFormat("yyyy-MM-dd").parse("2016-06-20"));
		proposta.setNome("Rafael");
		proposta.setRegiao("Nordeste");
		proposta.setRenda(new BigDecimal(8000.00));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/update/100001");
		Response response = target.request().put(Entity.entity(proposta, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta prop = response.readEntity(Proposta.class);
		Assert.assertNotNull(prop);
		Assert.assertEquals("Rafael", prop.getNome());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRemoverPropostaComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		Client client2 = ClientBuilder.newClient();
		WebTarget target2 = client2.target(URL_BASE + "/proposta/findById/100001");
		Response response2 = target2.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response2.getStatus()));
		Proposta proposta = response2.readEntity(Proposta.class);
		Assert.assertNull(proposta);
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRetornarProposta() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = response.readEntity(Proposta.class);
		Assert.assertNotNull(proposta);
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveCompararPropostaCpf() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = response.readEntity(Proposta.class);
		Assert.assertEquals("25283798143", proposta.getCpf());
	}

	@Test
	@UsingDataSet("db/proposta_listagem.xml")
	public void deveRetornaQuatroPropostas() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Proposta> propostas = (List<Proposta>) response.readEntity(List.class);
		Assert.assertEquals(4, propostas.size());
	}
}