package br.com.rp.rest;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Email;
import br.com.rp.domain.Proposta;
import br.com.rp.domain.SituacaoEmail;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class EmailRestTest extends AbstractTest {
	
	private static final String URL_BASE = "http://localhost:8080/vbank/api";
	
	@Test
	@UsingDataSet("db/email.xml")
	public void deveSalvarEmailComSucesso() throws ParseException{
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = response.readEntity(Proposta.class);
		Assert.assertEquals("João", proposta.getNome());
		
		Email email = new Email();
		email.setAssunto("Email para cliente");
		email.setDescricao("Este email é para envio ao cliente.");
		email.setDestinatario("email@email.com");
		email.setDhEnvio(Calendar.getInstance().getTime());
		email.setRemetente("serra@gmail.com");
		email.setSituacao(SituacaoEmail.AGUARDANDO_ENVIO);
		email.setProposta(proposta);
		
		client = ClientBuilder.newClient();
		target = client.target(URL_BASE + "/email/save");
		response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(email, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Email emailResponse = response.readEntity(Email.class);
		Assert.assertNotNull(emailResponse);
		Assert.assertEquals("serra@gmail.com", emailResponse.getRemetente());
	}
	
	@Test
	@UsingDataSet("db/email.xml")
	public void deveAlterarEmailComSucesso() throws ParseException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/email/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Email emailResponse = response.readEntity(Email.class);
		Assert.assertEquals("email@gmail.com", emailResponse.getRemetente());

		Email email = new Email();
		email.setAssunto("Email para cliente");
		email.setDescricao("Este email é para envio ao cliente.");
		email.setDestinatario("flavia@email.com");
		email.setDhEnvio(Calendar.getInstance().getTime());
		email.setRemetente("serra@gmail.com");
		email.setSituacao(SituacaoEmail.ENVIADO);

		target = client.target(URL_BASE + "/email/update/100001");
		response = target.request().put(Entity.entity(email, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Email emailResult = response.readEntity(Email.class);
		Assert.assertNotNull(emailResult);
		Assert.assertEquals("flavia@email.com", emailResult.getDestinatario());
	}
	
	@Test
	@UsingDataSet("db/email.xml")
	public void deveRemoverEmailComSucesso(){
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/email/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/email/findById/100001");
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));
		Email email = response.readEntity(Email.class);
		Assert.assertNull(email);
	}
	
	@Test
	@UsingDataSet("db/email.xml")
	public void deveRetornarEmail() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/email/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Email email = response.readEntity(Email.class);
		Assert.assertNotNull(email);
	}
	
	@Test
	@UsingDataSet("db/email.xml")
	public void deveRetornaDoisEmails() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/email/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Email> emails = (List<Email>) response.readEntity(List.class);
		Assert.assertEquals(2, emails.size());
	}

}
