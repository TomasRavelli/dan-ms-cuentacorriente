package dan.tp2021.cuentacorriente.domain;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Pago {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="ID_PAGO")
	private Integer id;
	
	private Instant fechaPago;
	
	@ManyToOne(cascade = CascadeType.PERSIST) //Siempre que se cree un nuevo pago, se va a crear un nuevo medio de pago.
//	@JoinColumn(name = "ID_MEDIOPAGO")
	private MedioPago medio;
	
	@ManyToOne
//	@JoinColumn(name = "ID_CLIENTE")
	private Cliente cliente;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Instant getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Instant fechaPago) {
		this.fechaPago = fechaPago;
	}
	public MedioPago getMedio() {
		return medio;
	}
	public void setMedio(MedioPago medio) {
		this.medio = medio;
	}
	
	
}
