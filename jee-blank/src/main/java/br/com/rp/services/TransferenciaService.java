package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Movimento;
import br.com.rp.domain.SituacaoTransferencia;
import br.com.rp.domain.Transferencia;
import br.com.rp.repository.TransferenciaRepository;

@Stateless
public class TransferenciaService {

	@EJB
	private TransferenciaRepository transferenciaRepository;

	@EJB
	private ParametroService parametroService;

	@EJB
	private MovimentoService movimentoService;

	public List<Transferencia> getAll() {
		return transferenciaRepository.getAll();
	}
	
	public void remove(Long id) {
		transferenciaRepository.remove(id);
	}

	public Transferencia save(Transferencia transferencia) {
		try {
//			 new SimpleDateFormat("HH:mm:ss").parse(new SimpleDateFormat("HH:mm:ss").format(transferencia.getDtTransferencia())).
//			Parametro parametro = parametroService.findParametro();
//			if (transferencia.getDtTransferencia().after(parametro.getHoraInicioTransacoes())
//					|| transferencia.getDtTransferencia().before(parametro.getHoraFimTransacoes())) {
				Transferencia transf = transferenciaRepository.save(transferencia);
				if (transf.getSituacaoTransferencia() == SituacaoTransferencia.FINALIZADA) {
					movimentoService.save(new Movimento().addTransferencia(transferencia));
				}
				return transf;
			// } else
			// throw new RuntimeException("Só são permitidas movimentações entre
			// "
			// + parametro.getHoraInicioTransacoes() + " e " +
			// parametro.getHoraFimTransacoes() + ".");
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Transferencia findById(Long id) {
		return transferenciaRepository.findById(id);
	}

}
