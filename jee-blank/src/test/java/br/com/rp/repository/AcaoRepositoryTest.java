package br.com.rp.repository;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Acao;

public class AcaoRepositoryTest extends AbstractTest {

	@EJB
	private AcaoRepository acaoRepository;

	@Test
	public void deveInserirAcaoComSucesso() {
		Acao acao = new Acao();
		acao.setDescricaoAcao("Consultar Saldo");
		acaoRepository.save(acao);
		Assert.assertNotNull(acao.getId());
	}

	@Test
	@UsingDataSet("db/acao.xml")
	public void deveCompararDescricaoAcao() {
		Acao acao = acaoRepository.findById(100001L);
		Assert.assertEquals("Consultar Saldo", acao.getDescricaoAcao());
	}

	@Test
	@UsingDataSet("db/acao.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, acaoRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/acao.xml")
	public void deveRemoverAcaoComSucesso() {
		acaoRepository.remove(100001L);
		Assert.assertEquals(1, acaoRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/acao.xml")
	public void deveAtualizarDescricaoComSucesso() {
		Acao acao = acaoRepository.findById(100001L);
		acao.setDescricaoAcao("Depósito");
		acaoRepository.save(acao);
		Assert.assertEquals("Depósito", acaoRepository.findById(100001L).getDescricaoAcao());
	}

}
