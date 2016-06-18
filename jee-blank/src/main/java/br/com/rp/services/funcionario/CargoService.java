package br.com.rp.services.funcionario;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.funcionario.Cargo;
import br.com.rp.repository.funcionario.CargoRepository;

@Stateless
public class CargoService {

	@EJB
	CargoRepository cargoRepository;
	
	public List<Cargo> getAll(){
		return cargoRepository.getAll();
	}
}
