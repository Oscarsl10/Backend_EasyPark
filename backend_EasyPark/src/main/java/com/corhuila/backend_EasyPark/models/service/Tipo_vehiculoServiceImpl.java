package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tipo_vehiculo;
import com.corhuila.backend_EasyPark.models.repository.ITipo_vehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Tipo_vehiculoServiceImpl implements ITipo_vehiculoService{

    @Autowired
    private ITipo_vehiculoRepository tipo_vehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Tipo_vehiculo> findAll(){
        return (List<Tipo_vehiculo>) tipo_vehiculoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Tipo_vehiculo findById(Long id){
        return tipo_vehiculoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Tipo_vehiculo save(Tipo_vehiculo tipo_vehiculo){
        return tipo_vehiculoRepository.save(tipo_vehiculo);
    }

    @Override
    @Transactional
    public void delete(Long id){
        tipo_vehiculoRepository.deleteById(id);
    }
}
