package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Acao;
import br.com.rp.repository.AcaoRepository;

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
public class AcaoRepositoryImpl extends AbstractRepositoryImpl<Acao> implements AcaoRepository {

	public AcaoRepositoryImpl() {
		super(Acao.class);
	}

}
