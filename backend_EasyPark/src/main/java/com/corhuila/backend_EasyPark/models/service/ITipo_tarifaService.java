package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tipo_tarifa;

import java.util.List;

public interface ITipo_tarifaService {

    public List<Tipo_tarifa> findAll();

    public Tipo_tarifa findById(Long id);

    public Tipo_tarifa save(Tipo_tarifa tipo_tarifa);

    public void delete(Long id);
}
