package br.com.rp.domain.funcionario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.rp.domain.BaseEntity;

/**
 * Cargo
 * 
 * Classe que identifica o cargo dos funcionários.
 * 
 * @author Rafael Suzin
 * @email rafaelsuzin1@gmail.com
 *
 */

@Entity
@Table(name = "cargo")
public class Cargo extends BaseEntity implements Serializable {

	@Column(name = "descricao_cargo", length = 60, nullable = false)
	private String descricaoCargo;
	
	public Cargo() {

	}

	public String getDescricaoCargo() {
		return descricaoCargo;
	}

	public void setDescricaoCargo(String descricaoCargo) {
		this.descricaoCargo = descricaoCargo;
	}
	
}