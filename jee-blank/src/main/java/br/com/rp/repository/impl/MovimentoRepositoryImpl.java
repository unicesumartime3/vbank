package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Movimento;
import br.com.rp.repository.MovimentoRepository;

@Stateless
public class MovimentoRepositoryImpl extends AbstractRepositoryImpl<Movimento> implements MovimentoRepository {

	public MovimentoRepositoryImpl() {
		super(Movimento.class);
	}
	
}
