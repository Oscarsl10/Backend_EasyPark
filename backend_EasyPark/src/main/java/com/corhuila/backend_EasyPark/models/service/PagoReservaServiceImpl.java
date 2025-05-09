package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.FacturaReserva;
import com.corhuila.backend_EasyPark.models.entity.PagoReserva;
import com.corhuila.backend_EasyPark.models.repository.IPagoReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PagoReservaServiceImpl implements IPagoReservaService {

    @Autowired
    private IPagoReservaRepository pagoReservaRepository;
    @Autowired
    private IFacturaReservaService facturaReservaService;

    @Override
    @Transactional(readOnly = true)
    public List<PagoReserva> findAll(){
        return pagoReservaRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public PagoReserva findById(Long id){
        PagoReserva pagoReserva = pagoReservaRepository.findById(id).orElse(null);
        return (pagoReserva != null && Boolean.TRUE.equals(pagoReserva.getStatus())) ? pagoReserva : null;
    }

    private static int contadorFactura = 1;

    private int generarNumeroFactura() {
        return contadorFactura++;
    }

    @Override
    @Transactional
    public PagoReserva save(PagoReserva pagoReserva){
        PagoReserva pagoGuardado = pagoReservaRepository.save(pagoReserva);

        // Crear factura automáticamente después del pago
        FacturaReserva factura = new FacturaReserva();
        factura.setNumeroFactura(generarNumeroFactura());
        factura.setFechaEmision(new Date());
        factura.setPagoReserva(pagoGuardado);
        factura.setVehiculo(pagoGuardado.getVehiculo());

        facturaReservaService.save(factura); // Guarda la factura

        return pagoGuardado;
    }

    @Override
    @Transactional
    public void delete(Long id){
        PagoReserva pagoReserva = pagoReservaRepository.findById(id).orElse(null);
        if (pagoReserva != null) {
            pagoReserva.setStatus(false);
            pagoReserva.setDeleted_At(new Date());
            pagoReservaRepository.save(pagoReserva);
        }
    }
}
