package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Banco;
import br.com.rp.repository.BancoRepository;

@Stateless
public class BancoService {

	@EJB
	private BancoRepository bancoRepository;

	public List<Banco> getAll() {
		return bancoRepository.getAll();
	}

	public Banco save(Banco banco) {
		return bancoRepository.save(banco);
	}

	public Banco findById(Long id) {
		return bancoRepository.findById(id);
	}

	public void remove(Long id) {
		bancoRepository.remove(id);
	}
}
