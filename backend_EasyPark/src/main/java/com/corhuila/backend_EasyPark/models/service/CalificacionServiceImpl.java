package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Calificacion;
import com.corhuila.backend_EasyPark.models.repository.ICalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CalificacionServiceImpl implements ICalificaciónService{

    @Autowired
    private ICalificacionRepository calificacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Calificacion> findAll(){
        return calificacionRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Calificacion findById(Long id){
        Calificacion calificacion = calificacionRepository.findById(id).orElse(null);
        return (calificacion != null && Boolean.TRUE.equals(calificacion.getStatus())) ? calificacion : null;
    }

    @Override
    @Transactional
    public Calificacion save(Calificacion calificacion){
        return calificacionRepository.save(calificacion);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Calificacion calificacion = calificacionRepository.findById(id).orElse(null);
        if (calificacion != null) {
            calificacion.setStatus(false);
            calificacion.setDeleted_At(new Date());
            calificacionRepository.save(calificacion);
        }
    }
}
