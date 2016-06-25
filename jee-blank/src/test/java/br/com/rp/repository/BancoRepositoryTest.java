package br.com.rp.repository;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Banco;

public class BancoRepositoryTest extends AbstractTest {

	@EJB
	private BancoRepository bancoRepository;

	@Test
	public void deveInserirBancoComSucesso() {
		Banco banco = new Banco();
		banco.setDescricao("Banco Bradesco");
		bancoRepository.save(banco);
		Assert.assertNotNull(banco.getId());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveCompararDescricaoBanco() {
		Banco banco = bancoRepository.findById(100001L);
		Assert.assertEquals("Banco do Brasil", banco.getDescricao());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, bancoRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveRemoverBancoComSucesso() {
		bancoRepository.remove(100001L);
		Assert.assertEquals(1, bancoRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/banco.xml")
	public void deveAtualizarDescricaoComSucesso() {
		Banco banco = bancoRepository.findById(100001L);
		banco.setDescricao("Banco Itau");
		bancoRepository.save(banco);
		Assert.assertEquals("Banco Itau", bancoRepository.findById(100001L).getDescricao());
	}
}
