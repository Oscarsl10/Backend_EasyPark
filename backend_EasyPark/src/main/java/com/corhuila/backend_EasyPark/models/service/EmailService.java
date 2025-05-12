package com.corhuila.backend_EasyPark.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String correoSoporte;

    public void enviarCorreoSoporte(String nombreUsuario, String correoUsuario, String asunto, String mensaje) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(correoSoporte);
        mail.setSubject("Soporte: " + asunto);
        mail.setText("De: " + nombreUsuario + " (" + correoUsuario + ")\n\n" + mensaje);
        mailSender.send(mail);
    }
}
