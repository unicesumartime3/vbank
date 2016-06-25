package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.integration.Integracao;
import br.com.rp.repository.IntegracaoRepository;

@Stateless
public class IntegracaoService {

	@EJB
	private IntegracaoRepository integracaoRepository;

	public List<Integracao> getAll() {
		return integracaoRepository.getAll();
	}

	public Integracao save(Integracao funcionario) {
		return integracaoRepository.save(funcionario);
	}

	public Integracao findById(Long id) {
		return integracaoRepository.findById(id);
	}

	public void remove(Long id) {
		integracaoRepository.remove(id);
	}

}
