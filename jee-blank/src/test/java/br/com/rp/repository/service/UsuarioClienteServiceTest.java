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
import br.com.rp.domain.UsuarioCliente;
import br.com.rp.services.ClienteService;
import br.com.rp.services.ContaService;
import br.com.rp.services.UsuarioClienteService;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class UsuarioClienteServiceTest extends AbstractTest {
	@EJB
	private UsuarioClienteService usuarioClienteService;

	@EJB
	private ClienteService clienteService;

	@EJB
	private ContaService contaService;

	@Test
	public void deveInserirCargoComSucesso() {
		Conta conta = new Conta();
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(false);
		conta.setNrConta("56589-5");
		contaService.save(conta);

		Cliente cliente = new Cliente();
		cliente.setNome("Rafael Suzin");
		cliente.setCpf("08564856652");
		cliente.setVlRenda(new BigDecimal(8006.00));
		cliente.setConta(conta);
		cliente.setEmail("flavia@gmail.com");
		clienteService.save(cliente);

		UsuarioCliente usuarioCliente = new UsuarioCliente();
		usuarioCliente.setNome("Rafael Suzin");
		usuarioCliente.setLogin("rafael");
		usuarioCliente.setSenha("123456");
		usuarioCliente.setCliente(cliente);
		usuarioClienteService.save(usuarioCliente);
		Assert.assertNotNull(usuarioCliente.getId());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRemoveUsuarioClienteComSucesso() {
		usuarioClienteService.remove(100001L);
		Assert.assertNull(usuarioClienteService.findById(100001L));
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveAlterarUsuarioClienteComSucesso() {
		UsuarioCliente usuarioCliente = usuarioClienteService.findById(100002L);
		usuarioCliente.setNome("Christian Marchiori da Silva");
		usuarioClienteService.save(usuarioCliente);
		Assert.assertEquals("Christian Marchiori da Silva", usuarioClienteService.findById(100002L).getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNomeUsuarioCliente() {
		Assert.assertEquals("Christian Marchiori", usuarioClienteService.findById(100001L).getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararSenhaIncorretaUsuarioCliente() {
		Assert.assertNotEquals("123458", usuarioClienteService.findById(100001L).getSenha());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararSenhaCorretaUsuarioCliente() {
		Assert.assertEquals("123456", usuarioClienteService.findById(100001L).getSenha());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRetornarClienteDoUsuario() {
		Assert.assertNotNull(usuarioClienteService.findById(100001L).getCliente());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNomeClienteDoUsuario() {
		Assert.assertEquals("Christian Marchiori da Silva",
				usuarioClienteService.findById(100001L).getCliente().getNome());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRetornarContaClienteUsuario(){
		Assert.assertNotNull(usuarioClienteService.findById(100001L).getCliente().getConta());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNrContaClienteUsuario(){
		Assert.assertEquals("12345", usuarioClienteService.findById(100001L).getCliente().getConta().getNrConta());
	}
}
