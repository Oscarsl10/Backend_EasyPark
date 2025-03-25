package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Pago;

import java.util.List;

public interface IPagoService {

    public List<Pago> findAll();

    public Pago findById(Long id);

    public Pago save(Pago pago);

    public void delete(Long id);
}
