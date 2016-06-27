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

import br.com.rp.domain.Transferencia;
import br.com.rp.services.TransferenciaService;

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

@Path("/transferencia")
@Produces("application/json")
public class TransferenciaRest {
	
	@EJB
	private TransferenciaService transferenciaService;
	
	@GET
	@Path("/findById/{id}")
	public Transferencia findById(@PathParam("id") Long id){
		return transferenciaService.findById(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Transferencia save(Transferencia transferencia){
		return transferenciaService.save(transferencia);
	}
	
	@PUT
	@Path("/update/{id}")
	public Transferencia update(@PathParam("id") Long id, Transferencia transferencia){
		Transferencia transferenciaResult = transferenciaService.findById(id);
		transferenciaResult.setSituacaoTransferencia(transferencia.getSituacaoTransferencia());		
		return transferenciaService.save(transferencia);
	}
	
	/*Remove, pela regra não é disponibilizado para o Front, porém aqui
	foi colocado para fazer o teste, pois se não remover, dá problema com
	o xml do DBUnit, erro relacionado com constraint, porém aqui poderá
	ser validado também permissão para acesso.*/
	
	@DELETE
	@Path("/remove/{id}")
	public void remove (@PathParam("id") Long id){
		transferenciaService.remove(id);
	}
	
	@GET
	@Path("/getAll")
	public List<Transferencia> getAll(){
		return transferenciaService.getAll();
	}

}
