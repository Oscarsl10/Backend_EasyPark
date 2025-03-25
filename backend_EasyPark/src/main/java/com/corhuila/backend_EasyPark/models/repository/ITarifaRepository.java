package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Tarifa;
import org.springframework.data.repository.CrudRepository;

public interface ITarifaRepository extends CrudRepository <Tarifa, Long> {
}
