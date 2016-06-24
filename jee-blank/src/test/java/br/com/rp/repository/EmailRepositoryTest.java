package br.com.rp.repository;

import java.util.Calendar;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Email;
import br.com.rp.domain.Proposta;
import br.com.rp.domain.SituacaoEmail;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class EmailRepositoryTest extends AbstractTest {
	
	@EJB
	private EmailRepository emailRepository;
	
	@EJB
	private PropostaRepository propostaRepository;

	@Test
	@UsingDataSet("db/email.xml")
	public void deveInserirEmailComSucesso() {
		Proposta proposta = propostaRepository.findById(100001L);
		Assert.assertEquals("João", proposta.getNome());
		
		Email email = new Email();
		email.setAssunto("Email para cliente");
		email.setDescricao("Este email é para envio ao cliente.");
		email.setDestinatario("email@email.com");
		email.setDhEnvio(Calendar.getInstance().getTime());
		email.setRemetente("email@gmail.com");
		email.setSituacao(SituacaoEmail.AGUARDANDO_ENVIO);
		email.setProposta(proposta);
		emailRepository.save(email);
		Assert.assertNotNull(email.getId());
	}

	@Test
	@UsingDataSet("db/email.xml")
	public void deveCompararDescricaoEmail() {
		Assert.assertEquals("Teste descricao email.", emailRepository.findById(100001L).getDescricao());
	}

	@Test
	@UsingDataSet("db/email.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, emailRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/email.xml")
	public void deveRemoverAcaoComSucesso() {
		emailRepository.remove(100001L);
		Assert.assertEquals(1, emailRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/email.xml")
	public void deveAtualizarDescricaoComSucesso() {
		Email email = emailRepository.findById(100001L);
		email.setDescricao("Teste de descrição de email");
		emailRepository.save(email);
		Assert.assertEquals("Teste de descrição de email", emailRepository.findById(100001L).getDescricao());
	}
}
