package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Cargo;
import br.com.rp.repository.CargoRepository;

@Stateless
public class CargoService {

	@EJB
	CargoRepository cargoRepository;
	
	public List<Cargo> getAll(){
		return cargoRepository.getAll();
	}
	
	public Cargo save(Cargo cargo){
		return cargoRepository.save(cargo);
	}
	
	public Cargo findById(Long id){
		return cargoRepository.findById(id);
	}
	
	public void remove(Long id){
		cargoRepository.remove(id);
	}
}
