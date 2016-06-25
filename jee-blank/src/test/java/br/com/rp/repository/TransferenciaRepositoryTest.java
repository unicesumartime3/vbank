package br.com.rp.repository;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.SituacaoTransferencia;
import br.com.rp.domain.TipoConta;
import br.com.rp.domain.Transferencia;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class TransferenciaRepositoryTest extends AbstractTest {

	@EJB
	private TransferenciaRepository transferenciaRepository;

	@EJB
	private ClienteRepository clienteRepository;

	@EJB
	private BancoRepository bancoRepository;

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveInserirTransferenciaComSucesso() {
		Transferencia transferencia = new Transferencia();
		transferencia.setClienteRemetente(clienteRepository.findById(100001L));
		transferencia.setDtTransferencia(Calendar.getInstance().getTime());
		transferencia.setVlTranferencia(new BigDecimal(500.00));
		transferencia.setNrContaFavorecido("546489");
		transferencia.setAgenciaFavorecido("4545856");
		transferencia.setEmailFavorecido("unicesumartime3@gmail.com");
		transferencia.setTipoContaFavorecido(TipoConta.CORRENTE);
		transferencia.setTipoContaDebito(TipoConta.CORRENTE);
		transferencia.setSituacaoTransferencia(SituacaoTransferencia.FINALIZADA);
		transferencia.setBancoFavorecido(bancoRepository.findById(100001L));

		transferenciaRepository.save(transferencia);
		Assert.assertNotNull(transferencia.getId());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveCompararAgenciaFavorecidoTransferencia() {
		Transferencia transferencia = transferenciaRepository.findById(100001L);
		Assert.assertEquals("4455668", transferencia.getAgenciaFavorecido());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, transferenciaRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveRemoverTransferenciaComSucesso() {
		transferenciaRepository.remove(100001L);
		Assert.assertEquals(1, transferenciaRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/transferencia.xml")
	public void deveAtualizarNrContaFavorecidoComSucesso() {
		Transferencia transferencia = transferenciaRepository.findById(100001L);
		transferencia.setNrContaFavorecido("4585698");
		transferenciaRepository.save(transferencia);
		Assert.assertEquals("4585698", transferenciaRepository.findById(100001L).getNrContaFavorecido());
	}
}
