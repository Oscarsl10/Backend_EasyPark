package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Pago;
import org.springframework.data.repository.CrudRepository;

public interface IPagoRepository extends CrudRepository <Pago, Long> {
}
