package dan.tp2021.cuentacorriente.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dan.tp2021.cuentacorriente.dao.PagoInMemoryRepository;
import dan.tp2021.cuentacorriente.domain.Pago;
import dan.tp2021.cuentacorriente.exceptions.pago.PagoException;
import dan.tp2021.cuentacorriente.exceptions.pago.PagoNotFoundException;

@Service
public class PagoServiceImpl implements PagoService {

	@Autowired
	PagoInMemoryRepository inMemoryRepository;

	@Override
	public Pago savePago(Pago p) throws PagoException {

		if(p.getId()!=null) {
			//Si quiere actualizar un pago, primero me fijo si dicho pago existe mediante su id.
			if(inMemoryRepository.existsById(p.getId())) {
				return inMemoryRepository.save(p);
			}else {
				//El pago que se quiere actualizar no existe.
				throw new PagoNotFoundException("No se eonctró el pago.");
			}
		}

		try {
			return inMemoryRepository.save(p);
		} catch (Exception e) {
			throw new PagoException("Error al guardar el pago: " + e.getMessage());
		}

	}

	@Override
	public Pago deletePagoById(Integer idPago) throws PagoException {

		try {
			Optional<Pago> p = inMemoryRepository.findById(idPago);
			if(p.isPresent()) {
				inMemoryRepository.deleteById(idPago);
				return p.get();
			}
		} catch (Exception e) {
			throw new PagoException("Error al eliminar el pago: " + e.getMessage());
		}

		//Si llega acá quiere decir que no hubo error (excepción) pero no se encontró el pago para eliminar.
		throw new PagoNotFoundException("");

	}

	@Override
	public Pago getPagoById(Integer idPago) throws PagoException {

		Optional<Pago> result =  inMemoryRepository.findById(idPago);
		if(result.isPresent()) {
			return result.get();
		}

		throw new PagoNotFoundException("");
	}

	@Override
	public List<Pago> getListaPagos() throws PagoException {
		List<Pago> resultado = new ArrayList<>();
		try {
			inMemoryRepository.findAll().forEach(p -> resultado.add(p));
		} catch (Exception e) {
			throw new PagoException("Error al obtener la lista de pagos: " + e.getMessage());
		}
		return resultado;
	}
	
	@Override
	public List<Pago> getListaPagosByIdCliente(Integer idCliente) throws PagoException {
		List<Pago> resultado = new ArrayList<>();
		try{
			inMemoryRepository.findAll().forEach(p -> {if(p.getCliente().getId().equals(idCliente)) resultado.add(p);});
		}catch(Exception e) {
			throw new PagoException("Error al obtener la lista de pagos para el id de cliente " + idCliente + ": " + e.getMessage());
		}
		return resultado;
	}
	
	@Override
	public List<Pago> getListaPagosByCuitCliente(String cuit) throws PagoException {
		List<Pago> resultado = new ArrayList<>();
		
		try{
			inMemoryRepository.findAll().forEach(p -> {if(p.getCliente().getCuit().equals(cuit)) resultado.add(p);});
		}catch(Exception e) {
			throw new PagoException("Error al obtener la lista de pagos para el cuit de cliente " + cuit + ": " + e.getMessage());
		}
		return resultado;
	}

	@Override
	public List<Pago> getPagosByParams(Integer id, String cuit) throws PagoNotFoundException, PagoException {
		
		List<Pago> resultado = new ArrayList<>();
		if (id > 0 && !cuit.isBlank()){
			List<Pago> lista = this.getListaPagos();
			resultado =	lista.stream()
						.filter(pago -> pago.getCliente().getId().equals(id) || pago.getCliente().getCuit().equals(cuit))
						.collect(Collectors.toList());
			if(!resultado.isEmpty()) {
			
				return (resultado);
			}
			
			throw new PagoNotFoundException("No se encontraron pagos que cumplan con estos criterios.");
			
		}

		if(id > 0) {
			resultado = this.getListaPagosByIdCliente(id);
			if(!resultado.isEmpty()) {
				return (resultado);
			}
			throw new PagoNotFoundException("No se encontraron pagos que cumplan con estos criterios.");
		}

		if(!cuit.isBlank()) {
			resultado = this.getListaPagosByCuitCliente(cuit);
			
			if(!resultado.isEmpty()) {
				return (resultado);
			}			
			throw new PagoNotFoundException("No se encontraron pagos que cumplan con estos criterios.");
		}

		resultado = this.getListaPagos();
		
		if(!resultado.isEmpty()) {
			return resultado;
		}
		
		throw new PagoNotFoundException("No se encontraron pagos que cumplan con estos criterios.");

	}

}
