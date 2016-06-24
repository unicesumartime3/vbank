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

import br.com.rp.domain.Proposta;
import br.com.rp.services.ClienteService;
import br.com.rp.services.PropostaService;

@Path("/proposta")
@Produces("application/json")
public class PropostaRest {

	@EJB
	private PropostaService propostaService;

	@EJB
	private ClienteService clienteService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Proposta save(Proposta proposta) {

		if (!propostaService.isCpfValido(proposta.getCpf())) {
			throw new RuntimeException("O CPF informado é invalido.");
		} else if (propostaService.isPropostaUltimosTrintaDias(proposta.getCpf())) {
			throw new RuntimeException("Há uma proposta para este CPF nos últimos 30 dias.");
		} else if (clienteService.isCpfExistente(proposta.getCpf())) {
			throw new RuntimeException("Já existe um cliente cadastrado com este CPF.");
		} else {
			return propostaService.save(proposta);
		}
	}

	@PUT
	@Path("/update/{id}")
	public Proposta update(@PathParam("id") Long id, Proposta proposta) {
		/*
		 * O front-end vai bloquear do CPF da proposta para não ser alterado.
		 */
		Proposta propostaResult = propostaService.findById(id);
		propostaResult.setCpf(proposta.getCpf());
		propostaResult.setDataProposta(proposta.getDataProposta());
		propostaResult.setNome(proposta.getNome());
		propostaResult.setRegiao(proposta.getRegiao());
		propostaResult.setRenda(proposta.getRenda());
		propostaResult.setSituacaoProposta(proposta.getSituacaoProposta());
		propostaResult.setEmail(proposta.getEmail());

		if (proposta.getMotivoRejeicao() != null) {
			propostaResult.setMotivoRejeicao(proposta.getMotivoRejeicao());
		}
		if (proposta.getUsuarioAnalise() != null) {
			propostaResult.setUsuarioAnalise(proposta.getUsuarioAnalise());
		}
		return propostaService.save(propostaResult);
	}

	@DELETE
	@Path("/remove/{id}")
	public void remove(@PathParam("id") Long id) {
		propostaService.remove(id);
	}

	@GET
	@Path("/findById/{id}")
	public Proposta findById(@PathParam("id") Long id) {
		return propostaService.findById(id);
	}

	@GET
	@Path("/getAll")
	public List<Proposta> getAll() {
		return propostaService.getAll();
	}

	@PUT
	@Path("/rejeitarProposta/{idProposta}/{mensagemRejeicao}/{idUsuarioAnalise}")
	public Proposta rejeitarProposta(@PathParam("idProposta") Long idProposta,
			@PathParam("mensagemRejeicao") String mensagemRejeicao,
			@PathParam("idUsuarioAnalise") Long idUsuarioAnalise) {
		return propostaService.rejeitarProposta(idProposta, mensagemRejeicao, idUsuarioAnalise);
	}

	@PUT
	@Path("/aprovarProposta/{idProposta}/{idUsuarioAnalise}")
	public Proposta aprovarProposta(@PathParam("idProposta") Long idProposta,
			@PathParam("idUsuarioAnalise") Long idUsuarioAnalise) {
		return propostaService.aprovarProposta(idProposta, idUsuarioAnalise);
	}
	
	@GET
	@Path("/findByRegiao/{regiao}")
	public List<Proposta> getPropostasRegiao(@PathParam("regiao") String regiao){
		return propostaService.findByRegiao(regiao);
	}

}
