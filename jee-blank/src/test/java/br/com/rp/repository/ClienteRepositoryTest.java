package br.com.rp.repository;

import java.math.BigDecimal;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.Conta;

public class ClienteRepositoryTest extends AbstractTest {
	
	@EJB
	private ContaRepository contaRepository;
	
	@EJB
	private ClienteRepository clienteRepository;
	
	@Test
	public void deveInserirClienteComSucesso() {
		
		Conta conta = new Conta();
		conta.setNrConta("1234");
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(false);
		contaRepository.save(conta);
		
		Cliente cliente = new Cliente();		
		cliente.setNome("Julio Serra");
		cliente.setCpf("862.946.864-58");
		cliente.setVlRenda(new BigDecimal(8500.00));
		cliente.setConta(conta);		
		clienteRepository.save(cliente);
		
		Assert.assertNotNull(cliente.getId());
	}
	
	@Test
	@UsingDataSet("db/cliente.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, clienteRepository.getAll().size());
	}

}
