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

import br.com.rp.domain.Conta;
import br.com.rp.services.ContaService;

@Path("/conta")
@Produces("application/json")
public class ContaRest {

	@EJB
	private ContaService contaService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Conta save(Conta conta) {
		return contaService.save(conta);
	}

	@PUT
	@Path("/update/{id}")
	public Conta update(@PathParam("id") Long id, Conta conta) {
		/*
		 * O front-end vai bloquear do CPF da proposta para não ser alterado.
		 */
		Conta contaResult = contaService.findById(id);
		contaResult.setIsContaCorrente(conta.getIsContaCorrente());
		contaResult.setIsContaPoupanca(conta.getIsContaPoupanca());
		contaResult.setNrConta(conta.getNrConta());
		return contaService.save(contaResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		contaService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Conta findById(@PathParam("id") Long id) {
		return contaService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Conta> getAll() {
		return contaService.getAll();
	}

}
