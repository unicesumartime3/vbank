package br.com.rp.repository;

import java.util.Calendar;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Parametro;

public class ParametroRepositoryTest extends AbstractTest {
	@EJB
	private ParametroRepository parametroRepository;

	@Test
	public void deveInserirParametroComSucesso() {
		Parametro parametro = new Parametro();
		parametro.setHoraFimTransacoes(Calendar.getInstance().getTime());
		parametro.setHoraInicioTransacoes(Calendar.getInstance().getTime());
		parametro.setHorarioIntegracaoEUA(Calendar.getInstance().getTime());
		parametro.setIntervaloIntegracaoBancoCentral(5L);
		parametroRepository.save(parametro);
		Assert.assertNotNull(parametro.getId());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveCompararDescricaoParametro() {
		Parametro parametro = parametroRepository.findById(100001L);
		Assert.assertEquals(new Long(5), parametro.getIntervaloIntegracaoBancoCentral());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, parametroRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveRemoverParametroComSucesso() {
		parametroRepository.remove(100001L);
		Assert.assertEquals(1, parametroRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveAtualizarIntervaloIntegracaoBacenComSucesso() {
		Parametro parametro = parametroRepository.findById(100001L);
		parametro.setIntervaloIntegracaoBancoCentral(10L);
		parametroRepository.save(parametro);
		Assert.assertEquals(new Long(10), parametroRepository.findById(100001L).getIntervaloIntegracaoBancoCentral());
	}
}
