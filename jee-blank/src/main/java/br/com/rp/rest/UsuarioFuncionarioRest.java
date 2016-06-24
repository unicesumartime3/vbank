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

import br.com.rp.domain.UsuarioFuncionario;
import br.com.rp.services.UsuarioFuncionarioService;

@Path("/usuario/funcionario")
@Produces("application/json")
public class UsuarioFuncionarioRest {

	@EJB
	private UsuarioFuncionarioService usuarioFuncionarioService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public UsuarioFuncionario save(UsuarioFuncionario usuarioFuncionario) {
		return usuarioFuncionarioService.save(usuarioFuncionario);
	}

	@PUT
	@Path("/update/{id}")
	public UsuarioFuncionario update(@PathParam("id") Long id, UsuarioFuncionario usuarioFuncionario) {
		UsuarioFuncionario usuarioFuncionarioResult = usuarioFuncionarioService.findById(id);
		usuarioFuncionarioResult.setNome(usuarioFuncionario.getNome());
		usuarioFuncionarioResult.setLogin(usuarioFuncionario.getLogin());
		usuarioFuncionarioResult.setSenha(usuarioFuncionario.getSenha());
		usuarioFuncionarioResult.setFuncionario(usuarioFuncionario.getFuncionario());

		return usuarioFuncionarioService.save(usuarioFuncionarioResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		usuarioFuncionarioService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public UsuarioFuncionario findById(@PathParam("id") Long id) {
		return usuarioFuncionarioService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<UsuarioFuncionario> getAll() {
		return usuarioFuncionarioService.getAll();
	}
}
