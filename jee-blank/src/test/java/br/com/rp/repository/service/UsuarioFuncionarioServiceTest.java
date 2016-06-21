package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cargo;
import br.com.rp.domain.Funcionario;
import br.com.rp.domain.UsuarioFuncionario;
import br.com.rp.services.CargoService;
import br.com.rp.services.FuncionarioService;
import br.com.rp.services.UsuarioFuncionarioService;

public class UsuarioFuncionarioServiceTest extends AbstractTest {
	@EJB
	private UsuarioFuncionarioService usuarioFuncionarioService;

	@EJB
	private FuncionarioService funcionarioService;

	@EJB
	private CargoService cargoService;

	@Test
	public void deveInserirCargoComSucesso() {
		Cargo cargo = new Cargo();
		cargo.setDescricaoCargo("Gerente de vendas");
		cargoService.save(cargo);

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Rafael Suzin");
		funcionario.setCpf("08564856652");
		funcionario.setCargo(cargo);
		funcionarioService.save(funcionario);

		UsuarioFuncionario usuarioFuncionario = new UsuarioFuncionario();
		usuarioFuncionario.setLogin("RAFAEL");
		usuarioFuncionario.setNome("Rafael Suzin");
		usuarioFuncionario.setSenha("123456");
		usuarioFuncionario.setFuncionario(funcionario);
		usuarioFuncionarioService.save(usuarioFuncionario);
		Assert.assertNotNull(usuarioFuncionario.getId());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveRemoveUsuarioFuncionarioComSucesso() {
		usuarioFuncionarioService.remove(100001L);
		Assert.assertNull(usuarioFuncionarioService.findById(100001L));
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveAlterarUsuarioFuncionarioComSucesso() {
		UsuarioFuncionario usuarioFuncionario = usuarioFuncionarioService.findById(100001L);
		usuarioFuncionario.setLogin("RSUZIN");
		usuarioFuncionarioService.save(usuarioFuncionario);
		Assert.assertEquals("RSUZIN", usuarioFuncionarioService.findById(100001L).getLogin());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveCompararNomeUsuarioFuncionario() {
		Assert.assertEquals("Rafael Suzin", usuarioFuncionarioService.findById(100001L).getNome());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveCompararLoginUsuarioFuncionario() {
		Assert.assertNotEquals("RAFAEL", usuarioFuncionarioService.findById(100001L).getLogin());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveRetornarFuncionarioDoUsuario() {
		Assert.assertNotNull(usuarioFuncionarioService.findById(100001L).getFuncionario());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveCompararNomeFuncionarioDoUsuario() {
		Assert.assertEquals("Rafael Suzin", usuarioFuncionarioService.findById(100002L).getFuncionario().getNome());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveRetornarCargoDoFuncionarioUsuario() {
		Assert.assertNotNull(usuarioFuncionarioService.findById(100001L).getFuncionario().getCargo());
	}

	@Test
	@UsingDataSet("db/usuario_funcionario.xml")
	public void deveCompararNomeCargoUsuarioFuncionario() {
		Assert.assertEquals("Gerente de Vendas",
				usuarioFuncionarioService.findById(100001L).getFuncionario().getCargo().getDescricaoCargo());
	}
}
