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
		Assert.assertNotNull(clienteService.save(new Cliente("Rafael", "157898775856","rafael@gmail.com", new BigDecimal(8550.00),
				contaService.save(new Conta("123458", true, false)))).getId());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveAlterarClienteComSucesso() {
		Cliente cliente = clienteService.findById(100002L);
		cliente.setNome("Daniele Magron");
		clienteService.save(cliente);
		Assert.assertEquals("Daniele Magron", clienteService.findById(100002L).getNome());
	}

	/*
	 * A Exception esperada é InvalidStateException, porém o arquillian lança
	 * outra exceção.
	 */
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveValidarCpfIncorreto() {
		Assert.assertFalse(clienteService.isCpfValido(clienteService.findById(100001L).getCpf()));
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveValidarCpfCorreto() {
		Assert.assertTrue(clienteService.isCpfValido(clienteService.findById(100002L).getCpf()));
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

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRetornarClientePorCpf() {
		Assert.assertNotNull(clienteService.findByCpf("20192073206"));
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveVerificarCpfExistente() {
		Assert.assertTrue(clienteService.isCpfExistente("20192073206"));
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveVerificarCpfNaoExistente() {
		Assert.assertFalse(clienteService.isCpfExistente("2019207356"));
	}
	
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveCompararEmailCliente(){
		Assert.assertEquals("julio@gmail.com", clienteService.findById(100001L).getEmail());
	}
}
