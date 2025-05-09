package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.PagoReserva;

import java.util.List;

public interface IPagoReservaService {

    public List<PagoReserva> findAll();

    public PagoReserva findById(Long id);

    public PagoReserva save(PagoReserva pagoReserva);

    public void delete(Long id);
}
