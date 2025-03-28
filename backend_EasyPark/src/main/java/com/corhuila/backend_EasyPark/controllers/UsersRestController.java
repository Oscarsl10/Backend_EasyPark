package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Admin;
import com.corhuila.backend_EasyPark.models.entity.Users;
import com.corhuila.backend_EasyPark.models.entity.Vehiculo;
import com.corhuila.backend_EasyPark.models.repository.IAdminRepository;
import com.corhuila.backend_EasyPark.models.service.UsersService;
import com.corhuila.backend_EasyPark.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
public class UsersRestController {

    @Autowired
    UsersService usersService;

    @Autowired
    IAdminRepository adminRepository; // Inyectamos el repositorio de admin

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody Users user) {
        // Validación para no permitir el uso del correo existente y de un administrador
        Optional<Admin> adminExistente = adminRepository.findById(user.getEmail());
        if (adminExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ADMIN_EMAIL");
        }

        try {
            Users newUser = usersService.addUser(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("USER_EMAIL");
        }
    }

    @PostMapping("/loginUser")
    public Boolean loginUser(@RequestBody LoginRequest loginRequest){
        return usersService.loginUser(loginRequest);
    }

    @GetMapping("/getUser/{email}")
    public Users show(@PathVariable String email){
        return usersService.findById(email);
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = usersService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/user/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public Users update(@RequestBody Users users, @PathVariable String email){
        Users userActual = usersService.findById(email);
        userActual.setFull_name(users.getFull_name());
        userActual.setTelefono(users.getTelefono());

        // Verifica si se envió una nueva contraseña antes de encriptarla
        if (users.getPassword() != null && !users.getPassword().isEmpty()) {
            userActual.setPassword(usersService.hashContrasenia(users.getPassword()));
        }

        return usersService.save(userActual);
    }

    // Endpoint para recuperar la contraseña
    @PostMapping("/recuperar-contrasenia")
    public ResponseEntity<Map<String, String>> recuperarContrasenia(@RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        try {
            usersService.recuperarContrasenia(email);
            response.put("message", "Se ha enviado una nueva contraseña a tu correo.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
