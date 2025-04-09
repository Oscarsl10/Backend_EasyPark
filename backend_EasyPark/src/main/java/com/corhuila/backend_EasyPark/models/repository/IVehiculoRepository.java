package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Vehiculo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IVehiculoRepository extends CrudRepository <Vehiculo, Long> {
    List<Vehiculo> findByStatusTrue();
}
