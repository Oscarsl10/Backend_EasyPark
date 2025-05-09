package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Factura;
import com.corhuila.backend_EasyPark.models.entity.FacturaReserva;
import com.corhuila.backend_EasyPark.models.service.IFacturaReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class FacturaReservaRestController {

    @Autowired
    private IFacturaReservaService facturaReservaService;

    @GetMapping("/factura-reserva")
    public List<FacturaReserva> index(){
        return facturaReservaService.findAll();
    }

    @GetMapping("/factura-reserva/{id}")
    public FacturaReserva show(@PathVariable Long id){
        return facturaReservaService.findById(id);
    }

    @GetMapping("/factura-reserva/vehiculo/{id}")
    public List<FacturaReserva> getByVehiculoId(@PathVariable Long id) {
        return facturaReservaService.findByVehiculoId(id);
    }

    @GetMapping("/factura-reserva/pago/{pagoId}")
    public ResponseEntity<FacturaReserva> obtenerFacturaPorPago(@PathVariable Long pagoId) {
        FacturaReserva facturaReserva = facturaReservaService.findByPagoReservaId(pagoId);
        if (facturaReserva != null) {
            return ResponseEntity.ok(facturaReserva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/factura-reserva")
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaReserva create(@RequestBody FacturaReserva facturaReserva){
        return facturaReservaService.save(facturaReserva);
    }

    @PutMapping("/factura-reserva/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaReserva update(@RequestBody FacturaReserva facturaReserva, @PathVariable Long id){
        FacturaReserva facturaReservaActual = facturaReservaService.findById(id);
        facturaReservaActual.setNumeroFactura(facturaReserva.getNumeroFactura());
        facturaReservaActual.setVehiculo(facturaReserva.getVehiculo());
        return facturaReservaService.save(facturaReservaActual);
    }

    @DeleteMapping("/factura-reserva/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        facturaReservaService.delete(id);
    }
}
