package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.RegistroVehiculo;
import com.corhuila.backend_EasyPark.models.entity.Vehiculo;
import com.corhuila.backend_EasyPark.models.service.IRegistroVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class RegistroVehiculoRestController {

    @Autowired
    private IRegistroVehiculoService registroVehiculoService;

    @GetMapping("/registroVehiculo")
    public List<RegistroVehiculo> index(){
        return registroVehiculoService.findAll();
    }

    @GetMapping("/registroVehiculo/{id}")
    public RegistroVehiculo show(@PathVariable Long id){
        return registroVehiculoService.findById(id);
    }

    @PostMapping("/registroVehiculo")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroVehiculo create(@RequestBody RegistroVehiculo registroVehiculo){
        return registroVehiculoService.save(registroVehiculo);
    }

    @PutMapping("/registroVehiculo/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroVehiculo update(@RequestBody RegistroVehiculo registroVehiculo, @PathVariable Long id){
        RegistroVehiculo registroVehiculoActual = registroVehiculoService.findById(id);
        registroVehiculoActual.setPlaca(registroVehiculo.getPlaca());
        registroVehiculoActual.setTipoVehiculo(registroVehiculo.getTipoVehiculo());
        return registroVehiculoService.save(registroVehiculoActual);
    }

    @DeleteMapping("/registroVehiculo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        registroVehiculoService.delete(id);
    }
}
