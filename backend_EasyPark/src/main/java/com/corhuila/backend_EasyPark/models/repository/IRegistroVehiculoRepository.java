package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.RegistroVehiculo;
import org.springframework.data.repository.CrudRepository;

public interface IRegistroVehiculoRepository extends CrudRepository <RegistroVehiculo, Long> {
}
