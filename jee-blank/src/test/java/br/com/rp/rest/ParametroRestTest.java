package br.com.rp.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import br.com.rp.domain.Parametro;

public class ParametroRestTest extends AbstractTest {
	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	public void deveSalvarParametroComSucesso() throws ParseException {
		Parametro parametroParan = new Parametro();
		parametroParan.setHoraFimTransacoes(Calendar.getInstance().getTime());
		parametroParan.setHoraInicioTransacoes(Calendar.getInstance().getTime());
		parametroParan.setIntervaloIntegracaoBancoCentral(10L);
		parametroParan.setHorarioIntegracaoEUA(new SimpleDateFormat("hh:mm:ss").parse("23:50:00"));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/parametro/save");
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(parametroParan, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Parametro parametro = response.readEntity(Parametro.class);
		Assert.assertNotNull(parametro);
		Assert.assertEquals(new Long(10), parametro.getIntervaloIntegracaoBancoCentral());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveAlterarParametroComSucesso() throws ParseException {
		Parametro parametroParan1 = new Parametro();
		parametroParan1.setHoraFimTransacoes(Calendar.getInstance().getTime());
		parametroParan1.setHoraInicioTransacoes(Calendar.getInstance().getTime());
		parametroParan1.setIntervaloIntegracaoBancoCentral(10L);
		parametroParan1.setHorarioIntegracaoEUA(new SimpleDateFormat("hh:mm:ss").parse("23:50:00"));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/parametro/save");
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(parametroParan1, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Parametro parametro1 = response.readEntity(Parametro.class);
		Assert.assertNotNull(parametro1);
		Assert.assertEquals(new Long(10), parametro1.getIntervaloIntegracaoBancoCentral());

		Parametro parametroParan = new Parametro();
		parametroParan.setHoraFimTransacoes(parametro1.getHoraFimTransacoes());
		parametroParan.setHoraInicioTransacoes(parametro1.getHoraInicioTransacoes());
		parametroParan.setIntervaloIntegracaoBancoCentral(15L);
		parametroParan.setHorarioIntegracaoEUA(parametro1.getHorarioIntegracaoEUA());

		target = client.target(URL_BASE + "/parametro/update/100001");
		response = target.request().put(Entity.entity(parametroParan, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Parametro p = response.readEntity(Parametro.class);
		Assert.assertEquals(new Long(15), p.getIntervaloIntegracaoBancoCentral());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveRemoverParametroComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/parametro/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/parametro/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		Parametro parametro = response.readEntity(Parametro.class);
		Assert.assertNull(parametro);
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveRetornarParametro() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/parametro/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
	}
	

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveRetornaDoisParametros() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/parametro/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Parametro> parametros = (List<Parametro>) response.readEntity(List.class);
		Assert.assertEquals(2, parametros.size());
	}
}
