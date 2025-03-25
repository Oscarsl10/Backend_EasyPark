package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Admin;
import com.corhuila.backend_EasyPark.models.service.AdminService;
import com.corhuila.backend_EasyPark.requests.LoginAdminRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
public class AdminRestController {

    @Autowired
    AdminService adminService;

    @PostMapping("/addAdmin")
    public Admin addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }

    @PostMapping("/loginAdmin")
    public Boolean loginAdmin(@RequestBody LoginAdminRequest loginAdminRequest){
        return adminService.loginAdmin(loginAdminRequest);
    }

    @GetMapping("/getAdmin/{email}")
    public Admin show(@PathVariable String email){
        return adminService.findById(email);
    }
}
