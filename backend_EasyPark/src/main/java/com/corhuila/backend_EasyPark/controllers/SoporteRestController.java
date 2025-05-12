package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Soporte;
import com.corhuila.backend_EasyPark.models.service.ISoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
@RequestMapping("/api")
public class SoporteRestController {

    @Autowired
    private ISoporteService soporteService;

    @PostMapping("/soporte/enviar")
    public ResponseEntity<Soporte> enviarMensaje(@RequestBody Soporte soporte) {
        Soporte guardado = soporteService.save(soporte);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping("/soporte")
    public ResponseEntity<List<Soporte>> index() {
        List<Soporte> lista = soporteService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/soporte/{id}")
    public ResponseEntity<Soporte> show(@PathVariable Long id) {
        Soporte soporte = soporteService.findById(id);
        if (soporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(soporte);
    }

    @DeleteMapping("/soporte/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        soporteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}




