package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Calificacion;
import com.corhuila.backend_EasyPark.models.entity.Tipo_tarifa;
import com.corhuila.backend_EasyPark.models.service.ICalificaciónService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class CalificacionRestController {

    @Autowired
    private ICalificaciónService calificaciónService;

    @GetMapping("/calificacion")
    public List<Calificacion> index(){
        return calificaciónService.findAll();
    }

    @GetMapping("/calificacion/{id}")
    public Calificacion show(@PathVariable Long id){
        return calificaciónService.findById(id);
    }

    @PostMapping("/calificacion")
    @ResponseStatus(HttpStatus.CREATED)
    public Calificacion create(@RequestBody Calificacion calificacion){
        return calificaciónService.save(calificacion);
    }

    @PutMapping("/calificacion/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Calificacion update(@RequestBody Calificacion calificacion, @PathVariable Long id){
        Calificacion calificacionActual = calificaciónService.findById(id);
        calificacionActual.setCalificacion(calificacion.getCalificacion());
        calificacionActual.setComentario(calificacion.getComentario());
        calificacionActual.setFecha(calificacion.getFecha());
        calificacionActual.setStatus(calificacion.getStatus());
        return calificaciónService.save(calificacionActual);
    }

    @DeleteMapping("/calificacion/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        calificaciónService.delete(id);
    }
}
