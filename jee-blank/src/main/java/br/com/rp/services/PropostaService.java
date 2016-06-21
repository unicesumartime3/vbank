package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Proposta;
import br.com.rp.domain.UsuarioFuncionario;
import br.com.rp.repository.PropostaRepository;

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
public class PropostaService {

	@EJB
	private PropostaRepository propostaRepository;
	
	public List<Proposta> getAll(){
		return propostaRepository.getAll();
	}
	
	public Proposta save(Proposta proposta){
		return propostaRepository.save(proposta);
	}
	
	public Proposta findById(Long id){
		return propostaRepository.findById(id);
	}
	
	public void remove(Long id){
		propostaRepository.remove(id);
	}
}
