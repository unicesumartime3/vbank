package br.com.rp.services;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Movimento;
import br.com.rp.domain.Parametro;
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
		Parametro parametro = parametroService.findParametro();
		if (parametroService.isHorarioTransacaoValido(parametro, transferencia)) {
			Transferencia transf = transferenciaRepository.save(transferencia);
			if (transf.getSituacaoTransferencia() == SituacaoTransferencia.FINALIZADA) {
				movimentoService.save(new Movimento().addMovimento(transf));
			}
			return transf;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			throw new RuntimeException(
					"Só são permitidas movimentações entre " + sdf.format(parametro.getHoraInicioTransacoes()) + " e "
							+ sdf.format(parametro.getHoraFimTransacoes()) + ".");
		}
	}

	public Transferencia findById(Long id) {
		return transferenciaRepository.findById(id);
	}

}
