package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Vehiculo;
import org.springframework.data.repository.CrudRepository;

public interface IVehiculoRepository extends CrudRepository <Vehiculo, Long> {
}
