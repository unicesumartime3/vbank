package br.com.rp.repository;

import javax.ejb.EJB;

import org.junit.Test;

import br.com.rp.AbstractTest;
import br.com.rp.domain.UsuarioFuncionario;

public class UsuarioFuncionarioRepositoryTest extends AbstractTest {

	@EJB
	private UsuarioFuncionarioRepository usuarioFuncionarioRepository;

	@Test
	public void deveInserirCargoComSucesso() {
		UsuarioFuncionario usuarioFuncionario = new UsuarioFuncionario();

	}
}
