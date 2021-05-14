package dan.tp2021.cuentacorriente.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dan.tp2021.cuentacorriente.dao.PagoRepositoryH2;
import dan.tp2021.cuentacorriente.domain.Pago;
import dan.tp2021.cuentacorriente.exceptions.pago.PagoException;
import dan.tp2021.cuentacorriente.exceptions.pago.PagoNotFoundException;

@Service
public class PagoServiceImpl implements PagoService {

	private static final Logger logger = LoggerFactory.getLogger(PagoServiceImpl.class);

	@Autowired
//	PagoInMemoryRepository inMemoryRepository;
	PagoRepositoryH2 inMemoryRepository;

	@Override
	public Pago savePago(Pago p) throws PagoException {

		if (p.getId() != null) {
			// Si quiere actualizar un pago, primero me fijo si dicho pago existe mediante
			// su id.
			logger.debug("Id no nulo, quiere actualizarlo.");
			if (inMemoryRepository.existsById(p.getId())) {
				try {
					return inMemoryRepository.save(p);
				} catch (Exception e) {
					logger.error("Error al actualizar pago: " + e.getMessage());
					throw new PagoException("Error al guardar pago actualizado");
				}

			} else {
				logger.error("El pago que se quiere actualizar no existe.");

				throw new PagoNotFoundException("No se encontró el pago.");
			}
		} else {
			try {
				//TODO es posible que se pueda crear un cliente en este microservicio? o siempre lo tengo que gestionar con el microservicio de USUARIOS?
				p.setFechaPago(Instant.now());
				logger.debug("Entro a save pago, fechaPago= " + p.getFechaPago());
				return inMemoryRepository.save(p);
			} catch (Exception e) {
				logger.error("Error al guardar el pago: " + e.getMessage());
				throw new PagoException("Error al guardar el pago: " + e.getMessage());
			}
		}

	}

	@Override
	public Pago deletePagoById(Integer idPago) throws PagoException {

		try {
			Optional<Pago> p = inMemoryRepository.findById(idPago);
			if (p.isPresent()) {
				inMemoryRepository.deleteById(idPago);
				return p.get();
			}
		} catch (Exception e) {
			logger.error("Error al eliminar el pago: " + e.getMessage());
			throw new PagoException("Error al eliminar el pago: " + e.getMessage());
		}

		// Si llega acá quiere decir que no hubo error (excepción) pero no se encontró
		// el pago para eliminar.
		logger.error("No existe un pago con id: " + idPago);
		throw new PagoNotFoundException("");

	}

	@Override
	public Pago getPagoById(Integer idPago) throws PagoException {

		Optional<Pago> result = inMemoryRepository.findById(idPago);
		if (result.isPresent()) {
			return result.get();
		}
		logger.error("No existe el pago con id: " + idPago);
		throw new PagoNotFoundException("");
	}

	@Override
	public List<Pago> getListaPagos() throws PagoException {
		List<Pago> resultado = new ArrayList<>();
		try {
			inMemoryRepository.findAll().forEach(p -> resultado.add(p));
		} catch (Exception e) {
			logger.error("Error al obtener pagos. Mensaje error: " + e.getMessage());
			throw new PagoException("Error al obtener la lista de pagos: " + e.getMessage());
		}
		return resultado;
	}

	@Override
	public List<Pago> getListaPagosByIdCliente(Integer idCliente) throws PagoException {
		List<Pago> resultado = new ArrayList<>();
		try {
//			inMemoryRepository.findAll().forEach(p -> {if(p.getCliente().getId().equals(idCliente)) resultado.add(p);});
			resultado = inMemoryRepository.findByIdCliente(idCliente);
		} catch (Exception e) {
			logger.error("Error al obtener pagos por id clientes. Mensaje: " + e.getMessage());
			throw new PagoException(
					"Error al obtener la lista de pagos para el id de cliente " + idCliente + ": " + e.getMessage());
		}
		return resultado;
	}

	@Override
	public List<Pago> getListaPagosByCuitCliente(String cuit) throws PagoException {
		List<Pago> resultado = new ArrayList<>();
		try {
//			inMemoryRepository.findAll().forEach(p -> {if(p.getCliente().getCuit().equals(cuit)) resultado.add(p);});
			resultado = inMemoryRepository.findByCuitCliente(cuit);
		} catch (Exception e) {
			logger.error("Error al buscar pagos por cuit de cliente. Mensaje de error: " + e.getMessage());
			throw new PagoException(
					"Error al obtener la lista de pagos para el cuit de cliente " + cuit + ": " + e.getMessage());
		}
		return resultado;
	}

	@Override
	public List<Pago> getPagosByParams(Integer id, String cuit) throws PagoNotFoundException, PagoException {

		if (id > 0 && !cuit.isBlank()) {
			try {
				return inMemoryRepository.getPagosByCuitOrIdCliente(id, cuit);
			} catch (Exception e) {
				logger.error("Error al obtener pagos desde la BD. Mensaje: " + e.getMessage());
				throw new PagoException("Error al obtener pagos desde la BD");
			}

		}

		if (id > 0) {

			try {
				return this.getListaPagosByIdCliente(id);
			} catch (Exception e) {
				logger.error("Error al obtener pagos desde la BD. Mensaje: " + e.getMessage());
				throw new PagoException("Error al obtener pagos desde la BD");
			}
		}

		if (!cuit.isBlank()) {

			try {
				return this.getListaPagosByCuitCliente(cuit);
			} catch (Exception e) {
				logger.error("Error al obtener pagos desde la BD. Mensaje: " + e.getMessage());
				throw new PagoException("Error al obtener pagos desde la BD");
			}
			

		}

		try {
			return this.getListaPagos();
		} catch (Exception e) {
			logger.error("Error al obtener pagos desde la BD. Mensaje: " + e.getMessage());
			throw new PagoException("Error al obtener pagos desde la BD");
		}
		
	}

}
