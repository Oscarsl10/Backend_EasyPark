package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Reserva;
import com.corhuila.backend_EasyPark.models.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class ReservaRestController {

    @Autowired
    private IReservaService reservaService;

    @GetMapping("/reserva")
    public List<Reserva> index(){
        return reservaService.findAll();
    }

    @GetMapping("/reserva/{id}")
    public Reserva show(@PathVariable Long id){
        return reservaService.findById(id);
    }

    @PostMapping("/reserva")
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva create(@RequestBody Reserva reserva){
        return reservaService.save(reserva);
    }

    @PutMapping("/reserva/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva update(@RequestBody Reserva reserva, @PathVariable Long id){
        Reserva reservaActual = reservaService.findById(id);
        reservaActual.setEspacios(reserva.getEspacios());
        reservaActual.setFechaInicio(reserva.getFechaInicio());
        reservaActual.setFechaFin(reserva.getFechaFin());
        reservaActual.setPrecio(reserva.getPrecio());
        return reservaService.save(reservaActual);
    }

    @DeleteMapping("/reserva/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        reservaService.delete(id);
    }
}
