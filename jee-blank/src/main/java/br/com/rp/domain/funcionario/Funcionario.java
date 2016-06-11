package br.com.rp.domain.funcionario;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.rp.domain.BaseEntity;

@Entity
@Table(name = "funcionario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tp_funcionario", discriminatorType = DiscriminatorType.STRING, length = 15)
public abstract class Funcionario extends BaseEntity implements Serializable{ 
	
	
	
	public Funcionario() {

	}
}
