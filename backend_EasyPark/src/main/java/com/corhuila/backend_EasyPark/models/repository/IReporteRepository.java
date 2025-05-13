package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Reporte;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IReporteRepository extends CrudRepository<Reporte, Long> {

    List<Reporte> findByStatusTrue();
}
