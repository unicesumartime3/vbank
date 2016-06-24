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

import br.com.rp.domain.Email;
import br.com.rp.services.EmailService;

@Path("/email")
@Produces("application/json")
public class EmailRest {
	
	@EJB
	private EmailService emailService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Email save(Email email) {
		return emailService.save(email);
	}

	@PUT
	@Path("/update/{id}")
	public Email update(@PathParam("id") Long id, Email email) {
		Email emailResult = emailService.findById(id);
		emailResult.setRemetente(email.getRemetente());
		emailResult.setDestinatario(email.getDestinatario());
		emailResult.setAssunto(email.getAssunto());
		emailResult.setDescricao(email.getDescricao());
		emailResult.setDhEnvio(email.getDhEnvio());		
		emailResult.setSituacao(email.getSituacao());		
		return emailService.save(emailResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		emailService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Email findById(@PathParam("id") Long id) {
		return emailService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Email> getAll() {
		return emailService.getAll();
	}
	
	@GET
	@Path("/findByProposta/{idProposta}")
	public Email findByProposta(@PathParam("idProposta") Long idProposta){
		return emailService.findByProposta(idProposta);
	}
}
