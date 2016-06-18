package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.MotivoRejeicao;
import br.com.rp.repository.MotivoRejeicaoRepository;

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
public class MotivoRejeicaoRepositoryImpl extends AbstractRepositoryImpl<MotivoRejeicao> implements MotivoRejeicaoRepository{

	public MotivoRejeicaoRepositoryImpl() {
		super(MotivoRejeicao.class);
	}

}
