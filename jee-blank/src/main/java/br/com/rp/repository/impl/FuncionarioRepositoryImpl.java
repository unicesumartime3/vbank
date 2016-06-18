package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Funcionario;
import br.com.rp.repository.FuncionarioRepository;

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
public class FuncionarioRepositoryImpl extends AbstractRepositoryImpl<Funcionario> implements FuncionarioRepository {

	public FuncionarioRepositoryImpl() {
		super(Funcionario.class);
	}

}
