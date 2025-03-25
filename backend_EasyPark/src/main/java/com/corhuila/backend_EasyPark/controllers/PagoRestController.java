package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Pago;
import com.corhuila.backend_EasyPark.models.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class PagoRestController {

    @Autowired
    private IPagoService pagoService;

    @GetMapping("/pago")
    public List<Pago> index(){
        return pagoService.findAll();
    }

    @GetMapping("/pago/{id}")
    public Pago show(@PathVariable Long id){
        return pagoService.findById(id);
    }

    @PostMapping("/pago")
    @ResponseStatus(HttpStatus.CREATED)
    public Pago create(@RequestBody Pago pago){
        return pagoService.save(pago);
    }

    @PutMapping("/pago/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Pago update(@RequestBody Pago pago, @PathVariable Long id){
        Pago pagoActual = pagoService.findById(id);
        pagoActual.setEntrada(pago.getEntrada());
        pagoActual.setSalida(pago.getSalida());
        pagoActual.setVehiculo(pago.getVehiculo());
        pagoActual.setTarifa(pago.getTarifa());
        return pagoService.save(pagoActual);
    }

    @DeleteMapping("/pago/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        pagoService.delete(id);
    }
}
