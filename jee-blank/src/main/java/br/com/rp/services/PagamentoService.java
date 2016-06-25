package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
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
public class PagamentoService {
	
	@EJB
	private PagamentoRepository pagamentoRepository;
	
	public List<Pagamento> getAll() {
		return pagamentoRepository.getAll();
	}

	public Pagamento save(Pagamento pagamento) {
		return pagamentoRepository.save(pagamento);
	}

	public Pagamento findById(Long id) {
		return pagamentoRepository.findById(id);
	}

	public void remove(Long id) {
		pagamentoRepository.remove(id);
	}

}
