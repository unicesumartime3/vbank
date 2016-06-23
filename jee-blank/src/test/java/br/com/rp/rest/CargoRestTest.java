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

public class CargoRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	public void deveSalvarCargoComSucesso() throws ParseException {
		Cargo cargoParan = new Cargo();
		cargoParan.setDescricaoCargo("Gerente de TI");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cargo/save");
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(cargoParan, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cargo cargo = response.readEntity(Cargo.class);
		Assert.assertNotNull(cargo);
		Assert.assertEquals("Gerente de TI", cargo.getDescricaoCargo());
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveAlterarCargoComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cargo/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cargo cargo = response.readEntity(Cargo.class);
		Assert.assertEquals("Gerente de Vendas", cargo.getDescricaoCargo());

		Cargo cargoParan = new Cargo();
		cargoParan.setDescricaoCargo("Gerente de Compras");

		target = client.target(URL_BASE + "/cargo/update/100001");
		response = target.request().put(Entity.entity(cargoParan, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cargo cargoResult = response.readEntity(Cargo.class);
		Assert.assertNotNull(cargoResult);
		Assert.assertEquals("Gerente de Compras", cargoResult.getDescricaoCargo());
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveRemoverCargoComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cargo/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/cargo/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		Cargo cargo = response.readEntity(Cargo.class);
		Assert.assertNull(cargo);
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveRetornarCargo() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cargo/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cargo cargo = response.readEntity(Cargo.class);
		Assert.assertNotNull(cargo);
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveCompararCargoDescricao() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cargo/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cargo cargo = response.readEntity(Cargo.class);
		Assert.assertEquals("Gerente de Vendas", cargo.getDescricaoCargo());
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveRetornaDoisCargos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cargo/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Cargo> cargos = (List<Cargo>) response.readEntity(List.class);
		Assert.assertEquals(2, cargos.size());
	}

}
