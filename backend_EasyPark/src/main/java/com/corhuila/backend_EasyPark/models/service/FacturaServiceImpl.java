package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Factura;
import com.corhuila.backend_EasyPark.models.repository.IFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FacturaServiceImpl implements IFacturaService{

    @Autowired
    private IFacturaRepository facturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Factura> findAll(){
        return facturaRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findById(Long id){
        Factura factura = facturaRepository.findById(id).orElse(null);
        return (factura != null && Boolean.TRUE.equals(factura.getStatus())) ? factura : null;
    }

    @Override
    @Transactional
    public Factura save(Factura factura){
        return facturaRepository.save(factura);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Factura factura = facturaRepository.findById(id).orElse(null);
        if (factura != null) {
            factura.setStatus(false);
            factura.setDeleted_At(new Date());
            facturaRepository.save(factura);
        }
    }
}
