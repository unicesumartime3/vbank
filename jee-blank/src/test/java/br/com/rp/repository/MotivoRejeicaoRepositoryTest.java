package br.com.rp.repository;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cargo;
import br.com.rp.domain.MotivoRejeicao;

public class MotivoRejeicaoRepositoryTest extends AbstractTest {

	@EJB
	private MotivoRejeicaoRepository motivoRejeicaoRepository;

	@Test
	public void deveInserirCargoComSucesso() {
		MotivoRejeicao motivo = new MotivoRejeicao();
		motivo.setDsMotivo("Renda insuficiente");
		motivoRejeicaoRepository.save(motivo);
		Assert.assertNotNull(motivo.getId());
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveCompararDescricaoMotivoRejeicao() {
		Assert.assertEquals("Renda insuficiente", motivoRejeicaoRepository.findById(100001L).getDsMotivo());
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, motivoRejeicaoRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveRemoverCargoComSucesso() {
		motivoRejeicaoRepository.remove(100001L);
		Assert.assertEquals(1, motivoRejeicaoRepository.getAll().size());
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveAtualizarDescricaoComSucesso() {
		MotivoRejeicao motivo = motivoRejeicaoRepository.findById(100001L);
		motivo.setDsMotivo("Horário inválido para operação");
		motivoRejeicaoRepository.save(motivo);
		Assert.assertEquals("Horário inválido para operação", motivoRejeicaoRepository.findById(100001L).getDsMotivo());
	}

}