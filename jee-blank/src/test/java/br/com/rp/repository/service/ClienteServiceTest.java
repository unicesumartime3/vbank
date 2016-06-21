package br.com.rp.repository.service;

import java.math.BigDecimal;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.Conta;
import br.com.rp.services.ClienteService;
import br.com.rp.services.ContaService;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class ClienteServiceTest extends AbstractTest {
	
	@EJB
	ContaService contaService;
	
	@EJB
	ClienteService clienteService;
	
	@Test
	public void deveInserirClienteComSucesso() {
		Conta conta = new Conta();
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(true);
		conta.setNrConta("125458");
		contaService.save(conta);

		Cliente cliente = new Cliente();
		cliente.setCpf("157898775856");
		cliente.setNome("Rafael");
		cliente.setVlRenda(new BigDecimal(8550.00));
		cliente.setConta(conta);
		clienteService.save(cliente);

		Assert.assertNotNull(cliente.getId());
	}
	
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveAlterarClienteComSucesso() {
		Cliente cliente = clienteService.findById(100002L);
		cliente.setNome("Daniele Magron");
		clienteService.save(cliente);
		Assert.assertEquals("Daniele Magron", clienteService.findById(100002L).getNome());
	}
	
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRemoverClienteComSucesso() {
		clienteService.remove(100002L);
		Assert.assertNull(clienteService.findById(100002L));
	}
	
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveCompararNomeCliente() {
		Assert.assertEquals("Perola Araujo", clienteService.findById(100002L).getNome());
	}
	
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRetornarNumeroContaCliente() {
		Assert.assertNotNull(clienteService.findById(100002L).getConta().getNrConta());
	}
	
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveCompararNumeroContaCliente() {
		Assert.assertEquals("67890", clienteService.findById(100002L).getConta().getNrConta());
	}
	
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, clienteService.getAll().size());
	}
}
