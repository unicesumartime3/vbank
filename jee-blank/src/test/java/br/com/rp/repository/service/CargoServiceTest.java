package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cargo;
import br.com.rp.services.CargoService;

public class CargoServiceTest extends AbstractTest {

	@EJB
	private CargoService cargoService;
	
	@Test
	public void deveInserirCargoComSucesso(){
		Cargo cargo = new Cargo();
		cargo.setDescricaoCargo("Gerente de Vendas");
		cargoService.save(cargo);
		Assert.assertNotNull(cargo.getId());
	}
	
	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveCompararDescricaoCargo(){
		Cargo cargos = cargoService.findById(100001L);
		Assert.assertEquals("Gerente de Vendas", cargos.getDescricaoCargo());
	}
	
	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveRetornarDoisRegistros(){
		Assert.assertEquals(2, cargoService.getAll().size());
	}
	
	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveRemoverCargoComSucesso(){
		cargoService.remove(100001L);
		Assert.assertEquals(1, cargoService.getAll().size());
	}
	
	@Test
	@UsingDataSet("db/cargo.xml")
	public void deveAtualizarDescricaoComSucesso(){
		Cargo cargo = cargoService.findById(100001L);
		cargo.setDescricaoCargo("Gerente de Contas");
		cargoService.save(cargo);
		Assert.assertEquals("Gerente de Contas", cargoService.findById(100001L).getDescricaoCargo());
	}
}
