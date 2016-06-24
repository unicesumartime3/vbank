package br.com.rp.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.Agendamento;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.Conta;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class AgendamentoRepositoryTest extends AbstractTest {
	
	@EJB
	private AgendamentoRepository agendamentoRepository;

	@EJB
	private ClienteRepository clienteRepository;

	@EJB
	private ContaRepository contaRepository;

	@Test
	public void deveInserirAgendamentoComSucesso() throws ParseException {
		Conta conta = new Conta();
		conta.setNrConta("1234");
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(false);
		contaRepository.save(conta);

		Cliente cliente = new Cliente();
		cliente.setNome("Rafael Suzin");
		cliente.setCpf("08564856652");
		cliente.setVlRenda(new BigDecimal(8006.00));
		cliente.setConta(conta);
		cliente.setEmail("flavia@gmail.com");
		clienteRepository.save(cliente);

		Agendamento agendamento = new Agendamento();
		agendamento.setDataAgendamento(new SimpleDateFormat("dd-mm-yyyy").parse("24-05-2016"));
		agendamento.setCliente(cliente);
		agendamentoRepository.save(agendamento);
		Assert.assertNotNull(agendamento.getId());
	}

	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveRemoverAgendamentoComSucesso() {
		agendamentoRepository.remove(100001L);
		Assert.assertNull(agendamentoRepository.findById(100001L));
	}

	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveAlterarAgendamentoComSucesso() throws ParseException {
		Agendamento agendamento = agendamentoRepository.findById(100002L);
		agendamento.setDataAgendamento(new SimpleDateFormat("dd-mm-yyyy").parse("24-05-2016"));
		agendamentoRepository.save(agendamento);
		Assert.assertEquals(new SimpleDateFormat("dd-mm-yyyy").parse("24-05-2016"),
				agendamentoRepository.findById(100002L).getDataAgendamento());
	}

	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, agendamentoRepository.getAll().size());
	}

}
