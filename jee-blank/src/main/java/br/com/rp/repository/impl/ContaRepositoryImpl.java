package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Conta;
import br.com.rp.repository.ContaRepository;

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
public class ContaRepositoryImpl extends AbstractRepositoryImpl<Conta> implements ContaRepository{

	public ContaRepositoryImpl() {
		super(Conta.class);
	}

}
