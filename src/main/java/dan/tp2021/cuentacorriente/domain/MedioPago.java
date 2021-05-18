package dan.tp2021.cuentacorriente.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({

	@JsonSubTypes.Type(value = Efectivo.class, name = "efectivo"),
	@JsonSubTypes.Type(value = Transferencia.class, name = "transferencia"),
	@JsonSubTypes.Type(value = Cheque.class, name = "cheque")
})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("M")
public abstract class MedioPago {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_MEDIOPAGO")
	protected Integer id;
	protected String observacion;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	
}
