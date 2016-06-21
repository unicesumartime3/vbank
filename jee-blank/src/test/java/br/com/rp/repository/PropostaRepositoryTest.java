package br.com.rp.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;

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
	


}
