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

import br.com.rp.domain.Acao;
import br.com.rp.services.AcaoService;

@Path("/acao")
@Produces("application/json")
public class AcaoRest {

	@EJB
	private AcaoService acaoService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Acao save(Acao acao) {
		return acaoService.save(acao);
	}

	@PUT
	@Path("/update/{id}")
	public Acao update(@PathParam("id") Long id, Acao acao) {
		Acao acaoResult = acaoService.findById(id);
		acaoResult.setDescricaoAcao(acao.getDescricaoAcao());
		return acaoService.save(acaoResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		acaoService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Acao findById(@PathParam("id") Long id) {
		return acaoService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Acao> getAll() {
		return acaoService.getAll();
	}
}
