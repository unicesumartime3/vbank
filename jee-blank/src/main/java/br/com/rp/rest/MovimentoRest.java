package br.com.rp.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.rp.domain.Movimento;
import br.com.rp.services.MovimentoService;

@Path("/movimento")
@Produces("application/json")
public class MovimentoRest {
	
	@EJB
	private MovimentoService movimentoService;
	
	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		movimentoService.remove(id);
	}

	@GET
	@Path("/getAll")
	public List<Movimento> getAll() {
		return movimentoService.getAll();
	}
}
