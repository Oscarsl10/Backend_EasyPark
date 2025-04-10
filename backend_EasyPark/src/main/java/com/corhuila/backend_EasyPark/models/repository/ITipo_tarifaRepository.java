package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Tipo_tarifa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITipo_tarifaRepository extends CrudRepository<Tipo_tarifa, Long> {
    List<Tipo_tarifa> findByStatusTrue();
}
