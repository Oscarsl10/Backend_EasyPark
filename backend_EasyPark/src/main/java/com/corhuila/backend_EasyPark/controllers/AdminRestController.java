package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Admin;
import com.corhuila.backend_EasyPark.models.entity.Users;
import com.corhuila.backend_EasyPark.models.repository.IAdminRepository;
import com.corhuila.backend_EasyPark.models.repository.IUsersRepository;
import com.corhuila.backend_EasyPark.models.service.AdminService;
import com.corhuila.backend_EasyPark.models.service.UsersService;
import com.corhuila.backend_EasyPark.requests.LoginAdminRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200"})

@RestController
public class AdminRestController {

    @Autowired
    AdminService adminService;
    @Autowired
    IUsersRepository usersRepository;

    @PostMapping("/addAdmin")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin){
        // Validación para no permitir el uso del correo de un usuario
        Optional<Users> usersExistente = usersRepository.findById(admin.getEmail());
        if (usersExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("USER_EMAIL");
        }

        try {
            Admin newAdmin = adminService.addAdmin(admin);
            return ResponseEntity.ok(newAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ADMIN_EMAIL");
        }
    }

    @PostMapping("/loginAdmin")
    public Boolean loginAdmin(@RequestBody LoginAdminRequest loginAdminRequest){
        return adminService.loginAdmin(loginAdminRequest);
    }

    @GetMapping("/getAdmin/{email}")
    public Admin show(@PathVariable String email){
        return adminService.findById(email);
    }


    @GetMapping("/checkEmailAdmin")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = adminService.existsAdminByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
