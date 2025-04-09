package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Factura;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFacturaRepository extends CrudRepository <Factura, Long> {
    List<Factura> findByStatusTrue();
}
