package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Acao;
import br.com.rp.repository.AcaoRepository;

@Stateless
public class AcaoService {
	
	@EJB
	AcaoRepository acaoRepository;
	
	public List<Acao> getAll(){
		return acaoRepository.getAll();
	}
}
