package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.UsuarioCliente;
import br.com.rp.services.ClienteService;
import br.com.rp.services.UsuarioClienteService;

public class UsuarioClienteServiceTest extends AbstractTest {
	@EJB
	private UsuarioClienteService usuarioClienteService;

	@EJB
	private ClienteService clienteService;

	@Test
	public void deveInserirCargoComSucesso() {
		Cliente cliente = new Cliente();
		cliente.setNome("Rafael Suzin");
		cliente.setCpf("08564856652");
		clienteService.save(cliente);

		UsuarioCliente usuarioCliente = new UsuarioCliente();
		usuarioCliente.setNome("Rafael Suzin");
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
		UsuarioCliente usuarioCliente = usuarioClienteService.findById(100001L);
		usuarioCliente.setNome("Christian Marchiori da Silva");
		usuarioClienteService.save(usuarioCliente);
		Assert.assertEquals("Christian Marhciori da Silva", usuarioClienteService.findById(100001L).getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNomeUsuarioCliente() {
		Assert.assertEquals("Christian Marchiori", usuarioClienteService.findById(100001L).getNome());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararSenhaUsuarioCliente() {
		Assert.assertNotEquals("123456", usuarioClienteService.findById(100001L).getSenha());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveRetornarClienteDoUsuario() {
		Assert.assertNotNull(usuarioClienteService.findById(100001L).getCliente());
	}

	@Test
	@UsingDataSet("db/usuario_cliente.xml")
	public void deveCompararNomeClienteDoUsuario() {
		Assert.assertEquals("Christian Marchiori da Silva", usuarioClienteService.findById(100002L).getCliente().getNome());
	}
}
