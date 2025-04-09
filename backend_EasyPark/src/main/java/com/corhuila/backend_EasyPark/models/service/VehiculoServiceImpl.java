package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Vehiculo;
import com.corhuila.backend_EasyPark.models.repository.IVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class VehiculoServiceImpl implements IVehiculoService{

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> findAll(){
        return vehiculoRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehiculo findById(Long id){
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);
        return (vehiculo != null && Boolean.TRUE.equals(vehiculo.getStatus())) ? vehiculo : null;
    }

    @Override
    @Transactional
    public Vehiculo save(Vehiculo vehiculo){
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);
        if (vehiculo != null) {
            vehiculo.setStatus(false);
            vehiculo.setDeleted_At(new Date());
            vehiculoRepository.save(vehiculo);
        }
    }
}
