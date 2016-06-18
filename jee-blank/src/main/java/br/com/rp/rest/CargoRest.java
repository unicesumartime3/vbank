package br.com.rp.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.com.rp.domain.Cargo;
import br.com.rp.services.CargoService;

@Path(value = "/cargo")
public class CargoRest {

	@EJB
	private CargoService cargoService;
	
	@GET
	public List<Cargo> getAll(){
		return cargoService.getAll();
	}
}
