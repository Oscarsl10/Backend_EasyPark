package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Users;
import com.corhuila.backend_EasyPark.models.service.UsersService;
import com.corhuila.backend_EasyPark.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
public class UsersRestController {

    @Autowired
    UsersService usersService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody Users user) {
        try {
            Users newUser = usersService.addUser(user);
            return ResponseEntity.ok(newUser); // Devuelve 200 OK con el usuario creado
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Devuelve 400 Bad Request si el correo ya existe
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
}
