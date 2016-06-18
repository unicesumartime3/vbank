package br.com.rp.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.com.rp.domain.UsuarioCliente;
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

@Path(value = "/usuariocliente")
public class UsuarioClienteRest {
	
	@EJB
	private UsuarioClienteService usuarioClienteService;
	
	@GET
	public List<UsuarioCliente> getAll() {
		return usuarioClienteService.getAll();
	}

}
