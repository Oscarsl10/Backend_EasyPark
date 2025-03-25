package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Factura;
import com.corhuila.backend_EasyPark.models.repository.IFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacturaServiceImpl implements IFacturaService{

    @Autowired
    private IFacturaRepository facturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Factura> findAll(){
        return (List<Factura>) facturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findById(Long id){
        return facturaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Factura save(Factura factura){
        return facturaRepository.save(factura);
    }

    @Override
    @Transactional
    public void delete(Long id){
        facturaRepository.deleteById(id);
    }
}
