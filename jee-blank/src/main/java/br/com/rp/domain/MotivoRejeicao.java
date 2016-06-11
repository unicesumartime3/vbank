package br.com.rp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "motivorejeicao")
public abstract class MotivoRejeicao extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Lob
	@Column(name = "ds_motivo")
	private String dsMotivo;

	public MotivoRejeicao() {

	}

	public String getDsMotivo() {
		return dsMotivo;
	}

	public void setDsMotivo(String dsMotivo) {
		this.dsMotivo = dsMotivo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
