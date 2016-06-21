package br.com.rp.repository;

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
import br.com.rp.domain.UsuarioFuncionario;

public class PropostaRepositoryTest extends AbstractTest{
	
	@EJB
	private MotivoRejeicaoRepository motivoRejeicaoRepository;
	
	@EJB
	private CargoRepository cargoRepository;
	
	@EJB
	private FuncionarioRepository funcionarioRepository;
	
	@EJB
	private UsuarioFuncionarioRepository usuarioFuncionarioRepository;

	@EJB
	private PropostaRepository propostaRepository;
		
	@Test
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
	public void deveInserirPropostaComSucesso() throws ParseException {
		MotivoRejeicao motivo = new MotivoRejeicao();
		motivo.setDsMotivo("Renda insuficiente");
		motivoRejeicaoRepository.save(motivo);
		
		Cargo cargo = new Cargo();
		cargo.setDescricaoCargo("Gerente de vendas");
		cargoRepository.save(cargo);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Rafael Suzin");
		funcionario.setCpf("08564856652");
		funcionario.setCargo(cargo);
		funcionarioRepository.save(funcionario);

		UsuarioFuncionario usuarioFuncionario = new UsuarioFuncionario();
		usuarioFuncionario.setLogin("RAFAEL");
		usuarioFuncionario.setNome("Rafael Suzin");
		usuarioFuncionario.setSenha("123456");
		usuarioFuncionario.setFuncionario(funcionario);
		usuarioFuncionarioRepository.save(usuarioFuncionario);
				
		Date dataProposta = new SimpleDateFormat ("dd-mm-yyyy").parse("24-05-2016");
		
		Proposta proposta = new Proposta();
		proposta.setCpf("09006848956");
		proposta.setNome("José");
		proposta.setRegiao("Sul");
		proposta.setRenda(new BigDecimal(500.00));
		proposta.setDataProposta(dataProposta);
		proposta.setUsuarioAnalise(usuarioFuncionario);
		proposta.setMotivoRejeicao(motivo);
		propostaRepository.save(proposta);
		Assert.assertNotNull(proposta.getId());
	}
	
	@Test
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
	@UsingDataSet("db/proposta.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, propostaRepository.getAll().size());
	}
	
	@Test
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
	@UsingDataSet("db/proposta.xml")
	public void deveCompararNomeProposta() {
		Assert.assertEquals("Maria", propostaRepository.findById(100002L).getNome());
	}
	
	@Test
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
	@UsingDataSet("db/proposta.xml")
	public void deveCompararCPFProposta() {
		Assert.assertEquals("09006848956", propostaRepository.findById(100001L).getCpf());
	}
	
	@Test
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
	@UsingDataSet("db/proposta.xml")
	public void deveCompararNomeFuncionarioAnalise(){
		Assert.assertEquals("Rafael Suzin", usuarioFuncionarioRepository.findById(100001L).getFuncionario().getNome());
	}
	
	@Test
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
	@UsingDataSet("db/proposta.xml")
	public void deveRemovePropostaComSucesso(){
		propostaRepository.remove(100001L);
		Assert.assertNull(propostaRepository.findById(100001L));
	}
	
	@Test
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
	@UsingDataSet("db/proposta.xml")
	public void deveAlterarPropostaComSucesso(){
		Proposta proposta = propostaRepository.findById(100001L);
		proposta.setNome("Joaquim");
		proposta.setRenda(new BigDecimal(800.00));
		propostaRepository.save(proposta);
		Assert.assertEquals("Joaquim", propostaRepository.findById(100001L).getNome());
	}


}
