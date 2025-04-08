package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Tipo_vehiculo;
import com.corhuila.backend_EasyPark.models.service.ITipo_vehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class Tipo_vehiculoRestController {

    @Autowired
    private ITipo_vehiculoService tipo_vehiculoService;

    @GetMapping("/tipo_vehiculo")
    public List<Tipo_vehiculo> index(){
        return tipo_vehiculoService.findAll();
    }

    @GetMapping("/tipo_vehiculo/{id}")
    public Tipo_vehiculo show(@PathVariable Long id){
        return tipo_vehiculoService.findById(id);
    }

    @PostMapping("/tipo_vehiculo")
    @ResponseStatus(HttpStatus.CREATED)
    public Tipo_vehiculo create(@RequestBody Tipo_vehiculo tipo_vehiculo){
        return tipo_vehiculoService.save(tipo_vehiculo);
    }

    @PutMapping("/tipo_vehiculo/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Tipo_vehiculo update(@RequestBody Tipo_vehiculo tipo_vehiculo, @PathVariable Long id){
        Tipo_vehiculo tipo_vehiculoActual = tipo_vehiculoService.findById(id);
        tipo_vehiculoActual.setTipo_vehiculo(tipo_vehiculo.getTipo_vehiculo());
        return tipo_vehiculoService.save(tipo_vehiculoActual);
    }

    @DeleteMapping("/tipo_vehiculo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        tipo_vehiculoService.delete(id);
    }
}
