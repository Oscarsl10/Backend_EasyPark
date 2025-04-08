package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tipo_vehiculo;

import java.util.List;

public interface ITipo_vehiculoService {

    public List<Tipo_vehiculo> findAll();

    public Tipo_vehiculo findById(Long id);

    public Tipo_vehiculo save(Tipo_vehiculo tipo_vehiculo);

    public void delete(Long id);
}
