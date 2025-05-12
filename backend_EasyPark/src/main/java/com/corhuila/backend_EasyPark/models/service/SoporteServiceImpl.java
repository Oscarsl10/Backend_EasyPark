package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Soporte;
import com.corhuila.backend_EasyPark.models.repository.ISoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class SoporteServiceImpl implements ISoporteService {

    @Autowired
    private ISoporteRepository soporteRepository;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public List<Soporte> findAll() {
        return soporteRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Soporte findById(Long id) {
        Soporte soporte = soporteRepository.findById(id).orElse(null);
        return (soporte != null && Boolean.TRUE.equals(soporte.getStatus())) ? soporte : null;
    }

    @Override
    @Transactional
    public Soporte save(Soporte mensaje) {
        validarMensaje(mensaje);

        mensaje.setFecha(LocalDateTime.now());
        Soporte guardado = soporteRepository.save(mensaje);

        emailService.enviarCorreoSoporte(
                mensaje.getNombre(),
                mensaje.getCorreo(),
                mensaje.getAsunto(),
                mensaje.getMensaje()
        );

        return guardado;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Soporte soporte = soporteRepository.findById(id).orElse(null);
        if (soporte != null) {
            soporte.setStatus(false);
            soporte.setDeleted_At(new Date());
            soporteRepository.save(soporte);
        }
    }

    private void validarMensaje(Soporte mensaje) {
        if (mensaje.getCorreo() == null || mensaje.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo del remitente es obligatorio.");
        }
        if (mensaje.getNombre() == null || mensaje.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del remitente es obligatorio.");
        }
        if (mensaje.getAsunto() == null || mensaje.getAsunto().trim().isEmpty()) {
            throw new IllegalArgumentException("El asunto es obligatorio.");
        }
        if (mensaje.getMensaje() == null || mensaje.getMensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío.");
        }
    }
}

