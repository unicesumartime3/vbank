package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Banco;
import br.com.rp.services.BancoService;

public class BancoServiceTest extends AbstractTest {
	
	@EJB
	private BancoService bancoService;

	@Test
	public void deveInserirBancoComSucesso() {
		Banco banco = new Banco();
		banco.setDescricao("Banco Bradesco");
		bancoService.save(banco);
		Assert.assertNotNull(banco.getId());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveCompararDescricaoBanco() {
		Banco banco = bancoService.findById(100001L);
		Assert.assertEquals("Banco do Brasil", banco.getDescricao());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, bancoService.getAll().size());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveRemoverBancoComSucesso() {
		bancoService.remove(100001L);
		Assert.assertEquals(1, bancoService.getAll().size());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveAtualizarDescricaoComSucesso() {
		Banco banco = bancoService.findById(100001L);
		banco.setDescricao("Banco Itau");
		bancoService.save(banco);
		Assert.assertEquals("Banco Itau", bancoService.findById(100001L).getDescricao());
	}

}
