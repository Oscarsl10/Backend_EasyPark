package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.FacturaReserva;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFacturaReservaRepository extends CrudRepository<FacturaReserva, Long> {
    List<FacturaReserva> findByStatusTrue();
    List<FacturaReserva> findByVehiculoId(Long vehiculoId);
    FacturaReserva findByPagoReservaId(Long pagoReservaId);
}
