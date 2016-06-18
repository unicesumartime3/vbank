package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Acao;
import br.com.rp.repository.AcaoRepository;

@Stateless
public class AcaoRepositoryImpl extends AbstractRepositoryImpl<Acao> implements AcaoRepository {

	public AcaoRepositoryImpl() {
		super(Acao.class);
	}

}
