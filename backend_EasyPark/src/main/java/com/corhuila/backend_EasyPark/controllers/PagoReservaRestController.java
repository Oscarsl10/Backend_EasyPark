package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.PagoReserva;
import com.corhuila.backend_EasyPark.models.service.IPagoReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class PagoReservaRestController {

    @Autowired
    private IPagoReservaService pagoReservaService;

    @GetMapping("/pago-reserva")
    public List<PagoReserva> index(){
        return pagoReservaService.findAll();
    }

    @GetMapping("/pago-reserva/{id}")
    public PagoReserva show(@PathVariable Long id){
        return pagoReservaService.findById(id);
    }

    @PostMapping("/pago-reserva")
    @ResponseStatus(HttpStatus.CREATED)
    public PagoReserva create(@RequestBody PagoReserva pagoReserva){
        return pagoReservaService.save(pagoReserva);
    }

    @PutMapping("/pago-reserva/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PagoReserva update(@RequestBody PagoReserva pagoReserva, @PathVariable Long id){
        PagoReserva pagoReservaActual = pagoReservaService.findById(id);
        pagoReservaActual.setReserva(pagoReserva.getReserva());
        pagoReservaActual.setVehiculo(pagoReserva.getVehiculo());
        pagoReservaActual.setStatus(pagoReserva.getStatus());
        pagoReservaActual.setMetodoPago(pagoReserva.getMetodoPago());
        return pagoReservaService.save(pagoReservaActual);
    }

    @DeleteMapping("/pago-reserva/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        pagoReservaService.delete(id);
    }
}
