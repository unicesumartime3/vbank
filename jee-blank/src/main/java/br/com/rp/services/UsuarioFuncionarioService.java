package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.UsuarioFuncionario;
import br.com.rp.repository.UsuarioFuncionarioRepository;

@Stateless
public class UsuarioFuncionarioService  {

	@EJB
	private UsuarioFuncionarioRepository usuarioFuncionarioRepository;
	
	public List<UsuarioFuncionario> getAll(){
		return usuarioFuncionarioRepository.getAll();
	}
}
