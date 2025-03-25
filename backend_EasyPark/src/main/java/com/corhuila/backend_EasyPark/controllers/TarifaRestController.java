package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Tarifa;
import com.corhuila.backend_EasyPark.models.service.ITarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class TarifaRestController {

    @Autowired
    private ITarifaService tarifaService;

    @GetMapping("/tarifa")
    public List<Tarifa> index(){
        return tarifaService.findAll();
    }

    @GetMapping("/tarifa/{id}")
    public Tarifa show(@PathVariable Long id){
        return tarifaService.findById(id);
    }

    @PostMapping("/tarifa")
    @ResponseStatus(HttpStatus.CREATED)
    public Tarifa create(@RequestBody Tarifa tarifa){
        return tarifaService.save(tarifa);
    }

    @PutMapping("/tarifa/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Tarifa update(@RequestBody Tarifa tarifa, @PathVariable Long id){
        Tarifa tarifaActual = tarifaService.findById(id);
        tarifaActual.setTipoVehiculo(tarifa.getTipoVehiculo());
        tarifaActual.setPrecio(tarifa.getPrecio());
        tarifaActual.setLapso(tarifa.getLapso());
        tarifaActual.setFecha(tarifa.getFecha());
        return tarifaService.save(tarifaActual);
    }

    @DeleteMapping("/tarifa/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        tarifaService.delete(id);
    }
}
