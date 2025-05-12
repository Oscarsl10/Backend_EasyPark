package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Calificacion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICalificacionRepository extends CrudRepository<Calificacion, Long> {

    List<Calificacion> findByStatusTrue();
}
