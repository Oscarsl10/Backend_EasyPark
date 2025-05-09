package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.FacturaReserva;
import com.corhuila.backend_EasyPark.models.repository.IFacturaReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FacturaReservaServiceImpl implements IFacturaReservaService {

    @Autowired
    private IFacturaReservaRepository facturaReservaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FacturaReserva> findAll(){
        return facturaReservaRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public FacturaReserva findById(Long id){
        FacturaReserva facturaReserva = facturaReservaRepository.findById(id).orElse(null);
        return (facturaReserva != null && Boolean.TRUE.equals(facturaReserva.getStatus())) ? facturaReserva : null;
    }

    @Override
    @Transactional
    public FacturaReserva save(FacturaReserva facturaReserva){
        return facturaReservaRepository.save(facturaReserva);
    }

    @Override
    @Transactional
    public void delete(Long id){
        FacturaReserva facturaReserva = facturaReservaRepository.findById(id).orElse(null);
        if (facturaReserva != null) {
            facturaReserva.setStatus(false);
            facturaReserva.setDeleted_At(new Date());
            facturaReservaRepository.save(facturaReserva);
        }
    }

    @Override
    public List<FacturaReserva> findByVehiculoId(Long id) {
        return facturaReservaRepository.findByVehiculoId(id);
    }

    @Override
    public FacturaReserva findByPagoReservaId(Long pagoId) {
        return facturaReservaRepository.findByPagoReservaId(pagoId);
    }
}
