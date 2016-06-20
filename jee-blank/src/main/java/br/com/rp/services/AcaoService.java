package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Acao;
import br.com.rp.repository.AcaoRepository;

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

@Stateless
public class AcaoService {
	
	@EJB
	AcaoRepository acaoRepository;
	
	public List<Acao> getAll(){
		return acaoRepository.getAll();
	}
	
	public Acao save(Acao acao){
		return acaoRepository.save(acao);
	}
	
	public Acao findById(Long id){
		return acaoRepository.findById(id);
	}
	
	public void remove(Long id){
		acaoRepository.remove(id);
	}
}
