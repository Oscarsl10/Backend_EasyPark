package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.FacturaReserva;

import java.util.List;

public interface IFacturaReservaService {

    public List<FacturaReserva> findAll();

    public FacturaReserva findById(Long id);

    public FacturaReserva save(FacturaReserva facturaReserva);

    public void delete(Long id);

    public List<FacturaReserva> findByVehiculoId(Long id);

    public FacturaReserva findByPagoReservaId(Long pagoId);
}
