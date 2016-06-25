package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Banco;
import br.com.rp.repository.BancoRepository;

@Stateless
public class BancoRepositoryImpl extends AbstractRepositoryImpl<Banco> implements BancoRepository {

	public BancoRepositoryImpl() {
		super(Banco.class);
	}

}
