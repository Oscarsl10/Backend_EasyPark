package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.EspacioTotal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEspacioTotalRepository extends CrudRepository <EspacioTotal, Long> {

    List<EspacioTotal> findByStatusTrue();
}
