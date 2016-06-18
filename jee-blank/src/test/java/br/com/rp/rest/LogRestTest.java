package br.com.rp.rest;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Log;

public class LogRestTest extends AbstractTest {

	private static final String URL = "http://localhost:8080/vbank/api/log";

	@Test
	@UsingDataSet("db/log.xml")
	public void deveRetornar2LogsPeloRest() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL);
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Log> logs = (List<Log>) response.readEntity(List.class);
		Assert.assertEquals(2, logs.size());
	}

}
