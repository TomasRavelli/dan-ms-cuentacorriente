package dan.tp2021.cuentacorriente.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dan.tp2021.cuentacorriente.domain.Pago;

@Repository
public interface PagoRepositoryH2 extends JpaRepository<Pago, Integer>{

	@Query("select p from Pago p where p.cliente.id = :idCliente")
	List<Pago> findByIdCliente(@Param("idCliente") Integer idCliente);

	@Query("select p from Pago p where p.cliente.cuit = :cuit")
	List<Pago> findByCuitCliente(@Param("cuit") String cuit);

	@Query("select p from Pago p where p.cliente.id = :idCliente or p.cliente.cuit = :cuitCliente")
	List<Pago> getPagosByCuitOrIdCliente(@Param("idCliente") Integer id, @Param("cuitCliente") String cuit);
	

}
