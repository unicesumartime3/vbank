package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Movimento;
import br.com.rp.domain.Pagamento;
import br.com.rp.domain.SituacaoPagamento;
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

	@EJB
	private MovimentoService movimentoService;

	public List<Pagamento> getAll() {
		return pagamentoRepository.getAll();
	}

	public Pagamento save(Pagamento pagamento) {
		try {
			Pagamento pagament = pagamentoRepository.save(pagamento);
			if (pagamento.getSituacaoPagamento() == SituacaoPagamento.FINALIZADO) {
				movimentoService.save(new Movimento().addPagamento(pagament));
			}
			return pagament;
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Pagamento findById(Long id) {
		return pagamentoRepository.findById(id);
	}

	public void remove(Long id) {
		pagamentoRepository.remove(id);
	}

}
