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

import br.com.rp.domain.MotivoRejeicao;
import br.com.rp.services.MotivoRejeicaoService;

@Path("/motivorejeicao")
@Produces("application/json")
public class MotivoRejeicaoRest {

	@EJB
	private MotivoRejeicaoService motivoService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public MotivoRejeicao save(MotivoRejeicao motivo) {
		return motivoService.save(motivo);
	}

	@PUT
	@Path("/update/{id}")
	public MotivoRejeicao update(@PathParam("id") Long id, MotivoRejeicao motivo) {
		MotivoRejeicao motivoResult = motivoService.findById(id);
		motivoResult.setDsMotivo(motivo.getDsMotivo());
		return motivoService.save(motivoResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		motivoService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public MotivoRejeicao findById(@PathParam("id") Long id) {
		return motivoService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<MotivoRejeicao> getAll() {
		return motivoService.getAll();
	}

}
