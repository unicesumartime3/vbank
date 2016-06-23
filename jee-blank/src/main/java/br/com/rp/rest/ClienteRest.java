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

import br.com.rp.domain.Cliente;
import br.com.rp.services.ClienteService;

@Path("/cliente")
@Produces("application/json")
public class ClienteRest {

	@EJB
	private ClienteService clienteService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Cliente save(Cliente cliente) {
		if (clienteService.isCpfExistente(cliente.getCpf())) {
			throw new RuntimeException("Já existe um cliente cadastrado com este CPF.");
		} else if (!clienteService.isCpfValido(cliente.getCpf())) {
			throw new RuntimeException("O CPF informado não é válido.");
		} else {
			return clienteService.save(cliente);
		}
	}

	@PUT
	@Path("/update/{id}")
	public Cliente update(@PathParam("id") Long id, Cliente cliente) {
		/*
		 * O front-end vai bloquear do CPF da proposta para não ser alterado.
		 */
		Cliente clienteResult = clienteService.findById(id);
		clienteResult.setCpf(cliente.getCpf());
		clienteResult.setNome(cliente.getNome());
		clienteResult.setVlRenda(cliente.getVlRenda());
		clienteResult.setConta(cliente.getConta());
		clienteResult.setEmail(cliente.getEmail());

		return clienteService.save(clienteResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		clienteService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Cliente findById(@PathParam("id") Long id) {
		return clienteService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Cliente> getAll() {
		return clienteService.getAll();
	}
}
