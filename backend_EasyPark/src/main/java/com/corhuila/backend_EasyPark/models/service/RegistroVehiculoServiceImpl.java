package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.RegistroVehiculo;
import com.corhuila.backend_EasyPark.models.repository.IRegistroVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RegistroVehiculoServiceImpl implements IRegistroVehiculoService {

    @Autowired
    private IRegistroVehiculoRepository registroVehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RegistroVehiculo> findAll(){
        return registroVehiculoRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public RegistroVehiculo findById(Long id){
        RegistroVehiculo registroVehiculo = registroVehiculoRepository.findById(id).orElse(null);
        return (registroVehiculo != null && Boolean.TRUE.equals(registroVehiculo.getStatus())) ? registroVehiculo : null;
    }

    @Override
    @Transactional
    public RegistroVehiculo save(RegistroVehiculo registroVehiculo){
        return registroVehiculoRepository.save(registroVehiculo);
    }

    @Override
    @Transactional
    public void delete(Long id){
        RegistroVehiculo registroVehiculo = registroVehiculoRepository.findById(id).orElse(null);
        if (registroVehiculo != null) {
            registroVehiculo.setStatus(false);
            registroVehiculo.setDeleted_At(new Date());
            registroVehiculoRepository.save(registroVehiculo);
        }
    }
}
