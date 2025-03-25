package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Reserva;

import java.util.List;

public interface IReservaService {

    public List<Reserva> findAll();

    public Reserva findById(Long id);

    public Reserva save(Reserva reserva);

    public void delete(Long id);
}
