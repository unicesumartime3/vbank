package br.com.rp.rest.funcionario;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.com.rp.domain.funcionario.Cargo;
import br.com.rp.services.funcionario.CargoService;

@Path(value = "/cargo")
public class CargoRest {

	@EJB
	private CargoService cargoService;
	
	@GET
	@Path(value = "/")
	public List<Cargo> getAll(){
		return cargoService.getAll();
	}
}
