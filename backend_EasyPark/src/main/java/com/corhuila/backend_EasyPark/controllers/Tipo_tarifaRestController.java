package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Tipo_tarifa;
import com.corhuila.backend_EasyPark.models.service.ITipo_tarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class Tipo_tarifaRestController {

    @Autowired
    private ITipo_tarifaService tipo_tarifaService;

    @GetMapping("/tipo_tarifa")
    public List<Tipo_tarifa> index(){
        return tipo_tarifaService.findAll();
    }

    @GetMapping("/tipo_tarifa/{id}")
    public Tipo_tarifa show(@PathVariable Long id){
        return tipo_tarifaService.findById(id);
    }

    @PostMapping("/tipo_tarifa")
    @ResponseStatus(HttpStatus.CREATED)
    public Tipo_tarifa create(@RequestBody Tipo_tarifa tipo_tarifa){
        return tipo_tarifaService.save(tipo_tarifa);
    }

    @PutMapping("/tipo_tarifa/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Tipo_tarifa update(@RequestBody Tipo_tarifa tipo_tarifa, @PathVariable Long id){
        Tipo_tarifa tipo_tarifaActual = tipo_tarifaService.findById(id);
        tipo_tarifaActual.setTipo_tarifa(tipo_tarifa.getTipo_tarifa());
        tipo_tarifaActual.setStatus(tipo_tarifa.getStatus());
        return tipo_tarifaService.save(tipo_tarifaActual);
    }

    @DeleteMapping("/tipo_tarifa/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        tipo_tarifaService.delete(id);
    }
}
