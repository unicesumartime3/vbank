package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Parametro;
import br.com.rp.repository.ParametroRepository;

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
public class ParametroRepositoryImpl extends AbstractRepositoryImpl<Parametro> implements ParametroRepository {

	public ParametroRepositoryImpl() {
		super(Parametro.class);
	}

}
