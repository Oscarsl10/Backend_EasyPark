package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tipo_tarifa;
import com.corhuila.backend_EasyPark.models.repository.ITipo_tarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class Tipo_tarifaServiceImpl implements ITipo_tarifaService{

    @Autowired
    private ITipo_tarifaRepository tipo_tarifaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Tipo_tarifa> findAll(){
        return tipo_tarifaRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Tipo_tarifa findById(Long id){
        Tipo_tarifa tipo_tarifa = tipo_tarifaRepository.findById(id).orElse(null);
        return (tipo_tarifa != null && Boolean.TRUE.equals(tipo_tarifa.getStatus())) ? tipo_tarifa : null;
    }

    @Override
    @Transactional
    public Tipo_tarifa save(Tipo_tarifa tipo_tarifa){
        return tipo_tarifaRepository.save(tipo_tarifa);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Tipo_tarifa tipo_tarifa = tipo_tarifaRepository.findById(id).orElse(null);
        if (tipo_tarifa != null) {
            tipo_tarifa.setStatus(false);
            tipo_tarifa.setDeleted_At(new Date());
            tipo_tarifaRepository.save(tipo_tarifa);
        }
    }
}
