package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Pago;
import com.corhuila.backend_EasyPark.models.entity.RegistroVehiculo;
import com.corhuila.backend_EasyPark.models.repository.IRegistroVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroVehiculoServiceImpl implements IRegistroVehiculoService {

    @Autowired
    private IRegistroVehiculoRepository registroVehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RegistroVehiculo> findAll(){
        return (List<RegistroVehiculo>) registroVehiculoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public RegistroVehiculo findById(Long id){
        return registroVehiculoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RegistroVehiculo save(RegistroVehiculo registroVehiculo){
        return registroVehiculoRepository.save(registroVehiculo);
    }

    @Override
    @Transactional
    public void delete(Long id){
        registroVehiculoRepository.deleteById(id);
    }
}
