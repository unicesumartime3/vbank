package br.com.rp.repository.service;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.MotivoRejeicao;
import br.com.rp.services.MotivoRejeicaoService;

public class MotivoRejeicaoServiceTest extends AbstractTest {

	@EJB
	private MotivoRejeicaoService motivoRejeicaoservice;

	@Test
	public void deveInserirMotivoComSucesso() {
		MotivoRejeicao motivo = new MotivoRejeicao();
		motivo.setDsMotivo("Renda insuficiente");
		motivoRejeicaoservice.save(motivo);
		Assert.assertNotNull(motivo.getId());
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveCompararDescricaoMotivoRejeicao() {
		Assert.assertEquals("Renda insuficiente", motivoRejeicaoservice.findById(100001L).getDsMotivo());
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, motivoRejeicaoservice.getAll().size());
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveRemoverMotivoComSucesso() {
		motivoRejeicaoservice.remove(100001L);
		Assert.assertEquals(1, motivoRejeicaoservice.getAll().size());
	}

	@Test
	@UsingDataSet("db/motivo_rejeicao.xml")
	public void deveAtualizarMotivoComSucesso() {
		MotivoRejeicao motivo = motivoRejeicaoservice.findById(100001L);
		motivo.setDsMotivo("Horário inválido para operação");
		motivoRejeicaoservice.save(motivo);
		Assert.assertEquals("Horário inválido para operação", motivoRejeicaoservice.findById(100001L).getDsMotivo());
	}

}