package br.com.rp.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.rp.integration.Integracao;
import br.com.rp.services.IntegracaoService;

@Path("/integracao")
@Produces("application/json")
public class IntegracaoRest {
	
	@EJB
	private IntegracaoService integracaoService;
	
	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		integracaoService.remove(id);
	}

	@GET
	@Path("/getAll")
	public List<Integracao> getAll() {
		return integracaoService.getAll();
	}

}
