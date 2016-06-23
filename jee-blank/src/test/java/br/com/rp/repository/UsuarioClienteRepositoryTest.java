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
import br.com.rp.domain.UsuarioCliente;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class UsuarioClienteRepositoryTest extends AbstractTest {

	@EJB
	private UsuarioClienteRepository usuarioClienteRepository;

	@EJB
	private ClienteRepository clienteRepository;
	
	@EJB
	private ContaRepository contaRepository;

	@Test
	public void deveInserirCargoComSucesso() {
		Conta conta = new Conta();
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(false);
		conta.setNrConta("56589-5");
		contaRepository.save(conta);

		Cliente cliente = new Cliente();
		cliente.setNome("Rafael Suzin");
		cliente.setCpf("08564856652");
		cliente.setVlRenda(new BigDecimal(8006.00));
		cliente.setConta(conta);
		cliente.setEmail("flavia@gmail.com");
		clienteRepository.save(cliente);

		UsuarioCliente usuarioCliente = new UsuarioCliente();
		usuarioCliente.setNome("Rafael Suzin");
		usuarioCliente.setSenha("123456");
		usuarioCliente.setCliente(cliente);
		usuarioClienteRepository.save(usuarioCliente);
		Assert.assertNotNull(usuarioCliente.getId());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRemoveUsuarioClienteComSucesso() {
		usuarioClienteRepository.remove(100001L);
		Assert.assertNull(usuarioClienteRepository.findById(100001L));
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveAlterarUsuarioClienteComSucesso() {
		UsuarioCliente usuarioCliente = usuarioClienteRepository.findById(100002L);
		usuarioCliente.setNome("Christian Marchiori da Silva");
		usuarioClienteRepository.save(usuarioCliente);
		Assert.assertEquals("Christian Marchiori da Silva", usuarioClienteRepository.findById(100002L).getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNomeUsuarioCliente() {
		Assert.assertEquals("Christian Marchiori", usuarioClienteRepository.findById(100001L).getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararSenhaIncorretaUsuarioCliente() {
		Assert.assertNotEquals("123458", usuarioClienteRepository.findById(100001L).getSenha());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararSenhaCorretaUsuarioCliente() {
		Assert.assertEquals("123456", usuarioClienteRepository.findById(100001L).getSenha());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRetornarClienteDoUsuario() {
		Assert.assertNotNull(usuarioClienteRepository.findById(100001L).getCliente());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNomeClienteDoUsuario() {
		Assert.assertEquals("Christian Marchiori da Silva",
				usuarioClienteRepository.findById(100001L).getCliente().getNome());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRetornarContaClienteUsuario(){
		Assert.assertNotNull(usuarioClienteRepository.findById(100001L).getCliente().getConta());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNrContaClienteUsuario(){
		Assert.assertEquals("12345", usuarioClienteRepository.findById(100001L).getCliente().getConta().getNrConta());
	}
	
}