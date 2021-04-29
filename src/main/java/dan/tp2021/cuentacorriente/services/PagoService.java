package dan.tp2021.cuentacorriente.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dan.tp2021.cuentacorriente.domain.Pago;

@Service
public interface PagoService {
	ResponseEntity<Pago> savePago(Pago p);
	ResponseEntity<Pago> deletePagoById(Integer idPago);
	ResponseEntity<Pago> getPagoById(Integer idPago);
	ResponseEntity<List<Pago>> getListaPagos();
	ResponseEntity<List<Pago>> getListaPagosByIdCliente(Integer idCliente); 
	ResponseEntity<List<Pago>> getListaPagosByCuitCliente(String cuitCliente); 
}
