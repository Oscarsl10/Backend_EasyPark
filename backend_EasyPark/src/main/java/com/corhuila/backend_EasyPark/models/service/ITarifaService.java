package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tarifa;
import java.util.List;

public interface ITarifaService {

    public List<Tarifa> findAll();

    public Tarifa findById(Long id);

    public Tarifa save(Tarifa tarifa);

    public void delete(Long id);
}
