package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.PagoReserva;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPagoReservaRepository extends CrudRepository<PagoReserva, Long> {
    List<PagoReserva> findByStatusTrue();
}
