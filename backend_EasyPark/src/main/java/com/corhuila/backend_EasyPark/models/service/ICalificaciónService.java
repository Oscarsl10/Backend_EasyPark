package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Calificacion;

import java.util.List;

public interface ICalificaciónService {

    public List<Calificacion> findAll();

    public Calificacion findById(Long id);

    public Calificacion save(Calificacion calificacion);

    public void delete(Long id);
}
