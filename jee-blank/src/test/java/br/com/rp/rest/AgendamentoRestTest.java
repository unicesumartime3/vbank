package br.com.rp.rest;

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
import br.com.rp.domain.Agendamento;
import br.com.rp.domain.Cliente;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class AgendamentoRestTest extends AbstractTest {
	
	private static final String URL_BASE = "http://localhost:8080/vbank/api";
	
	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveSalvarAgendamentoComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/cliente/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Cliente cliente = response.readEntity(Cliente.class);
		Assert.assertNotNull(cliente);

		Agendamento agendamento = new Agendamento();
		agendamento.setDataAgendamento(new SimpleDateFormat("dd-mm-yyyy").parse("24-05-2016"));
		agendamento.setCliente(cliente);
		target = client.target(URL_BASE + "/agendamento/save");
		response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(agendamento, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		agendamento = response.readEntity(Agendamento.class);
		Assert.assertNotNull(agendamento);
		Assert.assertEquals("Julio Serra", cliente.getNome());
	}
	
	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveAlterarAgendamentoComSucesso() throws ParseException {
		Client client2 = ClientBuilder.newClient();
		WebTarget target2 = client2.target(URL_BASE + "/agendamento/findById/100001");
		Response response2 = target2.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response2.getStatus()));
		Agendamento agendamento = response2.readEntity(Agendamento.class);
		Assert.assertNotNull(agendamento);

		Agendamento agendamento2 = new Agendamento();
		agendamento2.setDataAgendamento(new SimpleDateFormat("yyyy-MM-dd").parse("2016-06-20"));
		agendamento2.setCliente(agendamento.getCliente());

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/agendamento/update/100001");
		Response response = target.request().put(Entity.entity(agendamento2, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Agendamento agend = response.readEntity(Agendamento.class);
		Assert.assertNotNull(agend);
		Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2016-06-20"), agend.getDataAgendamento());
	}
	
	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveRemoverAgendamentoComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/agendamento/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		Client client2 = ClientBuilder.newClient();
		WebTarget target2 = client2.target(URL_BASE + "/agendamento/findById/100001");
		Response response2 = target2.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response2.getStatus()));
		Agendamento agendamento = response2.readEntity(Agendamento.class);
		Assert.assertNull(agendamento);
	}
	
	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveRetornarAgendamento() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/agendamento/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Agendamento agendamento = response.readEntity(Agendamento.class);
		Assert.assertNotNull(agendamento);
	}
	
	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveRetornaDoisAgendamentos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/agendamento/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Agendamento> agendamentos = (List<Agendamento>) response.readEntity(List.class);
		Assert.assertEquals(2, agendamentos.size());
	}

}
