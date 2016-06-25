package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.rp.domain.Parametro;
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
}
