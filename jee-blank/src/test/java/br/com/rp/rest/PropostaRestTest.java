package br.com.rp.rest;

import java.math.BigDecimal;
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
import br.com.rp.domain.Cargo;
import br.com.rp.domain.Email;
import br.com.rp.domain.Funcionario;
import br.com.rp.domain.Proposta;
import br.com.rp.domain.SituacaoProposta;
import br.com.rp.domain.UsuarioFuncionario;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class PropostaRestTest extends AbstractTest {

	private static final String URL_BASE = "http://localhost:8080/vbank/api";

	@Test
	public void deveSalvarPropostaComSucesso() throws ParseException {
		Proposta proposta = new Proposta();
		proposta.setCpf("74912901164");
		proposta.setDataProposta(new SimpleDateFormat("yyyy-MM-dd").parse("2016-06-20"));
		proposta.setNome("Rafael");
		proposta.setRegiao("Nordeste");
		proposta.setRenda(new BigDecimal(8000.00));
		proposta.setSituacaoProposta(SituacaoProposta.ABERTA);
		proposta.setEmail("rafael@gmail.com");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/save");
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(proposta, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta prop = response.readEntity(Proposta.class);
		Assert.assertNotNull(prop);
		Assert.assertEquals("74912901164", prop.getCpf());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveAlterarPropostaComSucesso() throws ParseException {
		Client client2 = ClientBuilder.newClient();
		WebTarget target2 = client2.target(URL_BASE + "/proposta/findById/100001");
		Response response2 = target2.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response2.getStatus()));
		Proposta proposta2 = response2.readEntity(Proposta.class);
		Assert.assertEquals("Unicesumar Time 3", proposta2.getNome());

		Proposta proposta = new Proposta();
		proposta.setCpf("25283798143");
		proposta.setDataProposta(new SimpleDateFormat("yyyy-MM-dd").parse("2016-06-20"));
		proposta.setNome("Rafael");
		proposta.setRegiao("Nordeste");
		proposta.setRenda(new BigDecimal(8000.00));
		proposta.setSituacaoProposta(SituacaoProposta.ABERTA);
		proposta.setEmail("rafael@gmail.com");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/update/100001");
		Response response = target.request().put(Entity.entity(proposta, MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta prop = response.readEntity(Proposta.class);
		Assert.assertNotNull(prop);
		Assert.assertEquals("Rafael", prop.getNome());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRemoverPropostaComSucesso() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/remove/100001");
		Response response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		Client client2 = ClientBuilder.newClient();
		WebTarget target2 = client2.target(URL_BASE + "/proposta/findById/100001");
		Response response2 = target2.request().get();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response2.getStatus()));
		Proposta proposta = response2.readEntity(Proposta.class);
		Assert.assertNull(proposta);
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRetornarProposta() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = response.readEntity(Proposta.class);
		Assert.assertNotNull(proposta);
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveCompararPropostaCpf() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = response.readEntity(Proposta.class);
		Assert.assertEquals("25283798143", proposta.getCpf());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRejeitarPropostaComSucesso() throws ParseException {
		// Os códigos comentados são uma opção de fazer o teste se usar xml,
		// criando os objetos manualmente.
		// Cargo cargoParan = new Cargo();
		// cargoParan.setDescricaoCargo("Gerente de TI");
		//
		// Client client = ClientBuilder.newClient();
		// WebTarget target = client.target(URL_BASE + "/cargo/save");
		// Response response =
		// target.request().accept(MediaType.APPLICATION_JSON)
		// .post(Entity.entity(cargoParan, MediaType.APPLICATION_JSON));
		// Assert.assertEquals(Integer.valueOf(200),
		// Integer.valueOf(response.getStatus()));
		// Cargo cargo = response.readEntity(Cargo.class);
		// Assert.assertNotNull(cargo);
		//
		// Funcionario funcionarioParan = new Funcionario();
		// funcionarioParan.setCpf("21717048455");
		// funcionarioParan.setEmail("julio@gmail.com");
		// funcionarioParan.setNome("Julio Serra");
		// funcionarioParan.setCargo(cargo);
		//
		// target = client.target(URL_BASE + "/funcionario/save");
		// response = target.request().accept(MediaType.APPLICATION_JSON)
		// .post(Entity.entity(funcionarioParan, MediaType.APPLICATION_JSON));
		// Assert.assertEquals(Integer.valueOf(200),
		// Integer.valueOf(response.getStatus()));
		// Funcionario funcionario = response.readEntity(Funcionario.class);
		// Assert.assertNotNull(funcionario);
		//
		// client = ClientBuilder.newClient();
		// target = client.target(URL_BASE + "/usuario/funcionario/save");
		// response = target.request().accept(MediaType.APPLICATION_JSON)
		// .post(Entity.entity(new UsuarioFuncionario("Flavia Ferreira",
		// "flavia", "123456", funcionario),
		// MediaType.APPLICATION_JSON));
		// Assert.assertEquals(Integer.valueOf(200),
		// Integer.valueOf(response.getStatus()));
		// UsuarioFuncionario usuarioFuncionario =
		// response.readEntity(UsuarioFuncionario.class);
		// Assert.assertNotNull(usuarioFuncionario);
		// Assert.assertEquals("Flavia Ferreira", usuarioFuncionario.getNome());
		//
		// Proposta prop = new Proposta();
		// prop.setCpf("74912901164");
		// prop.setDataProposta(new
		// SimpleDateFormat("yyyy-MM-dd").parse("2016-03-20"));
		// prop.setNome("Rafael");
		// prop.setRegiao("Nordeste");
		// prop.setRenda(new BigDecimal(8000.00));
		// prop.setSituacaoProposta(SituacaoProposta.ABERTA);
		// prop.setEmail("rafael@gmail.com");
		//
		// client = ClientBuilder.newClient();
		// target = client.target(URL_BASE + "/proposta/save");
		// response = target.request().accept(MediaType.APPLICATION_JSON)
		// .post(Entity.entity(prop, MediaType.APPLICATION_JSON));
		// Assert.assertEquals(Integer.valueOf(200),
		// Integer.valueOf(response.getStatus()));
		// Proposta proposta = response.readEntity(Proposta.class);
		// Assert.assertNotNull(proposta);
		// Assert.assertEquals(SituacaoProposta.ABERTA,
		// prop.getSituacaoProposta());
		//
		// System.out.println("TESTANDO :" + proposta.getId());
		// System.out.println("TESTANDO :" + usuarioFuncionario.getId());

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(
				URL_BASE + "/proposta/rejeitarProposta/100001/Renda insuficiente para abertura de cadastro/100001");
		Response response = target.request().put(Entity.entity(new Proposta(), MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta propostaResult = response.readEntity(Proposta.class);
		Assert.assertNotNull(propostaResult);
		Assert.assertNotNull(propostaResult.getUsuarioAnalise());
		Assert.assertNotNull(propostaResult.getMotivoRejeicao());
		Assert.assertEquals(SituacaoProposta.REJEITADA, propostaResult.getSituacaoProposta());
		Assert.assertEquals("Renda insuficiente para abertura de cadastro",
				propostaResult.getMotivoRejeicao().getDsMotivo());
		Assert.assertEquals(new Long(100001), propostaResult.getUsuarioAnalise().getId());

		target = client.target(URL_BASE + "/email/findByProposta/" + propostaResult.getId());
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Email email = response.readEntity(Email.class);
		Assert.assertNotNull(email);

		target = client.target(URL_BASE + "/email/remove/" + email.getId());
		response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

		target = client.target(URL_BASE + "/proposta/remove/" + propostaResult.getId());
		response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveAprovarPropostaComSucesso() {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/findById/100001");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta proposta = response.readEntity(Proposta.class);
		Assert.assertNotNull(proposta);
		Assert.assertEquals(SituacaoProposta.ABERTA, proposta.getSituacaoProposta());

		client = ClientBuilder.newClient();
		target = client.target(URL_BASE + "/proposta/aprovarProposta/100001/100001");
		response = target.request().put(Entity.entity(new Proposta(), MediaType.APPLICATION_JSON));
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Proposta prop = response.readEntity(Proposta.class);
		Assert.assertNotNull(prop);
		Assert.assertNotNull(prop.getUsuarioAnalise());
		Assert.assertEquals(SituacaoProposta.APROVADA, prop.getSituacaoProposta());
		Assert.assertEquals(new Long(100001), prop.getUsuarioAnalise().getId());

		target = client.target(URL_BASE + "/email/findByProposta/" + prop.getId());
		response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		Email email = response.readEntity(Email.class);
		Assert.assertNotNull(email);

		target = client.target(URL_BASE + "/email/remove/" + email.getId());
		response = target.request().delete();
		Assert.assertEquals(Integer.valueOf(204), Integer.valueOf(response.getStatus()));

	}

	@Test
	@UsingDataSet("db/proposta_listagem.xml")
	public void deveRetornaQuatroPropostas() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/getAll");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Proposta> propostas = (List<Proposta>) response.readEntity(List.class);
		Assert.assertEquals(4, propostas.size());
	}
	
	@Test
	@UsingDataSet("db/proposta_listagem.xml")
	public void deveRetornarDuasPropostasPorRegiao(){
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL_BASE + "/proposta/findByRegiao/Sul");
		Response response = target.request().get();
		Assert.assertEquals(Integer.valueOf(200), Integer.valueOf(response.getStatus()));
		List<Proposta> propostas = (List<Proposta>) response.readEntity(List.class);
		Assert.assertEquals(3, propostas.size());
	}
}