package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Transferencia;
import br.com.rp.repository.TransferenciaRepository;

@Stateless
public class TransferenciaRepositoryimpl extends AbstractRepositoryImpl<Transferencia> implements TransferenciaRepository {

	public TransferenciaRepositoryimpl() {
		super(Transferencia.class);
	}
	
}
