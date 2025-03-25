package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Users;
import com.corhuila.backend_EasyPark.models.service.UsersService;
import com.corhuila.backend_EasyPark.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRestController {

    @Autowired
    UsersService usersService;

    @PostMapping("/addUser")
    // @CrossOrigin(origins = {"http://localhost:4200"})
    public Users addUser(@RequestBody Users user){
        return usersService.addUser(user);
    }

    @PostMapping("/loginUser")
    // @CrossOrigin(origins = {"http://localhost:4200"})
    public Boolean loginUser(@RequestBody LoginRequest loginRequest){
        return usersService.loginUser(loginRequest);
    }
}
