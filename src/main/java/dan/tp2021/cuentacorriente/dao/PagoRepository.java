package dan.tp2021.cuentacorriente.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dan.tp2021.cuentacorriente.domain.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer>{

	List<Pago> findByClienteId(Integer idCliente);

	List<Pago> findByClienteCuit(String cuitCliente);

	List<Pago> findByClienteIdOrClienteCuit(Integer idCliente, String cuitCliente);

}
