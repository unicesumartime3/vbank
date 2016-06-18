package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Cargo;
import br.com.rp.repository.CargoRepository;

@Stateless
public class CargoRopositoryImpl extends AbstractRepositoryImpl<Cargo> implements CargoRepository {

	public CargoRopositoryImpl() {
		super(Cargo.class);
	}

}
