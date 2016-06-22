package br.com.rp.repository;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.UsuarioCliente;

public class UsuarioClienteRepositoryTest extends AbstractTest {

	@EJB
	private UsuarioClienteRepository usuarioClienteRepository;

	@EJB
	private ClienteRepository clienteRepository;

	@Test
	public void deveInserirComSucesso() {
		Cliente cliente = new Cliente();
		cliente.setNome("Christian Marchiori");
		cliente.setCpf("08431277911");
		clienteRepository.save(cliente);

		UsuarioCliente usuarioCliente = new UsuarioCliente();
		usuarioCliente.setNome("Christian Marchiori");
		usuarioCliente.setSenha("123456");
		usuarioClienteRepository.save(usuarioCliente);
		Assert.assertNotNull(usuarioCliente.getId());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRemoverUsuarioClienteComSucesso(){
		usuarioClienteRepository.remove(100001L);
		Assert.assertNull(usuarioClienteRepository.findById(100001L));
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveAlterarUsuarioClienteComSucesso(){
		UsuarioCliente usuarioCliente = usuarioClienteRepository.findById(100001L);
		usuarioCliente.setNome("Christian");
		usuarioClienteRepository.save(usuarioCliente);
		Assert.assertEquals("Christian", usuarioClienteRepository.findById(100001L).getNome());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNomeUsuarioCliente() {
		Assert.assertEquals("Rafael Suzin", usuarioClienteRepository.findById(100001L).getNome());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararSenhaUsuarioCliente(){
		Assert.assertNotEquals("123456", usuarioClienteRepository.findById(100001L).getSenha());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRetornarClienteDoUsuario(){
		Assert.assertNotNull(usuarioClienteRepository.findById(100001L).getCliente());
	}
	
	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNomeClienteDoUsuario(){
		Assert.assertEquals("Rafael Suzin da Silva", usuarioClienteRepository.findById(100002L).getCliente().getNome());
	}
	
}