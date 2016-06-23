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

import br.com.rp.domain.UsuarioCliente;
import br.com.rp.domain.UsuarioFuncionario;
import br.com.rp.services.UsuarioClienteService;

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

@Path("/usuario/cliente")
@Produces("application/json")
public class UsuarioClienteRest {
	
	@EJB
	private UsuarioClienteService usuarioClienteService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public UsuarioCliente save(UsuarioCliente usuarioCliente) {
			return usuarioClienteService.save(usuarioCliente);
	}
	
	@PUT
	@Path("/update/{id}")
	public UsuarioCliente update(@PathParam("id") Long id, UsuarioCliente usuarioCliente) {
		UsuarioCliente usuarioClienteResult = usuarioClienteService.findById(id);
		usuarioClienteResult.setNome(usuarioCliente.getNome());
		usuarioClienteResult.setSenha(usuarioCliente.getSenha());
		usuarioClienteResult.setCliente(usuarioCliente.getCliente());
		
		return usuarioClienteService.save(usuarioClienteResult);
	}
	
	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		usuarioClienteService.remove(id);
	}
	
	@GET
	@Path("/findById/{id}")
	public UsuarioCliente findById(@PathParam("id") Long id) {
		return usuarioClienteService.findById(id);
	}
	
	@GET
	@Path("/getAll")
	public List<UsuarioCliente> getAll() {
		return usuarioClienteService.getAll();
	}

}
