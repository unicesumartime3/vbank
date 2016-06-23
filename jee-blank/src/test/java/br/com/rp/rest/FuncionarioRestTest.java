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
import br.com.rp.domain.Cargo;
import br.com.rp.domain.Funcionario;

public class FuncionarioRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveSalvarFuncionarioComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cargo/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cargo cargo = response.readEntity(Cargo.class);
		Assert.assertNotNull(cargo);

		Funcionario funcionarioParan = new Funcionario();
		funcionarioParan.setCpf("09006848956");
		funcionarioParan.setNome("Julio Serra");
		funcionarioParan.setCargo(cargo);

		target = client.target(URL_BASE + "/funcionario/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(funcionarioParan, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Funcionario funcionario = response.readEntity(Funcionario.class);
		Assert.assertNotNull(funcionario);
		Assert.assertEquals("Julio Serra", funcionario.getNome());
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveAlterarFuncionarioComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/funcionario/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Funcionario funcionario = response.readEntity(Funcionario.class);
		Assert.assertEquals("Rafael Suzin", funcionario.getNome());

		target = client.target(URL_BASE + "/cargo/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cargo cargo = response.readEntity(Cargo.class);
		Assert.assertNotNull(cargo);

		Funcionario funcionarioParan = new Funcionario();
		funcionarioParan.setCpf("09006848956");
		funcionarioParan.setNome("Julio Serra");
		funcionarioParan.setCargo(cargo);

		target = client.target(URL_BASE + "/funcionario/update/100001");
		response = target.request().put(Entity.entity(funcionarioParan, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Funcionario funcionarioResult = response.readEntity(Funcionario.class);
		Assert.assertNotNull(funcionarioResult);
		Assert.assertEquals("Julio Serra", funcionarioResult.getNome());
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveRemoverFuncionarioComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/funcionario/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/funcionario/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		Funcionario funcionario = response.readEntity(Funcionario.class);
		Assert.assertNull(funcionario);
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveRetornarFuncionario() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/funcionario/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Funcionario funcionario = response.readEntity(Funcionario.class);
		Assert.assertNotNull(funcionario);
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveCompararFuncionarioNome() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/funcionario/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Funcionario funcionario = response.readEntity(Funcionario.class);
		Assert.assertEquals("Rafael Suzin", funcionario.getNome());
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveRetornaDoisFuncionarios() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/funcionario/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Funcionario> funcionarios = (List<Funcionario>)  response.readEntity(List.class);
		Assert.assertEquals(2, funcionarios.size());
	}

}
