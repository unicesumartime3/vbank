package br.com.rp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Christian Marchiori
 * @email cmxk@live.com
 *
 * @author Flávia Ferreira
 * @email flaviahferreirah@gmail.com
 *
 * @author Júlio Serra
 * @email julioserraaraujo@gmail.com 
 * 
 * @author Rafael Suzin
 * @email rafaelsuzin1@gmail.com
 *
 */
@Entity
@Table(name = "parametros")
public class Parametros extends BaseEntity implements Serializable {
	
	@Column(name = "intervalo_integracao_bacen", nullable = false)
	private Long intervaloIntegracaoBancoCentral;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hr_integracao_eua", nullable = false)
	private Date horarioIntegracaoEUA;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hr_inicio_transacao", nullable = false)
	private Date horaInicioTransacoes;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hr_fim_transacao", nullable = false)
	private Date horaFimTransacoes;
	
	public Parametros() {

	}
	
	public Long getIntervaloIntegracaoBancoCentral() {
		return intervaloIntegracaoBancoCentral;
	}

	public void setIntervaloIntegracaoBancoCentral(Long intervaloIntegracaoBancoCentral) {
		this.intervaloIntegracaoBancoCentral = intervaloIntegracaoBancoCentral;
	}

	public Date getHorarioIntegracaoEUA() {
		return horarioIntegracaoEUA;
	}

	public void setHorarioIntegracaoEUA(Date horarioIntegracaoEUA) {
		this.horarioIntegracaoEUA = horarioIntegracaoEUA;
	}

	public Date getHoraInicioTransacoes() {
		return horaInicioTransacoes;
	}

	public void setHoraInicioTransacoes(Date horaInicioTransacoes) {
		this.horaInicioTransacoes = horaInicioTransacoes;
	}

	public Date getHoraFimTransacoes() {
		return horaFimTransacoes;
	}

	public void setHoraFimTransacoes(Date horaFimTransacoes) {
		this.horaFimTransacoes = horaFimTransacoes;
	}
}
