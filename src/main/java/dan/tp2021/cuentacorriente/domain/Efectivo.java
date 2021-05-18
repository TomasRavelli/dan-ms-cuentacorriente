package dan.tp2021.cuentacorriente.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("efectivo")
@Entity
@DiscriminatorValue("E")
public class Efectivo extends MedioPago {

	private Integer nroRecibo;

	public Integer getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(Integer nroRecibo) {
		this.nroRecibo = nroRecibo;
	}
}
