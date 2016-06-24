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

import br.com.rp.domain.Agendamento;
import br.com.rp.services.AgendamentoService;

/**
 * 
 * @author Christian Marchiori
 * @email cmxk@live.com
 *
 * @author Flávia Ferreira
 * @email flaviahferreirah@gmail.com
 *
 * @author Júlio Serra
 * @email julioserraaraujo@gmail.com
 * 
 * @author Rafael Suzin
 * @email rafaelsuzin1@gmail.com
 *
 */

@Path("/agendamento")
@Produces("application/json")
public class AgendamentoRest {

	@EJB
	private AgendamentoService agendamentoService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Agendamento save(Agendamento agendamento) {
		return agendamentoService.save(agendamento);
	}

	@PUT
	@Path("/update/{id}")
	public Agendamento update(@PathParam("id") Long id, Agendamento agendamento) {
		Agendamento agendamentoResult = agendamentoService.findById(id);
		agendamentoResult.setDataAgendamento(agendamento.getDataAgendamento());
		agendamentoResult.setCliente(agendamento.getCliente());

		return agendamentoService.save(agendamentoResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		agendamentoService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Agendamento findById(@PathParam("id") Long id) {
		return agendamentoService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Agendamento> getAll() {
		return agendamentoService.getAll();
	}

}
