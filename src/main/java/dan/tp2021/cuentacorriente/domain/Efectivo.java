package dan.tp2021.cuentacorriente.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("efectivo")
public class Efectivo extends MedioPago {

	private Integer nroRecibo;

	public Integer getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(Integer nroRecibo) {
		this.nroRecibo = nroRecibo;
	}
}
