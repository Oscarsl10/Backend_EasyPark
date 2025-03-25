package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Users;
import com.corhuila.backend_EasyPark.models.repository.IUsersRepository;
import com.corhuila.backend_EasyPark.requests.LoginRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    IUsersRepository usersRepository;

    public Users addUser(Users user){
        user.setPassword(hashContrasenia(user.getPassword()));
        return usersRepository.save(user);
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

    public Boolean loginUser(LoginRequest loginRequest) {
        Optional<Users> user = usersRepository.findById(loginRequest.getUserId());

        if (user.isEmpty()) {
            return false;
        }

        Users user1 = user.get();
        // Encripta la contraseña ingresada y compárala con la almacenada
        if (!user1.getPassword().equals(hashContrasenia(loginRequest.getPassword()))) {
            return false;
        }

        return true;
    }
}