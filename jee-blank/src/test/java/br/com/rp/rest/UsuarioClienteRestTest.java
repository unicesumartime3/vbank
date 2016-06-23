 package br.com.rp.rest;

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
import br.com.rp.domain.UsuarioCliente;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class UsuarioClienteRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveSalvarUsuarioClienteComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);

		target = client.target(URL_BASE + "/usuario/cliente/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new UsuarioCliente("Flavia Ferreira", "123456", cliente),
						MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioCliente usuarioCliente = response.readEntity(UsuarioCliente.class);
		Assert.assertNotNull(usuarioCliente);
		Assert.assertEquals("Flavia Ferreira", usuarioCliente.getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveAlterarUsuarioClienteComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioCliente usuarioCliente = response.readEntity(UsuarioCliente.class);
		Assert.assertEquals("Christian Marchiori", usuarioCliente.getNome());

		target = client.target(URL_BASE + "/cliente/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);

		target = client.target(URL_BASE + "/usuario/cliente/update/100001");
		response = target.request()
				.put(Entity.entity(new UsuarioCliente("Flavia Ferreira","123456", cliente),
						MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioCliente usuarioClienteResult = response.readEntity(UsuarioCliente.class);
		Assert.assertNotNull(usuarioClienteResult);
		Assert.assertEquals("Flavia Ferreira", usuarioClienteResult.getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRemoverUsuarioClienteComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/cliente/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/usuario/cliente/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		UsuarioCliente usuarioCliente = response.readEntity(UsuarioCliente.class);
		Assert.assertNull(usuarioCliente);
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRetornarUsuarioCliente() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioCliente usuarioCliente = response.readEntity(UsuarioCliente.class);
		Assert.assertNotNull(usuarioCliente);
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararUsuarioClienteNome() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioCliente usuarioCliente = response.readEntity(UsuarioCliente.class);
		Assert.assertEquals("Christian Marchiori", usuarioCliente.getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRetornaDoisUsuariosClientes() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/cliente/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<UsuarioCliente> usuariosCliente = (List<UsuarioCliente>) response.readEntity(List.class);
		Assert.assertEquals(2, usuariosCliente.size());
	}
}
