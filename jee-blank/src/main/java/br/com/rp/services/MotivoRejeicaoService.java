package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
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
public class MotivoRejeicaoService {

	@EJB
	private MotivoRejeicaoRepository motivoRejeicaoRepository;

	public List<MotivoRejeicao> getAll() {
		return motivoRejeicaoRepository.getAll();
	}

	public MotivoRejeicao save(MotivoRejeicao motivo) {
		return motivoRejeicaoRepository.save(motivo);
	}

	public MotivoRejeicao findById(Long id) {
		return motivoRejeicaoRepository.findById(id);
	}

	public void remove(Long id) {
		motivoRejeicaoRepository.remove(id);
	}
}
