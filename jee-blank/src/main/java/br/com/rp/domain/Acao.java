package br.com.rp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Acao
 * 
 * Classe que identifica as ações do funcionário.
 * 
 * @author Rafael Suzin
 * @email rafaelsuzin1@gmail.com
 *
 */
@Entity
@Table(name = "acao")
public class Acao extends BaseEntity implements Serializable{
	
	/*
	 * Descrição da ação. Nesse atributo será informado o nome de cada 
	 * método para identificação da ação.
	 */
	@Column(name = "ds_acao", length = 60, nullable = false)
	private String descricaoAcao;
	
	public Acao() {

	}

	public String getDsAcao() {
		return descricaoAcao;
	}

	public void setDsAcao(String descricaoAcao) {
		this.descricaoAcao = descricaoAcao;
	}
	
}
