package dan.tp2021.cuentacorriente.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({

	@JsonSubTypes.Type(value = Efectivo.class, name = "efectivo"),
	@JsonSubTypes.Type(value = Transferencia.class, name = "transferencia"),
	@JsonSubTypes.Type(value = Cheque.class, name = "cheque")
})
public abstract class MedioPago {

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
