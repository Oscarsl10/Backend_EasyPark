package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Factura;
import com.corhuila.backend_EasyPark.models.entity.Pago;
import com.corhuila.backend_EasyPark.models.repository.IPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PagoServiceImpl implements IPagoService{

    @Autowired
    private IPagoRepository pagoRepository;
    @Autowired
    private IFacturaService facturaService;

    @Override
    @Transactional(readOnly = true)
    public List<Pago> findAll(){
        return pagoRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Pago findById(Long id){
        Pago pago = pagoRepository.findById(id).orElse(null);
        return (pago != null && Boolean.TRUE.equals(pago.getStatus())) ? pago : null;
    }

    private static int contadorFactura = 1;

    private int generarNumeroFactura() {
        return contadorFactura++;
    }

    @Override
    @Transactional
    public Pago save(Pago pago){
        Pago pagoGuardado = pagoRepository.save(pago);

        // Crear factura automáticamente después del pago
        Factura factura = new Factura();
        factura.setNumeroFactura(generarNumeroFactura());
        factura.setFechaEmision(new Date());
        factura.setPago(pagoGuardado);
        factura.setRegistroVehiculo(pagoGuardado.getRegistroVehiculo());

        facturaService.save(factura); // Guarda la factura

        return pagoGuardado;
    }

    @Override
    @Transactional
    public void delete(Long id){
        Pago pago = pagoRepository.findById(id).orElse(null);
        if (pago != null) {
            pago.setStatus(false);
            pago.setDeleted_At(new Date());
            pagoRepository.save(pago);
        }
    }
}
