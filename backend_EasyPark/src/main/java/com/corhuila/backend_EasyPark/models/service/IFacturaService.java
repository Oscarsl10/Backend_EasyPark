package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Factura;

import java.util.List;

public interface IFacturaService {

    public List<Factura> findAll();

    public Factura findById(Long id);

    public Factura save(Factura factura);

    public void delete(Long id);
}
