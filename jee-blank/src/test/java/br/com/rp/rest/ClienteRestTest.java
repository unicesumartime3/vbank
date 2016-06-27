package br.com.rp.rest;

import java.math.BigDecimal;
import java.text.ParseException;
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
import br.com.rp.domain.Cliente;
import br.com.rp.domain.Conta;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class ClienteRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveSalvarClienteComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/conta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Conta conta = response.readEntity(Conta.class);
		Assert.assertNotNull(conta);

		target = client.target(URL_BASE + "/cliente/save");
		response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(
				new Cliente("Julio Serra", "21717048455", "julio@gmail.com",new BigDecimal(8000.00), conta), MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);
		Assert.assertEquals("Julio Serra", cliente.getNome());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveAlterarClienteComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertEquals("Julio Serra", cliente.getNome());

		target = client.target(URL_BASE + "/cliente/update/100001");
		response = target.request()
				.put(Entity.entity(
						new Cliente("Rafael Suzin", cliente.getCpf(), cliente.getEmail(), cliente.getVlRenda(), cliente.getConta()),
						MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente clienteResult = response.readEntity(Cliente.class);
		Assert.assertNotNull(clienteResult);
		Assert.assertEquals("Rafael Suzin", clienteResult.getNome());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRemoverClienteComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/cliente/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNull(cliente);
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRetornarCliente() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveCompararClienteNome() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertEquals("Julio Serra", cliente.getNome());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRetornaDoisCliente() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Cliente> clientes = (List<Cliente>) response.readEntity(List.class);
		Assert.assertEquals(2, clientes.size());
	}

}
