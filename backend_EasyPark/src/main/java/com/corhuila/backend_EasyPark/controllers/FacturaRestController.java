package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Factura;
import com.corhuila.backend_EasyPark.models.service.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class FacturaRestController {

    @Autowired
    private IFacturaService facturaService;

    @GetMapping("/factura")
    public List<Factura> index(){
        return facturaService.findAll();
    }

    @GetMapping("/factura/{id}")
    public Factura show(@PathVariable Long id){
        return facturaService.findById(id);
    }

    @GetMapping("/facturav/{id}")
    public List<Factura> getByVehiculoId(@PathVariable Long id) {
        return facturaService.findByRegistroVehiculoId(id);
    }

    @GetMapping("/factura/pago/{pagoId}")
    public ResponseEntity<Factura> obtenerFacturaPorPago(@PathVariable Long pagoId) {
        Factura factura = facturaService.findByPagoId(pagoId);
        if (factura != null) {
            return ResponseEntity.ok(factura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/factura")
    @ResponseStatus(HttpStatus.CREATED)
    public Factura create(@RequestBody Factura factura){
        return facturaService.save(factura);
    }

    @PutMapping("/factura/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Factura update(@RequestBody Factura factura, @PathVariable Long id){
        Factura facturaActual = facturaService.findById(id);
        facturaActual.setNumeroFactura(factura.getNumeroFactura());
        facturaActual.setRegistroVehiculo(factura.getRegistroVehiculo());
        return facturaService.save(facturaActual);
    }

    @DeleteMapping("/factura/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        facturaService.delete(id);
    }
}
