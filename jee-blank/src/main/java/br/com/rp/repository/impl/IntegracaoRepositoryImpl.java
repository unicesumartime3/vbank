package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.integration.Integracao;
import br.com.rp.repository.IntegracaoRepository;

@Stateless
public class IntegracaoRepositoryImpl extends AbstractRepositoryImpl<Integracao> implements IntegracaoRepository {

	public IntegracaoRepositoryImpl() {
		super(Integracao.class);
	}

}
