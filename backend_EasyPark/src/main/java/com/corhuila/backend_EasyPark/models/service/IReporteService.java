package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Reporte;

import java.util.List;

public interface IReporteService {

    public List<Reporte> findAll();

    public Reporte findById(Long id);

    public Reporte save(Reporte reporte);

    public void delete(Long id);
}
