package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Agendamento;
import br.com.rp.repository.AgendamentoRepository;

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
public class AgendamentoService {

	@EJB
	private AgendamentoRepository agendamentoRepository;

	public List<Agendamento> getAll() {
		return agendamentoRepository.getAll();
	}

	public Agendamento save(Agendamento agendamento) {
		return agendamentoRepository.save(agendamento);
	}

	public Agendamento findById(Long id) {
		return agendamentoRepository.findById(id);
	}

	public void remove(Long id) {
		agendamentoRepository.remove(id);
	}
}
