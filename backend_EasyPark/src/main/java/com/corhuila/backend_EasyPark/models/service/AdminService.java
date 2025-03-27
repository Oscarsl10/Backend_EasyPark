package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Admin;
import com.corhuila.backend_EasyPark.models.repository.IAdminRepository;
import com.corhuila.backend_EasyPark.requests.LoginAdminRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    IAdminRepository adminRepository;

    public boolean existsAdminByEmail(String email) {
        return adminRepository.existsAdminByEmail(email);
    }

    @Transactional(readOnly = true)
    public Admin findById(String email){
        return adminRepository.findById(email).orElse(null);
    }

    public Admin addAdmin(Admin admin){
        if (existsAdminByEmail(admin.getEmail())) { // Primero verifica si el email ya existe
            throw new RuntimeException("El correo ya está registrado.");
        }

        admin.setPassword(hashContrasenia(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public String hashContrasenia(String password){
        try{
            MessageDigest instancia = MessageDigest.getInstance("SHA-256");
            byte [] hash = instancia.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error al encriptar la contraseña");
        }
    }

    public Boolean loginAdmin(LoginAdminRequest loginAdminRequest){
        Optional<Admin> admin = adminRepository.findById(loginAdminRequest.getUserId());

        if (admin.isEmpty()){
            return false;
        }

        Admin admin1 = admin.get();

        if (!admin1.getPassword().equals(hashContrasenia(loginAdminRequest.getPassword()))){
            return false;
        }

        return true;
    }
}
