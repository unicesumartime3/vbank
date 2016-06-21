package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Conta;
import br.com.rp.repository.ContaRepository;

public class ContaRepositoryTest extends AbstractTest {
	
	@EJB
	private ContaRepository contaRepository;
	
	@Test
	public void deveInserirContaComSucesso() {
		Conta conta = new Conta();
		conta.setNrConta("1234");
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(false);
		contaRepository.save(conta);
		Assert.assertNotNull(conta.getId());
	}
	
	@Test
	@UsingDataSet("db/conta.xml")
	public void deveCompararNumeroConta() {
		Conta conta = contaRepository.findById(100001L);
		Assert.assertEquals("12345", conta.getNrConta());
	}

	@Test
	@UsingDataSet("db/conta.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, contaRepository.getAll().size());
	}
	
	@Test
	@UsingDataSet("db/conta.xml")
	public void deveRemoverContaComSucesso() {
		contaRepository.remove(100001L);
		Assert.assertEquals(1, contaRepository.getAll().size());
	}
	
	@Test
	@UsingDataSet("db/conta.xml")
	public void deveAtualizarNumeroContaComSucesso() {
		Conta conta = contaRepository.findById(100001L);
		conta.setNrConta("1234-5");
		contaRepository.save(conta);
		Assert.assertEquals("1234-5", contaRepository.findById(100001L).getNrConta());
	}
	
}
