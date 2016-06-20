package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Acao;
import br.com.rp.services.AcaoService;

public class AcaoServiceTest extends AbstractTest{
	
	@EJB
	private AcaoService acaoService;

	@Test
	public void deveInserirAcaoComSucesso() {
		Acao acao = new Acao();
		acao.setDescricaoAcao("Consultar Saldo");
		acaoService.save(acao);
		Assert.assertNotNull(acao.getId());
	}

	@Test
	@UsingDataSet("db/acao.xml")
	public void deveCompararDescricaoAcao() {
		Acao acao = acaoService.findById(100001L);
		Assert.assertEquals("Consultar Saldo", acao.getDescricaoAcao());
	}

	@Test
	@UsingDataSet("db/acao.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, acaoService.getAll().size());
	}

	@Test
	@UsingDataSet("db/acao.xml")
	public void deveRemoverAcaoComSucesso() {
		acaoService.remove(100001L);
		Assert.assertEquals(1, acaoService.getAll().size());
	}

	@Test
	@UsingDataSet("db/acao.xml")
	public void deveAtualizarDescricaoComSucesso() {
		Acao acao = acaoService.findById(100001L);
		acao.setDescricaoAcao("Depósito");
		acaoService.save(acao);
		Assert.assertEquals("Depósito", acaoService.findById(100001L).getDescricaoAcao());
	}

}
