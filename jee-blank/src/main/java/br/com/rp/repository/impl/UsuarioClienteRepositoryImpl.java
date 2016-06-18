package br.com.rp.repository.impl;

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
public class UsuarioClienteRepositoryImpl extends AbstractRepositoryImpl<UsuarioCliente> implements UsuarioClienteRepository {

	public UsuarioClienteRepositoryImpl() {
		super(UsuarioCliente.class);
	}

}
