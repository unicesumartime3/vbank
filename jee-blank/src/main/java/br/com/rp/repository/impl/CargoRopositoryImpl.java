package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Cargo;
import br.com.rp.repository.CargoRepository;

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
public class CargoRopositoryImpl extends AbstractRepositoryImpl<Cargo> implements CargoRepository {

	public CargoRopositoryImpl() {
		super(Cargo.class);
	}

}
