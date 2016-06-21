package br.com.rp.repository;

import java.math.BigDecimal;

import javax.ejb.EJB;

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
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(true);
		conta.setNrConta("125458");
		contaRepository.save(conta);

		Cliente cliente = new Cliente();
		cliente.setCpf("157898775856");
		cliente.setNome("Rafael");
		cliente.setVlRenda(new BigDecimal(8550.00));
		cliente.setConta(conta);
		clienteRepository.save(cliente);

		Assert.assertNotNull(cliente.getId());
	}

}
