package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Soporte;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISoporteRepository extends CrudRepository<Soporte, Long> {

    List<Soporte> findByStatusTrue();
}
