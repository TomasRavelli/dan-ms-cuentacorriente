package dan.tp2021.cuentacorriente.services;

import java.util.List;

import dan.tp2021.cuentacorriente.domain.Pago;
import dan.tp2021.cuentacorriente.exceptions.pago.PagoException;

public interface PagoService {
	Pago savePago(Pago p) throws PagoException;
	Pago deletePagoById(Integer idPago) throws PagoException;
	Pago getPagoById(Integer idPago) throws PagoException;
	List<Pago> getListaPagos() throws PagoException;
	List<Pago> getListaPagosByIdCliente(Integer idCliente) throws PagoException;
	List<Pago> getListaPagosByCuitCliente(String cuitCliente) throws PagoException;
	List<Pago> getPagosByParams(Integer idPago, String cuitCliente) throws PagoException;
}
