package br.com.rp.repository;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cargo;

public class CargoRepositoryTest extends AbstractTest {

	@EJB
	private CargoRepository cargoRepository;

	@Test
	public void deveInserirCargoComSucesso() {
		Cargo cargo = new Cargo();
		cargo.setDescricaoCargo("Gerente de Vendas");
		cargoRepository.save(cargo);
		Assert.assertNotNull(cargo.getId());
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveCompararDescricaoCargo() {
		Cargo cargos = cargoRepository.findById(100001L);
		Assert.assertEquals("Gerente de Vendas", cargos.getDescricaoCargo());
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, cargoRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveRemoverCargoComSucesso() {
		cargoRepository.remove(100001L);
		Assert.assertEquals(1, cargoRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveAtualizarDescricaoComSucesso() {
		Cargo cargo = cargoRepository.findById(100001L);
		cargo.setDescricaoCargo("Gerente de Contas");
		cargoRepository.save(cargo);
		Assert.assertEquals("Gerente de Contas", cargoRepository.findById(100001L).getDescricaoCargo());
	}
}
