package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Pago;
import com.corhuila.backend_EasyPark.models.repository.IPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagoServiceImpl implements IPagoService{

    @Autowired
    private IPagoRepository pagoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Pago> findAll(){
        return (List<Pago>) pagoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Pago findById(Long id){
        return pagoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Pago save(Pago pago){
        return pagoRepository.save(pago);
    }

    @Override
    @Transactional
    public void delete(Long id){
        pagoRepository.deleteById(id);
    }
}
