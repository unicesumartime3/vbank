package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Deposito;
import br.com.rp.domain.Movimento;
import br.com.rp.domain.SituacaoDeposito;
import br.com.rp.repository.DepositoRepository;

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
public class DepositoService {

	@EJB
	private DepositoRepository depositoRepository;

	@EJB
	private MovimentoService movimentoService;

	public List<Deposito> getAll() {
		return depositoRepository.getAll();
	}

	public Deposito save(Deposito deposito) {
		try {
			Deposito deposit = depositoRepository.save(deposito);
			if (deposito.getSituacaoDeposito() == SituacaoDeposito.FINALIZADO) {
				movimentoService.save(new Movimento().addDeposito(deposit));
			}
			return deposit;
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Deposito findById(Long id) {
		return depositoRepository.findById(id);
	}

	public void remove(Long id) {
		depositoRepository.remove(id);
	}

}
