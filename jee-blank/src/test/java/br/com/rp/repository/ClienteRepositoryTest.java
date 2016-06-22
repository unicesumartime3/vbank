package br.com.rp.repository;

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

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class ClienteRepositoryTest extends AbstractTest {

	@EJB
	private ContaRepository contaRepository;

	@EJB
	private ClienteRepository clienteRepository;

	@Test
	public void deveInserirClienteComSucesso() {
		Conta conta = new Conta();
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(true);
		conta.setNrConta("125458");
		contaRepository.save(conta);

		Cliente cliente = new Cliente();
		cliente.setCpf("09006848956");
		cliente.setNome("Rafael");
		cliente.setVlRenda(new BigDecimal(8550.00));
		cliente.setConta(conta);
		clienteRepository.save(cliente);

		Assert.assertNotNull(cliente.getId());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveAlterarClienteComSucesso() {
		Cliente cliente = clienteRepository.findById(100002L);
		cliente.setNome("Daniele Magron");
		clienteRepository.save(cliente);
		Assert.assertEquals("Daniele Magron", clienteRepository.findById(100002L).getNome());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRemoverClienteComSucesso() {
		clienteRepository.remove(100002L);
		Assert.assertNull(clienteRepository.findById(100002L));
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveCompararNomeCliente() {
		Assert.assertEquals("Perola Araujo", clienteRepository.findById(100002L).getNome());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRetornarNumeroContaCliente() {
		Assert.assertNotNull(clienteRepository.findById(100002L).getConta().getNrConta());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveCompararNumeroContaCliente() {
		Assert.assertEquals("67890", clienteRepository.findById(100002L).getConta().getNrConta());
	}

	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, clienteRepository.getAll().size());
	}

}
