package br.com.rp.repository.service;

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
import br.com.rp.services.AgendamentoService;
import br.com.rp.services.ClienteService;
import br.com.rp.services.ContaService;

@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY)
public class AgendamentoServiceTest extends AbstractTest {

	@EJB
	private AgendamentoService agendamentoService;

	@EJB
	private ClienteService clienteService;

	@EJB
	private ContaService contaService;

	@Test
	public void deveInserirAgendamentoComSucesso() throws ParseException {
		Conta conta = new Conta();
		conta.setNrConta("1234");
		conta.setIsContaCorrente(true);
		conta.setIsContaPoupanca(false);
		contaService.save(conta);

		Cliente cliente = new Cliente();
		cliente.setNome("Rafael Suzin");
		cliente.setCpf("08564856652");
		cliente.setVlRenda(new BigDecimal(8006.00));
		cliente.setConta(conta);
		cliente.setEmail("flavia@gmail.com");
		clienteService.save(cliente);

		Agendamento agendamento = new Agendamento();
		agendamento.setDataAgendamento(new SimpleDateFormat("dd-mm-yyyy").parse("24-05-2016"));
		agendamento.setCliente(cliente);
		agendamentoService.save(agendamento);
		Assert.assertNotNull(agendamento.getId());
	}

	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveRemoverAgendamentoComSucesso() {
		agendamentoService.remove(100001L);
		Assert.assertNull(agendamentoService.findById(100001L));
	}

	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveAlterarAgendamentoComSucesso() throws ParseException {
		Agendamento agendamento = agendamentoService.findById(100002L);
		agendamento.setDataAgendamento(new SimpleDateFormat("dd-mm-yyyy").parse("24-05-2016"));
		agendamentoService.save(agendamento);
		Assert.assertEquals(new SimpleDateFormat("dd-mm-yyyy").parse("24-05-2016"),
				agendamentoService.findById(100002L).getDataAgendamento());
	}

	@Test
	@UsingDataSet("db/agendamento.xml")
	public void deveRetornarDoisRegistros() {
		Assert.assertEquals(2, agendamentoService.getAll().size());
	}

}
