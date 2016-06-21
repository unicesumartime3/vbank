package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Cargo;
import br.com.rp.domain.Funcionario;
import br.com.rp.repository.FuncionarioRepository;

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
public class FuncionarioService {

	@EJB
	private FuncionarioRepository funcionarioRepository;
	
	public List<Funcionario> getAll() {
		return funcionarioRepository.getAll();
	}
	
	
	public Funcionario save(Funcionario funcionario){
		return funcionarioRepository.save(funcionario);
	}
	
	public Funcionario findById(Long id){
		return funcionarioRepository.findById(id);
	}
	
	public void remove(Long id){
		funcionarioRepository.remove(id);
	}
}
