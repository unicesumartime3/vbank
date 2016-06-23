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
import br.com.rp.domain.MotivoRejeicao;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class MotivoRejeicaoRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	public void deveSalvarMotivoComSucesso() {
		MotivoRejeicao motivo = new MotivoRejeicao();
		motivo.setDsMotivo("Renda insuficiente");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/motivorejeicao/save");
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(motivo, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		MotivoRejeicao mot = response.readEntity(MotivoRejeicao.class);
		Assert.assertNotNull(mot);
		Assert.assertEquals("Renda insuficiente", mot.getDsMotivo());
	}
	
	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveAlterarMotivoComSucesso() {
		Client client2 = ClientBuilder.newClient();
		WebTarget target2 = client2.target(URL_BASE + "/motivorejeicao/findById/100001");
		Response response2 = target2.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response2.getStatus()));
		MotivoRejeicao motivo2 = response2.readEntity(MotivoRejeicao.class);
		Assert.assertEquals("Renda insuficiente", motivo2.getDsMotivo());

		MotivoRejeicao motivo = new MotivoRejeicao();
		motivo.setDsMotivo("Número da conta inválido");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/motivorejeicao/update/100001");
		Response response = target.request().put(Entity.entity(motivo, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		MotivoRejeicao mot = response.readEntity(MotivoRejeicao.class);
		Assert.assertNotNull(mot);
		Assert.assertEquals("Número da conta inválido", mot.getDsMotivo());
	}
	
	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveRemoverMotivoComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/motivorejeicao/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		Client client2 = ClientBuilder.newClient();
		WebTarget target2 = client2.target(URL_BASE + "/motivorejeicao/findById/100001");
		Response response2 = target2.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response2.getStatus()));
		MotivoRejeicao motivo = response2.readEntity(MotivoRejeicao.class);
		Assert.assertNull(motivo);
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveRetornarMotivo() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/motivorejeicao/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		MotivoRejeicao motivo = response.readEntity(MotivoRejeicao.class);
		Assert.assertNotNull(motivo);
	}
	
	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveCompararDescricaoMotivo() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/motivorejeicao/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		MotivoRejeicao motivo = response.readEntity(MotivoRejeicao.class);
		Assert.assertEquals("Renda insuficiente", motivo.getDsMotivo());
	}
	
	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveRetornaDoisMotivos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/motivorejeicao/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<MotivoRejeicao> motivos = (List<MotivoRejeicao>) response.readEntity(List.class);
		Assert.assertEquals(2, motivos.size());
	}

}
