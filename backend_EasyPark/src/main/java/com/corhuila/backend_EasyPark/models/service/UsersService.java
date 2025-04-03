package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Users;
import com.corhuila.backend_EasyPark.models.repository.IUsersRepository;
import com.corhuila.backend_EasyPark.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsersService {

    @Autowired
    IUsersRepository usersRepository;

    @Autowired
    JavaMailSender mailSender; // Para enviar correos

    // Método para recuperar contraseña
    public void recuperarContrasenia(String email) {
        Optional<Users> usuario = usersRepository.findById(email);

        if (usuario.isEmpty()) {
            throw new RuntimeException("El correo no está registrado.");
        }

        // Generar una nueva contraseña aleatoria
        String nuevaContrasenia = generarContraseniaAleatoria(8);
        String contraseniaEncriptada = hashContrasenia(nuevaContrasenia);

        // Guardar la nueva contraseña encriptada en la BD
        Users user = usuario.get();
        user.setPassword(contraseniaEncriptada);
        usersRepository.save(user);

        // Enviar la nueva contraseña por correo
        enviarCorreo(email, nuevaContrasenia);
    }

    // Método para generar una contraseña aleatoria
    private String generarContraseniaAleatoria(int length) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return sb.toString();
    }

    // Método para enviar la nueva contraseña por correo
    private void enviarCorreo(String destinatario, String nuevaContrasenia) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Recuperación de contraseña EasyPark");
        mensaje.setText("Tu nueva contraseña temporal es: " + nuevaContrasenia +
                "\nPor favor, inicia sesión y cámbiala lo antes posible.");

        mailSender.send(mensaje);
    }

    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public Users findById(String email){
        return usersRepository.findById(email).orElse(null);
    }

    public Users addUser(Users user) {
        if (existsByEmail(user.getEmail())) { // Primero verifica si el email ya existe
            throw new RuntimeException("El correo ya está registrado.");
        }

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

    @Transactional
    public Users save(Users user){
        return usersRepository.save(user);
    }

    public boolean verificarContrasenia(String rawPassword, String hashedPassword) {
        return hashedPassword.equals(hashContrasenia(rawPassword));
    }

    @Transactional(readOnly = true)
    public List<Users> findAll(){
        return (List<Users>) usersRepository.findAll();
    }
}