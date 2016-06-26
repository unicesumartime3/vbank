package br.com.rp.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
		
    	Calendar horaTransferencia = Calendar.getInstance();
    	horaTransferencia.setTime(transferencia.getDtTransferencia());
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
	
			Transferencia transf = transferenciaRepository.save(transferencia);
			if (transf.getSituacaoTransferencia() == SituacaoTransferencia.FINALIZADA) {
				movimentoService.save(new Movimento().addTransferencia(transferencia));
			}
			return transf;
		 } else {
			 SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			 throw new RuntimeException("Só são permitidas movimentações entre "
					 + sdf.format(parametro.getHoraInicioTransacoes()) + " e " 
					 + sdf.format(parametro.getHoraFimTransacoes()) + ".");
		 }

	}

	public Transferencia findById(Long id) {
		return transferenciaRepository.findById(id);
	}

}
