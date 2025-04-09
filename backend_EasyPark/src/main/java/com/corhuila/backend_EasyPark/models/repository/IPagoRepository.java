package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Pago;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPagoRepository extends CrudRepository <Pago, Long> {
    List<Pago> findByStatusTrue();
}
