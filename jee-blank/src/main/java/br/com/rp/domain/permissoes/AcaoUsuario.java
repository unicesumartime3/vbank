package br.com.rp.domain.permissoes;
 
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.rp.domain.BaseEntity;
import br.com.rp.domain.funcionario.Funcionario;
import br.com.rp.domain.usuario.Usuario;
import br.com.rp.domain.usuario.UsuarioFuncionario;

/**
 * AcaoFuncionario
 * 
 * Classe associativa de Acao e Funcionario, esta identifica
 *  as ações que cada funcionário tem acesso dentro do sistema.
 * 
 * @author Rafael Suzin
 * @email rafaelsuzin1@gmail.com
 *
 */
@Entity
@Table(name = "acaofuncionario")
public class AcaoUsuario extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_acao", referencedColumnName = "id", nullable = false)
	private Acao acao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_funcionario", referencedColumnName = "id", nullable = false)
	private UsuarioFuncionario usuarioFuncionario;
	
	public AcaoUsuario() {

	}

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	public UsuarioFuncionario getUsuarioFuncionario() {
		return usuarioFuncionario;
	}

	public void setUsuarioFuncionario(UsuarioFuncionario usuarioFuncionario) {
		this.usuarioFuncionario = usuarioFuncionario;
	}

}
