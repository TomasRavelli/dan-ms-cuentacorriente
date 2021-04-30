package dan.tp2021.cuentacorriente.dao;

import org.springframework.stereotype.Repository;

import dan.tp2021.cuentacorriente.domain.Pago;
import frsf.isi.dan.InMemoryRepository;

@Repository
public class PagoInMemoryRepository extends InMemoryRepository<Pago> {

	@Override
	public Integer getId(Pago entity) {
		
		return entity.getId();
	}

	@Override
	public void setId(Pago entity, Integer id) {
		entity.setId(id);
		
	}

}
