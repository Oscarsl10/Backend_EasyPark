package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tarifa;
import com.corhuila.backend_EasyPark.models.repository.ITarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TarifaServiceImpl implements ITarifaService{

    @Autowired
    private ITarifaRepository tarifaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Tarifa> findAll(){
        return tarifaRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Tarifa findById(Long id){
        Tarifa tarifa = tarifaRepository.findById(id).orElse(null);
        return (tarifa != null && Boolean.TRUE.equals(tarifa.getStatus())) ? tarifa : null;
    }

    @Override
    @Transactional
    public Tarifa save(Tarifa tarifa){
        return tarifaRepository.save(tarifa);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Tarifa tarifa = tarifaRepository.findById(id).orElse(null);
        if (tarifa != null) {
            tarifa.setStatus(false);
            tarifa.setDeleted_At(new Date());
            tarifaRepository.save(tarifa);
        }
    }
}
