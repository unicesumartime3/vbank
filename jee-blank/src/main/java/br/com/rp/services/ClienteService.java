package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Cliente;
import br.com.rp.repository.ClienteRepository;

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
public class ClienteService  {
	
	@EJB
	ClienteRepository clienteRepository;
	
	public List<Cliente> getAll(){
		return clienteRepository.getAll();
	}
	
	public Cliente save(Cliente cliente){
		return clienteRepository.save(cliente);
	}
	
	public Cliente findById(Long id){
		return clienteRepository.findById(id);
	}
	
	public void remove(Long id){
		clienteRepository.remove(id);
	}

}
