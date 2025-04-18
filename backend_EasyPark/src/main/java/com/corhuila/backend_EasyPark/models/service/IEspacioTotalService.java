package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.EspacioTotal;

import java.util.List;

public interface IEspacioTotalService {

    public List<EspacioTotal> findAll();

    public EspacioTotal findById(Long id);

    public EspacioTotal save(EspacioTotal espacio);

    public void delete(Long id);
}
