package br.com.rp.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rp.domain.Cargo;
import br.com.rp.services.CargoService;

@Path("/cargo")
@Produces("application/json")
public class CargoRest {

	@EJB
	private CargoService cargoService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Cargo save(Cargo cargo) {
		return cargoService.save(cargo);
	}

	@PUT
	@Path("/update/{id}")
	public Cargo update(@PathParam("id") Long id, Cargo cargo) {
		/*
		 * O front-end vai bloquear do CPF da proposta para não ser alterado.
		 */
		Cargo cargoResult = cargoService.findById(id);
		cargoResult.setDescricaoCargo(cargo.getDescricaoCargo());
		return cargoService.save(cargoResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		cargoService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Cargo findById(@PathParam("id") Long id) {
		return cargoService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Cargo> getAll() {
		return cargoService.getAll();
	}
}
