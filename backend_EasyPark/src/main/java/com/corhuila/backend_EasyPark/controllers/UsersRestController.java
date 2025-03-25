package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Users;
import com.corhuila.backend_EasyPark.models.service.UsersService;
import com.corhuila.backend_EasyPark.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
public class UsersRestController {

    @Autowired
    UsersService usersService;

    @PostMapping("/addUser")
    public Users addUser(@RequestBody Users user){
        return usersService.addUser(user);
    }

    @PostMapping("/loginUser")
    public Boolean loginUser(@RequestBody LoginRequest loginRequest){
        return usersService.loginUser(loginRequest);
    }

    @GetMapping("/getUser/{email}")
    public Users show(@PathVariable String email){
        return usersService.findById(email);
    }
}
