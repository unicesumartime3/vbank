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
import br.com.rp.domain.Funcionario;
import br.com.rp.domain.UsuarioFuncionario;

public class UsuarioFuncionarioRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveSalvarUsuarioFuncionarioComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/funcionario/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Funcionario funcionario = response.readEntity(Funcionario.class);
		Assert.assertNotNull(funcionario);

		target = client.target(URL_BASE + "/usuario/funcionario/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new UsuarioFuncionario("Flavia Ferreira", "flavia", "123456", funcionario),
						MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioFuncionario usuarioFuncionario = response.readEntity(UsuarioFuncionario.class);
		Assert.assertNotNull(usuarioFuncionario);
		Assert.assertEquals("Flavia Ferreira", usuarioFuncionario.getNome());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveAlterarUsuarioFuncionarioComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/funcionario/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioFuncionario usuarioFuncionario = response.readEntity(UsuarioFuncionario.class);
		Assert.assertEquals("Rafael Suzin", usuarioFuncionario.getNome());

		target = client.target(URL_BASE + "/funcionario/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Funcionario funcionario = response.readEntity(Funcionario.class);
		Assert.assertNotNull(funcionario);

		target = client.target(URL_BASE + "/usuario/funcionario/update/100001");
		response = target.request()
				.put(Entity.entity(new UsuarioFuncionario("Flavia Ferreira", "flavia", "123456", funcionario),
						MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioFuncionario usuarioFuncionarioResult = response.readEntity(UsuarioFuncionario.class);
		Assert.assertNotNull(usuarioFuncionarioResult);
		Assert.assertEquals("Flavia Ferreira", usuarioFuncionarioResult.getNome());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveRemoverUsuarioFuncionarioComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/funcionario/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/usuario/funcionario/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		UsuarioFuncionario usuarioFuncionario = response.readEntity(UsuarioFuncionario.class);
		Assert.assertNull(usuarioFuncionario);
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveRetornarUsuarioFuncionario() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/funcionario/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioFuncionario usuarioFuncionario = response.readEntity(UsuarioFuncionario.class);
		Assert.assertNotNull(usuarioFuncionario);
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveCompararUsuarioFuncionarioNome() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/funcionario/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		UsuarioFuncionario usuarioFuncionario = response.readEntity(UsuarioFuncionario.class);
		Assert.assertEquals("Rafael Suzin", usuarioFuncionario.getNome());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveRetornaDoisUsuariosFuncionario() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/usuario/funcionario/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<UsuarioFuncionario> usuariosfuncionario = (List<UsuarioFuncionario>) response.readEntity(List.class);
		Assert.assertEquals(2, usuariosfuncionario.size());
	}
}
