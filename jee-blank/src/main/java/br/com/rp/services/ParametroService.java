package br.com.rp.services;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.rp.domain.Deposito;
import br.com.rp.domain.Pagamento;
import br.com.rp.domain.Parametro;
import br.com.rp.domain.Transferencia;
import br.com.rp.repository.ParametroRepository;

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
public class ParametroService {

	@PersistenceContext(unitName = "vbankpu")
	private EntityManager em;

	@EJB
	private ParametroRepository parametroRepository;

	public List<Parametro> getAll() {
		return parametroRepository.getAll();
	}

	public Parametro save(Parametro parametro) {
		return parametroRepository.save(parametro);
	}

	public Parametro findParametro() {
		return em.createQuery("from " + Parametro.class.getSimpleName(), Parametro.class).getSingleResult();
	}

	public Parametro findById(Long id) {
		return parametroRepository.findById(id);
	}

	public void remove(Long id) {
		parametroRepository.remove(id);
	}

	public Boolean isHorarioTransacaoValido(Parametro parametro, Object object) {
		Calendar horaTransferencia = Calendar.getInstance();
		if (object instanceof Deposito) {
			if (((Deposito) object).getAgendamento() != null)
				horaTransferencia.setTime(((Deposito) object).getAgendamento().getDataAgendamento());
			else
				horaTransferencia.setTime(((Deposito) object).getDtDeposito());
		} else if (object instanceof Transferencia) {
			if (((Transferencia) object).getAgendamento() != null)
				horaTransferencia.setTime(((Transferencia) object).getAgendamento().getDataAgendamento());
			else
				horaTransferencia.setTime(((Transferencia) object).getDtTransferencia());
		} else if (object instanceof Pagamento) {
			if (((Pagamento) object).getAgendamento() != null)
				horaTransferencia.setTime(((Pagamento) object).getAgendamento().getDataAgendamento());
			else
				horaTransferencia.setTime(((Pagamento) object).getDtPagamento());
		}

		horaTransferencia.set(Calendar.DATE, 1);
		horaTransferencia.set(Calendar.MONTH, 0);
		horaTransferencia.set(Calendar.YEAR, 2000);

		Calendar horaInicioTransacoes = Calendar.getInstance();
		horaInicioTransacoes.setTime(parametro.getHoraInicioTransacoes());
		horaInicioTransacoes.set(Calendar.DATE, 1);
		horaInicioTransacoes.set(Calendar.MONTH, 0);
		horaInicioTransacoes.set(Calendar.YEAR, 2000);

		Calendar horaFimTransacoes = Calendar.getInstance();
		horaFimTransacoes.setTime(parametro.getHoraFimTransacoes());
		horaFimTransacoes.set(Calendar.DATE, 1);
		horaFimTransacoes.set(Calendar.MONTH, 0);
		horaFimTransacoes.set(Calendar.YEAR, 2000);

		if (horaTransferencia.getTime().after(horaInicioTransacoes.getTime())
				&& horaTransferencia.getTime().before(horaFimTransacoes.getTime())) {
			return true;
		}
		return false;

	}
}
