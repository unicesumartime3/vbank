package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Pagamento;
import br.com.rp.repository.PagamentoRepository;

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
public class PagamentoRepositoryImpl extends AbstractRepositoryImpl<Pagamento> implements PagamentoRepository {

	public PagamentoRepositoryImpl() {
		super(Pagamento.class);
	}

}
