package br.com.rp.repository.funcionario;

import javax.ejb.Local;

import br.com.rp.domain.funcionario.Cargo;
import br.com.rp.repository.Repository;

@Local
public interface CargoRepository extends Repository<Cargo>{

}
