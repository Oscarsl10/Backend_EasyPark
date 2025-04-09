package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Reserva;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IReservaRepository extends CrudRepository<Reserva, Long> {
    List<Reserva> findByStatusTrue();
}
