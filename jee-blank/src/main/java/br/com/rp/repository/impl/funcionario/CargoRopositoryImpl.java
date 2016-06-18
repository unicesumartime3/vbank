package br.com.rp.repository.impl.funcionario;

import javax.ejb.Stateless;

import br.com.rp.domain.funcionario.Cargo;
import br.com.rp.repository.funcionario.CargoRepository;
import br.com.rp.repository.impl.AbstractRepositoryImpl;

@Stateless
public class CargoRopositoryImpl extends AbstractRepositoryImpl<Cargo> implements CargoRepository {

	public CargoRopositoryImpl() {
		super(Cargo.class);
	}

}
