package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tipo_vehiculo;
import com.corhuila.backend_EasyPark.models.repository.ITipo_vehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class Tipo_vehiculoServiceImpl implements ITipo_vehiculoService{

    @Autowired
    private ITipo_vehiculoRepository tipo_vehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Tipo_vehiculo> findAll(){
        return tipo_vehiculoRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Tipo_vehiculo findById(Long id){
        Tipo_vehiculo tipoVehiculo = tipo_vehiculoRepository.findById(id).orElse(null);
        return (tipoVehiculo != null && Boolean.TRUE.equals(tipoVehiculo.getStatus())) ? tipoVehiculo : null;
    }

    @Override
    @Transactional
    public Tipo_vehiculo save(Tipo_vehiculo tipo_vehiculo){
        return tipo_vehiculoRepository.save(tipo_vehiculo);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Tipo_vehiculo tipoVehiculo = tipo_vehiculoRepository.findById(id).orElse(null);
        if (tipoVehiculo != null) {
            tipoVehiculo.setStatus(false);
            tipoVehiculo.setDeleted_At(new Date());
            tipo_vehiculoRepository.save(tipoVehiculo);
        }
    }
}
