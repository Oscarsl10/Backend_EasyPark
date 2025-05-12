package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Soporte;

import java.util.List;

public interface ISoporteService {

    public List<Soporte> findAll();

    public Soporte findById(Long id);

    public Soporte save(Soporte soporte);

    public void delete(Long id);
}
