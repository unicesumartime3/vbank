package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Conta;
import br.com.rp.services.ContaService;

public class ContaServiceTest extends AbstractTest {
	
	@EJB
	private ContaService contaService;
	
	@Test
	public void deveInserirContaComSucesso() {
		Conta conta = new Conta();
		conta.setNrConta("1234");
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(false);
		contaService.save(conta);
		Assert.assertNotNull(conta.getId());
	}
	
	@Test
	@UsingDataSet("db/conta.xml")
	public void deveCompararNumeroConta() {
		Assert.assertEquals("12345", contaService.findById(100001L).getNrConta());
	}

	@Test
	@UsingDataSet("db/conta.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, contaService.getAll().size());
	}
	
	@Test
	@UsingDataSet("db/conta.xml")
	public void deveRemoverContaComSucesso() {
		contaService.remove(100001L);
		Assert.assertEquals(1, contaService.getAll().size());
	}
	
	@Test
	@UsingDataSet("db/conta.xml")
	public void deveAtualizarNumeroContaComSucesso() {
		Conta conta = contaService.findById(100001L);
		conta.setNrConta("1234-5");
		contaService.save(conta);
		Assert.assertEquals("1234-5", contaService.findById(100001L).getNrConta());
	}

}
