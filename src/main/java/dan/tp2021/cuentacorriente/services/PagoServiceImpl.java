package dan.tp2021.cuentacorriente.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dan.tp2021.cuentacorriente.domain.Pago;
import frsf.isi.dan.InMemoryRepository;

public class PagoServiceImpl implements PagoService {

	@Autowired
	InMemoryRepository<Pago> inMemoryRepository;

	@Override
	public ResponseEntity<Pago> savePago(Pago p) {

		if(p.getId()!=null) {
			//Si quiere actualizar un pago, primero me fijo si dicho pago existe mediante su id.
			if(inMemoryRepository.existsById(p.getId())) {
				return ResponseEntity.ok(inMemoryRepository.save(p));
			}else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.ok(inMemoryRepository.save(p));

	}

	@Override
	public ResponseEntity<Pago> deletePagoById(Integer idPago) {

		try {
			inMemoryRepository.deleteById(idPago);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception E) {
			return ResponseEntity.notFound().build();
		}

	}

	@Override
	public ResponseEntity<Pago> getPagoById(Integer idPago) {

		return ResponseEntity.of(inMemoryRepository.findById(idPago));
	}

	@Override
	public ResponseEntity<List<Pago>> getListaPagos() {
		List<Pago> resultado = new ArrayList<>();
		try {
			inMemoryRepository.findAll().forEach(p -> resultado.add(p));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(resultado);
	}
	
	@Override
	public ResponseEntity<List<Pago>> getListaPagosByIdCliente(Integer idCliente){
		List<Pago> resultado = new ArrayList<>();
		try{
			inMemoryRepository.findAll().forEach(p -> {if(p.getCliente().getId().equals(idCliente)) resultado.add(p);});
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(resultado);
	}
	
	@Override
	public ResponseEntity<List<Pago>> getListaPagosByCuitCliente(String cuit){
		List<Pago> resultado = new ArrayList<>();
		
		try{
			inMemoryRepository.findAll().forEach(p -> {if(p.getCliente().getCuit().equals(cuit)) resultado.add(p);});
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(resultado);
	}

}
