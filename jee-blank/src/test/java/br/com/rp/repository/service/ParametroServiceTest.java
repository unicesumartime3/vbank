package br.com.rp.repository.service;

import java.util.Calendar;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Parametro;
import br.com.rp.services.ParametroService;

public class ParametroServiceTest extends AbstractTest {
	@EJB
	private ParametroService parametroService;

	@Test
	public void deveInserirParametroComSucesso() {
		Parametro parametro = new Parametro();
		parametro.setHoraFimTransacoes(Calendar.getInstance().getTime());
		parametro.setHoraInicioTransacoes(Calendar.getInstance().getTime());
		parametro.setHorarioIntegracaoEUA(Calendar.getInstance().getTime());
		parametro.setIntervaloIntegracaoBancoCentral(5L);
		parametroService.save(parametro);
		Assert.assertNotNull(parametro.getId());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveCompararDescricaoParametro() {
		Parametro parametro = parametroService.findById(100001L);
		Assert.assertEquals(new Long(5), parametro.getIntervaloIntegracaoBancoCentral());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, parametroService.getAll().size());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveRemoverParametroComSucesso() {
		parametroService.remove(100001L);
		Assert.assertEquals(1, parametroService.getAll().size());
	}

	@Test
	@UsingDataSet("db/parametro.xml")
	public void deveAtualizarIntervaloIntegracaoBacenComSucesso() {
		Parametro parametro = parametroService.findById(100001L);
		parametro.setIntervaloIntegracaoBancoCentral(10L);
		parametroService.save(parametro);
		Assert.assertEquals(new Long(10), parametroService.findById(100001L).getIntervaloIntegracaoBancoCentral());
	}
}
