package br.com.rp.rest;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
	@Path("/save/{cpf}/{dataProposta}/{nome}/{regiao}/{renda}")
	public Proposta save(@PathParam("cpf") String cpf, @PathParam("dataProposta") Date dataProposta,
			@PathParam("nome") String nome, @PathParam("regiao") String regiao, @PathParam("renda") BigDecimal renda) {

		if (!propostaService.isCpfValido(cpf)) {
			throw new RuntimeException("O CPF informado é invalido.");
		} else if (propostaService.isPropostaUltimosTrintaDias(cpf)) {
			throw new RuntimeException("Há uma proposta para este CPF nos últimos 30 dias.");
		} else if (!clienteService.isCpfExistente(cpf)) {
			throw new RuntimeException("Já existe um cliente cadastrado com este CPF.");
		} else {

			Proposta proposta = new Proposta();
			proposta.setCpf(cpf);
			proposta.setDataProposta(dataProposta);
			proposta.setNome(nome);
			proposta.setRegiao(regiao);
			proposta.setRenda(renda);
			return propostaService.save(proposta);
		}
	}
	
	@GET
	@Path("/get/{id}")
	public Proposta findById(@PathParam("id") Long id){
		return propostaService.findById(id);
	}
}
