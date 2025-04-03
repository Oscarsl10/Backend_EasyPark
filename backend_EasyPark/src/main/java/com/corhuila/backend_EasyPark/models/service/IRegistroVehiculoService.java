package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.RegistroVehiculo;

import java.util.List;

public interface IRegistroVehiculoService {

    public List<RegistroVehiculo> findAll();

    public RegistroVehiculo findById(Long id);

    public RegistroVehiculo save(RegistroVehiculo registroVehiculo);

    public void delete(Long id);
}
