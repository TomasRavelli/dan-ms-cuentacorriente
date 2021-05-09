package dan.tp2021.cuentacorriente.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dan.tp2021.cuentacorriente.domain.Pago;
import dan.tp2021.cuentacorriente.services.PagoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/pago")
@Api(value = "PagoRest", description = "Permite gestionar los pagos de los pedidos.")
public class PagoRest {

	public static List<Pago> listaPagos;

	@Autowired
	PagoService pagoServiceImpl;

	@PostMapping()
	@ApiOperation(value = "Crear un nuevo pago")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pago creado correctamente"),
			@ApiResponse(code = 401, message = "Operacion No autorizada"),
			@ApiResponse(code = 403, message = "Operacion Prohibida") })
	public ResponseEntity<Pago> crearNuevoPago(@RequestBody Pago nuevoPago) {
		
		//En nuevoPago viene un atributo "type" que utiliza Jackson para poder instancia la clase Abstacta MedioPago
		if (nuevoPago != null && nuevoPago.getCliente() != null && nuevoPago.getMedio() != null) {
			
			try {
				return pagoServiceImpl.savePago(nuevoPago);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar un pago por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pago eliminado correctamente"),
			@ApiResponse(code = 401, message = "Operacion No autorizada"),
			@ApiResponse(code = 403, message = "Operacion Prohibida"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Pago> deletePagoById(@PathVariable Integer id) {
		try {
			return pagoServiceImpl.deletePagoById(id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Actualizar un pago.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pago actualizado correctamente"),
			@ApiResponse(code = 401, message = "Operacion No autorizada"),
			@ApiResponse(code = 403, message = "Operacion Prohibida"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Pago> updatePago(@RequestBody Pago nuevoPago) {
		if (nuevoPago.getId() != null) {
			try {
				return pagoServiceImpl.savePago(nuevoPago);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Obtener un pago por su id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pago obtenido correctamente"),
			@ApiResponse(code = 401, message = "Operacion No autorizada"),
			@ApiResponse(code = 403, message = "Operacion Prohibida"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<Pago> getPagoById(@PathVariable Integer id) {
		try {
			return pagoServiceImpl.getPagoById(id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping()
	@ApiOperation(value = "Obtener pagos segun diferentes criterios: rango de fechas, id de cliente, cuit de cliente")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pagos obtenidos correctamente"),
			@ApiResponse(code = 401, message = "Operacion No autorizada"),
			@ApiResponse(code = 403, message = "Operacion Prohibida"),
			@ApiResponse(code = 404, message = "El ID no existe") })
	public ResponseEntity<List<Pago>> getListaPagos(@RequestParam(required = false, defaultValue = "0", name = "idCliente") Integer id,
													@RequestParam(required = false, defaultValue = "", name = "cuitCliente") String cuit,
													@RequestParam(required = false, defaultValue = "", name = "fechaDesde") String fechaDesde,
													@RequestParam(required = false, defaultValue = "", name = "fechaHasta") String fechaHasta) {

		//TODO ver como implementar, si es necesario, el filtrado por fechas.
		List<Pago> resultado = new ArrayList<>();
		try {
			ResponseEntity<List<Pago>> lista = pagoServiceImpl.getListaPagos();
			if(lista.getStatusCode().equals(HttpStatus.OK)) {
			if (id > 0 && !cuit.isBlank()){
				
					resultado =	lista.getBody().stream()
								.filter(pago -> pago.getCliente().getId().equals(id) || pago.getCliente().getCuit().equals(cuit))
								.collect(Collectors.toList());
					return ResponseEntity.ok(resultado);
				
			}
			else {
				if(id>0) {
					return pagoServiceImpl.getListaPagosByIdCliente(id);
				}
				else {
					if(!cuit.isBlank()) {
						return pagoServiceImpl.getListaPagosByCuitCliente(cuit);
					}
					else {
						return lista;
					}
				}
			}
			}
			else {
				return ResponseEntity.status(lista.getStatusCode()).build();
			}
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
}
