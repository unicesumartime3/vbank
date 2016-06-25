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

import br.com.rp.domain.Banco;
import br.com.rp.services.BancoService;

@Path("/banco")
@Produces("application/json")
public class BancoRest {

	@EJB
	private BancoService bancoService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Banco save(Banco banco) {
		return bancoService.save(banco);
	}

	@PUT
	@Path("/update/{id}")
	public Banco update(@PathParam("id") Long id, Banco banco) {
		Banco bancoResult = bancoService.findById(id);
		bancoResult.setDescricao(banco.getDescricao());
		return bancoService.save(bancoResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		bancoService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Banco findById(@PathParam("id") Long id) {
		return bancoService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Banco> getAll() {
		return bancoService.getAll();
	}

}
