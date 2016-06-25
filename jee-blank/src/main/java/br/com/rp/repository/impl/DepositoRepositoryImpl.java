package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Deposito;
import br.com.rp.repository.DepositoRepository;

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
public class DepositoRepositoryImpl extends AbstractRepositoryImpl<Deposito> implements DepositoRepository {

	public DepositoRepositoryImpl() {
		super(Deposito.class);
	}

}
