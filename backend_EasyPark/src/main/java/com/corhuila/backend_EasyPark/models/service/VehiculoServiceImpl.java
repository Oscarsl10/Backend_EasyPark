package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Vehiculo;
import com.corhuila.backend_EasyPark.models.repository.IVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoServiceImpl implements IVehiculoService{

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> findAll(){
        return (List<Vehiculo>) vehiculoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehiculo findById(Long id){
        return vehiculoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Vehiculo save(Vehiculo vehiculo){
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    @Transactional
    public void delete(Long id){
        vehiculoRepository.deleteById(id);
    }
}
