package dan.tp2021.cuentacorriente.domain;

import java.time.Instant;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("cheque")
@Entity
@DiscriminatorValue("C")
public class Cheque extends MedioPago {

	private Integer nroCheque;
	private Instant fechaCobro;
	private String banco;
	
	public Integer getNroCheque() {
		return nroCheque;
	}
	public void setNroCheque(Integer nroCheque) {
		this.nroCheque = nroCheque;
	}
	public Instant getFechaCobro() {
		return fechaCobro;
	}
	public void setFechaCobro(Instant fechaCobro) {
		this.fechaCobro = fechaCobro;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	
}
