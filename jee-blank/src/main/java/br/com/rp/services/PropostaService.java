package br.com.rp.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.rp.domain.Proposta;
import br.com.rp.repository.PropostaRepository;

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
public class PropostaService {

	@PersistenceContext(unitName = "vbankpu")
	private EntityManager em;

	@EJB
	private PropostaRepository propostaRepository;

	public List<Proposta> getAll() {
		return propostaRepository.getAll();
	}

	public Proposta save(Proposta proposta) {
		return propostaRepository.save(proposta);
	}

	public Proposta findById(Long id) {
		return propostaRepository.findById(id);
	}

	public void remove(Long id) {
		propostaRepository.remove(id);
	}

	public List<Proposta> findByDataUltimaProposta(String cpf) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DATE, -30);
		return em
				.createQuery(
						"from " + Proposta.class.getSimpleName() + " where cpf = '" + cpf + "' and dataProposta > '"
								+ new SimpleDateFormat("yyyy-MM-dd").format(calendario.getTime()) + "'",
						Proposta.class)
				.getResultList();
	}

	public Boolean isPropostaUltimosTrintaDias(String cpf) {
		if (findByDataUltimaProposta(cpf).size() > 0) {
			return true;
		}
		return false;
	}

	public Boolean isCpfValido(String cpf) {
		try {
			new CPFValidator().assertValid(cpf);
		} catch (InvalidStateException e) {
			return false;
		}
		return true;
	}

}
