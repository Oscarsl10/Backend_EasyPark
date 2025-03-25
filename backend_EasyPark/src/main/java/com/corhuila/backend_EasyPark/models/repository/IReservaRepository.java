package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Reserva;
import org.springframework.data.repository.CrudRepository;

public interface IReservaRepository extends CrudRepository<Reserva, Long> {
}
