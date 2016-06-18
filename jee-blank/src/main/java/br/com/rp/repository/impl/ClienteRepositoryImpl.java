package br.com.rp.repository.impl;

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
public class ClienteRepositoryImpl extends AbstractRepositoryImpl<Cliente> implements ClienteRepository {

	public ClienteRepositoryImpl() {
		super(Cliente.class);
	}

}
