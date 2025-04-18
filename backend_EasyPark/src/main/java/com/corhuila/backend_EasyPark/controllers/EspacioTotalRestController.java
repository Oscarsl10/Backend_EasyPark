package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.EspacioTotal;
import com.corhuila.backend_EasyPark.models.service.IEspacioTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class EspacioTotalRestController {

    @Autowired
    private IEspacioTotalService espacioService;

    @GetMapping("/espacio_total")
    public List<EspacioTotal> index(){
        return espacioService.findAll();
    }

    @GetMapping("/espacio_total/{id}")
    public EspacioTotal show(@PathVariable Long id){
        return espacioService.findById(id);
    }

    @PostMapping("/espacio_total")
    @ResponseStatus(HttpStatus.CREATED)
    public EspacioTotal create(@RequestBody EspacioTotal espacio){
        return espacioService.save(espacio);
    }

    @PutMapping("/espacio_total/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EspacioTotal update(@RequestBody EspacioTotal espacio, @PathVariable Long id){
        espacio.setId(id); // aseguramos que venga con el ID
        return espacioService.save(espacio);
    }

    @DeleteMapping("/espacio_total/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        espacioService.delete(id);
    }
}
