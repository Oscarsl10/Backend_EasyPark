package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Reporte;
import com.corhuila.backend_EasyPark.models.repository.IReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReporteServiceImpl implements IReporteService {

    @Autowired
    private IReporteRepository reporteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reporte> findAll(){
        return reporteRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Reporte findById(Long id){
        Reporte reporte = reporteRepository.findById(id).orElse(null);
        return (reporte != null && Boolean.TRUE.equals(reporte.getStatus())) ? reporte : null;
    }

    @Override
    @Transactional
    public Reporte save(Reporte reporte){
        return reporteRepository.save(reporte);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Reporte reporte = reporteRepository.findById(id).orElse(null);
        if (reporte != null) {
            reporte.setStatus(false);
            reporte.setDeleted_At(new Date());
            reporteRepository.save(reporte);
        }
    }
}
