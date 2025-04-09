package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Tipo_vehiculo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITipo_vehiculoRepository extends CrudRepository <Tipo_vehiculo, Long> {
    List<Tipo_vehiculo> findByStatusTrue();
}
