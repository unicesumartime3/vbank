package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.UsuarioFuncionario;
import br.com.rp.repository.UsuarioFuncionarioRepository;

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
public class UsuarioFuncionarioRepositoryImpl extends AbstractRepositoryImpl<UsuarioFuncionario>
		implements UsuarioFuncionarioRepository {

	public UsuarioFuncionarioRepositoryImpl() {
		super(UsuarioFuncionario.class);
	}

}
