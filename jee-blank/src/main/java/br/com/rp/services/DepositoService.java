package br.com.rp.services;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Deposito;
import br.com.rp.domain.Movimento;
import br.com.rp.domain.Parametro;
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

	@EJB
	private ParametroService parametroService;

	public List<Deposito> getAll() {
		return depositoRepository.getAll();
	}

	public Deposito save(Deposito deposito) {
		Parametro parametro = parametroService.findParametro();
		if (parametroService.isHorarioTransacaoValido(parametro, deposito)) {
			Deposito deposit = depositoRepository.save(deposito);
			if (deposito.getSituacaoDeposito() == SituacaoDeposito.FINALIZADO) {
				movimentoService.save(new Movimento().addMovimento(deposit));
			}
			return deposit;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			throw new RuntimeException(
					"Só são permitidas movimentações entre " + sdf.format(parametro.getHoraInicioTransacoes()) + " e "
							+ sdf.format(parametro.getHoraFimTransacoes()) + ".");
		}
	}

	public Deposito findById(Long id) {
		return depositoRepository.findById(id);
	}

	public void remove(Long id) {
		depositoRepository.remove(id);
	}

}
