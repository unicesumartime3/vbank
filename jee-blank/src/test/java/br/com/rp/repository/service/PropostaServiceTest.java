package br.com.rp.repository.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Cargo;
import br.com.rp.domain.Funcionario;
import br.com.rp.domain.MotivoRejeicao;
import br.com.rp.domain.Proposta;
import br.com.rp.domain.SituacaoProposta;
import br.com.rp.domain.UsuarioFuncionario;
import br.com.rp.services.CargoService;
import br.com.rp.services.ClienteService;
import br.com.rp.services.ContaService;
import br.com.rp.services.EmailService;
import br.com.rp.services.FuncionarioService;
import br.com.rp.services.MotivoRejeicaoService;
import br.com.rp.services.PropostaService;
import br.com.rp.services.UsuarioFuncionarioService;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class PropostaServiceTest extends AbstractTest {

	@EJB
	private MotivoRejeicaoService motivoRejeicaoService;

	@EJB
	private CargoService cargoService;

	@EJB
	private FuncionarioService funcionarioService;

	@EJB
	private UsuarioFuncionarioService usuarioFuncionarioService;

	@EJB
	private PropostaService propostaService;

	@EJB
	private EmailService emailService;

	@EJB
	private ClienteService clienteService;

	@EJB
	private ContaService contaService;

	@Test
	public void deveInserirPropostaComSucesso() throws ParseException {
		MotivoRejeicao motivo = new MotivoRejeicao();
		motivo.setDsMotivo("Renda insuficiente");
		motivoRejeicaoService.save(motivo);

		Cargo cargo = new Cargo();
		cargo.setDescricaoCargo("Gerente de vendas");
		cargoService.save(cargo);

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Rafael Suzin");
		funcionario.setEmail("rafael@gmail.com");
		funcionario.setCpf("08564856652");
		funcionario.setCargo(cargo);
		funcionarioService.save(funcionario);

		UsuarioFuncionario usuarioFuncionario = new UsuarioFuncionario();
		usuarioFuncionario.setLogin("RAFAEL");
		usuarioFuncionario.setNome("Rafael Suzin");
		usuarioFuncionario.setSenha("123456");
		usuarioFuncionario.setFuncionario(funcionario);
		usuarioFuncionarioService.save(usuarioFuncionario);

		Date dataProposta = new SimpleDateFormat("dd-mm-yyyy").parse("24-05-2016");

		Proposta proposta = new Proposta();
		proposta.setCpf("09006848956");
		proposta.setNome("José");
		proposta.setRegiao("Sul");
		proposta.setRenda(new BigDecimal(500.00));
		proposta.setDataProposta(dataProposta);
		proposta.setUsuarioAnalise(usuarioFuncionario);
		proposta.setMotivoRejeicao(motivo);
		proposta.setSituacaoProposta(SituacaoProposta.ABERTA);
		proposta.setEmail("jose@gmail.com");
		propostaService.save(proposta);
		Assert.assertNotNull(proposta.getId());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, propostaService.getAll().size());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveCompararNomeProposta() {
		Assert.assertEquals("Maria", propostaService.findById(100002L).getNome());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveCompararCPFProposta() {
		Assert.assertEquals("25283798143", propostaService.findById(100001L).getCpf());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveCompararNomeFuncionarioAnalise() {
		Assert.assertEquals("Rafael Suzin", usuarioFuncionarioService.findById(100001L).getFuncionario().getNome());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRemovePropostaComSucesso() {
		propostaService.remove(100001L);
		Assert.assertNull(propostaService.findById(100001L));
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveAlterarPropostaComSucesso() {
		Proposta proposta = propostaService.findById(100001L);
		proposta.setNome("Joaquim");
		proposta.setRenda(new BigDecimal(800.00));
		propostaService.save(proposta);
		Assert.assertEquals("Joaquim", propostaService.findById(100001L).getNome());
	}

	/*
	 * A Exception esperada é InvalidStateException, porém o arquillian lança
	 * outra exceção.
	 */
	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveValidarCpfIncorreto() {
		Assert.assertFalse(propostaService.isCpfValido("25243791453"));
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveValidarCpfCorreto() {
		Assert.assertTrue(propostaService.isCpfValido("25243798843"));
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRetornarPropostasUltimosTrintaDias() {
		Assert.assertFalse(propostaService.findByDataUltimaProposta("25243798843").isEmpty());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveCompararPropostasUltimosTrintaDias() {
		Assert.assertEquals(1, propostaService.findByDataUltimaProposta("25243798843").size());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveVerificarUltimosTrintaDiasExistente() {
		Assert.assertTrue(propostaService.isPropostaUltimosTrintaDias("25243798843"));
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveVerificarUltimosTrintaDiasNaoExistente() {
		Assert.assertFalse(propostaService.isPropostaUltimosTrintaDias("25243794543"));
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRetornarDuasPropostas() {
		Assert.assertEquals(2, propostaService.getAll().size());
	}

	@Test
	@UsingDataSet("db/proposta.xml")
	public void deveRetornarPropostaComStatusAberta() {
		Assert.assertEquals(SituacaoProposta.ABERTA, propostaService.findById(100001L).getSituacaoProposta());
	}

	@Test
	@UsingDataSet(value = { "db/proposta.xml" })
	public void deveRejeitarPropostaComSucesso() {
		Proposta prop = propostaService.findById(100001L);
		Assert.assertEquals(SituacaoProposta.ABERTA, prop.getSituacaoProposta());
		Assert.assertTrue(emailService.getAll().isEmpty());
		Proposta proposta = propostaService.rejeitarProposta(100001L, "Renda insuficiente para abertura de cadastro",
				100002L);
		Assert.assertEquals(SituacaoProposta.REJEITADA, proposta.getSituacaoProposta());
		Assert.assertEquals("Renda insuficiente para abertura de cadastro", proposta.getMotivoRejeicao().getDsMotivo());
		Assert.assertEquals(new Long(100002), proposta.getUsuarioAnalise().getId());
		Assert.assertEquals(1, emailService.getAll().size());
		propostaService.remove(proposta.getId());
		/*
		 * Foi implementado um remove do email adicionado, pois o h2 tenta
		 * remover o registro automáticamente temina a execução de cada método,
		 * e a proposta não podia ser excluída pois estava vinculada a um email.
		 */
		emailService.remove(emailService.findByProposta(proposta.getId()).getId());;
	}

	@Test
	@UsingDataSet(value = { "db/proposta.xml" })
	public void deveAprovarPropostaComSucesso() {
		Proposta prop = propostaService.findById(100001L);
		Assert.assertNotNull(prop);
		Assert.assertNull(prop.getMotivoRejeicao());
		Assert.assertNull(prop.getUsuarioAnalise());
		Assert.assertEquals(SituacaoProposta.ABERTA, prop.getSituacaoProposta());
		Proposta proposta = propostaService.aprovarProposta(100001L, 100002L);
		Assert.assertEquals(SituacaoProposta.APROVADA, proposta.getSituacaoProposta());
		Assert.assertEquals(new Long(100002), proposta.getUsuarioAnalise().getId());
		Assert.assertEquals(1, clienteService.getAll().size());
		Assert.assertEquals(1, contaService.getAll().size());
		/*
		 * Foi implementado um remove do email adicionado, pois o h2 tenta
		 * remover o registro automáticamente temina a execução de cada método,
		 * e a proposta não podia ser excluída pois estava vinculada a um email.
		 */
		emailService.remove(emailService.findByProposta(proposta.getId()).getId());;
	}
}
