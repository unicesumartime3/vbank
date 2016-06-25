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
import br.com.rp.domain.Parametro;
import br.com.rp.services.BancoService;
import br.com.rp.services.ParametroService;

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

@Path("/parametro")
@Produces("application/json")
public class ParametroRest {
	@EJB
	private ParametroService parametroService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Parametro save(Parametro parametro) {
		return parametroService.save(parametro);
	}

	@PUT
	@Path("/update/{id}")
	public Parametro update(@PathParam("id") Long id, Parametro parametro) {
		Parametro parametroResult = parametroService.findById(id);
		parametroResult.setHoraFimTransacoes(parametro.getHoraFimTransacoes());
		parametroResult.setHoraInicioTransacoes(parametro.getHoraInicioTransacoes());
		parametroResult.setHorarioIntegracaoEUA(parametro.getHorarioIntegracaoEUA());
		parametroResult.setIntervaloIntegracaoBancoCentral(parametro.getIntervaloIntegracaoBancoCentral());
		return parametroService.save(parametroResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		parametroService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Parametro findById(@PathParam("id") Long id) {
		return parametroService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Parametro> getAll() {
		return parametroService.getAll();
	}

}
