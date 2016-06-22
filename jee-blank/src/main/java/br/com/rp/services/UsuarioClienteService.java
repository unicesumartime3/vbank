package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.UsuarioCliente;
import br.com.rp.repository.UsuarioClienteRepository;

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
public class UsuarioClienteService {

	@EJB 
	private UsuarioClienteRepository usuarioClienteRepository;
	
	public List<UsuarioCliente> getAll() {
		return usuarioClienteRepository.getAll();
	}
	
	public UsuarioCliente save(UsuarioCliente usuarioCliente){
		return usuarioClienteRepository.save(usuarioCliente);
	}
	
	public UsuarioCliente findById(Long id){
		return usuarioClienteRepository.findById(id);
	}
	
	public void remove(Long id){
		usuarioClienteRepository.remove(id);
	}	
		
}
