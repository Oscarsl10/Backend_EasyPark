package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.RegistroVehiculo;
import com.corhuila.backend_EasyPark.models.entity.Reporte;
import com.corhuila.backend_EasyPark.models.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class ReporteRestController {

    @Autowired
    private IReporteService reporteService;

    @GetMapping("/reporte")
    public List<Reporte> index(){
        return reporteService.findAll();
    }

    @GetMapping("/reporte/{id}")
    public Reporte show(@PathVariable Long id){
        return reporteService.findById(id);
    }

    @PostMapping("/reporte")
    @ResponseStatus(HttpStatus.CREATED)
    public Reporte create(@RequestBody Reporte reporte){
        return reporteService.save(reporte);
    }

    @PutMapping("/reporte/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Reporte update(@RequestBody Reporte reporte, @PathVariable Long id){
        Reporte reporteActual = reporteService.findById(id);
        reporteActual.setAutor(reporte.getAutor());
        reporteActual.setMensaje(reporte.getMensaje());
        reporteActual.setStatus(reporte.getStatus());
        return reporteService.save(reporteActual);
    }

    @DeleteMapping("/reporte/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        reporteService.delete(id);
    }
}
