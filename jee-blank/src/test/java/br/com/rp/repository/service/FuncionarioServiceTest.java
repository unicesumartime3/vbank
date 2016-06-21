package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cargo;
import br.com.rp.domain.Funcionario;
import br.com.rp.services.CargoService;
import br.com.rp.services.FuncionarioService;

public class FuncionarioServiceTest extends AbstractTest {

	@EJB
	private CargoService cargoService;

	@EJB
	private FuncionarioService funcionarioService;

	@Test
	public void deveInserirFuncionarioComSucesso() {
		Cargo cargo = new Cargo();
		cargo.setDescricaoCargo("Gerente de Vendas");
		cargoService.save(cargo);

		Funcionario funcionario = new Funcionario();
		funcionario.setCpf("15489758956");
		funcionario.setNome("Julio Serra");
		funcionario.setCargo(cargo);
		funcionarioService.save(funcionario);
		Assert.assertNotNull(funcionario.getId());
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveAlterarFuncionarioComSucesso() {
		Funcionario funcionario = funcionarioService.findById(100001L);
		funcionario.setNome("Julio Serra");
		funcionarioService.save(funcionario);
		Assert.assertEquals("Julio Serra", funcionarioService.findById(100001L).getNome());
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveRemoverFuncionarioComSucesso() {
		funcionarioService.remove(100001L);
		Assert.assertNull(funcionarioService.findById(100001L));
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveCompararNomeFuncionario() {
		Assert.assertEquals("Rafael Suzin", funcionarioService.findById(100001L).getNome());
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveRetornarCargoFuncionario() {
		Assert.assertNotNull(funcionarioService.findById(100001L).getCargo());
	}

	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveCompararDescricaoCargoFuncionario() {
		Assert.assertEquals("Gerente de Vendas", funcionarioService.findById(100001L).getCargo().getDescricaoCargo());
	}
	
	@Test
	@UsingDataSet("db/funcionario.xml")
	public void deveRetornarDoisRegistros(){
		Assert.assertEquals(2, funcionarioService.getAll().size());
	}

}
