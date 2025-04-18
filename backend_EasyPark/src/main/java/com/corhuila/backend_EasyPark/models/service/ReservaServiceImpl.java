package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.EspacioTotal;
import com.corhuila.backend_EasyPark.models.entity.Reserva;
import com.corhuila.backend_EasyPark.models.repository.IEspacioTotalRepository;
import com.corhuila.backend_EasyPark.models.repository.IReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService{

    @Autowired
    private IReservaRepository reservaRepository;
    @Autowired
    private IEspacioTotalRepository espacioTotalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findAll(){
        return reservaRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Reserva findById(Long id){
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        return (reserva != null && Boolean.TRUE.equals(reserva.getStatus())) ? reserva : null;
    }

    @Override
    @Transactional
    public Reserva save(Reserva reserva) {
        Long espacioId = reserva.getEspacio_total().getId();
        EspacioTotal espacioTotal = espacioTotalRepository.findById(espacioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espacio no encontrado"));

        // ✅ Validación aquí
        if (espacioTotal.getDisponibles() == null) {
            espacioTotal.setDisponibles(espacioTotal.getEspacio_total());
        }

        if (espacioTotal.getDisponibles() >= reserva.getEspacios()) {
            espacioTotal.setDisponibles(espacioTotal.getDisponibles() - reserva.getEspacios());
            espacioTotalRepository.save(espacioTotal); // Asegura persistencia
            reserva.setEspacio_total(espacioTotal);
            return reservaRepository.save(reserva);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay suficientes espacios disponibles");
        }
    }

    @Override
    @Transactional
    public void delete(Long id){
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva != null) {
            reserva.setStatus(false);
            reserva.setDeleted_At(new Date());
            reservaRepository.save(reserva);
        }
    }

    @Override
    @Transactional
    public void restoreEspacios(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId).orElse(null);
        if (reserva != null && Boolean.TRUE.equals(reserva.getStatus())) {
            EspacioTotal espacioTotal = espacioTotalRepository.findById(reserva.getEspacio_total().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espacio no encontrado"));

            // ✅ Validación aquí
            if (espacioTotal.getDisponibles() == null) {
                espacioTotal.setDisponibles(espacioTotal.getEspacio_total());
            }

            espacioTotal.setDisponibles(espacioTotal.getDisponibles() + reserva.getEspacios());
            espacioTotalRepository.save(espacioTotal);
        }
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = 60000) // cada minuto
    public void liberarEspaciosReservasExpiradas() {
        Date ahora = new Date();
        List<Reserva> reservas = reservaRepository.findByFechaFinBeforeAndStatusTrue(ahora);

        for (Reserva reserva : reservas) {

            // 🔁 Usar entidad gestionada directamente desde el repositorio
            EspacioTotal espacio = espacioTotalRepository.findById(reserva.getEspacio_total().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espacio no encontrado"));

            // 🛡️ Validar disponibles null
            if (espacio.getDisponibles() == null) {
                espacio.setDisponibles(espacio.getEspacio_total());
            }

            // 📊 Calcular y actualizar disponibles
            int nuevosDisponibles = espacio.getDisponibles() + reserva.getEspacios();
            espacio.setDisponibles(nuevosDisponibles);
            espacioTotalRepository.save(espacio); // 🔐 Persistir el cambio

            // ❌ Marcar reserva como expirada
            reserva.setStatus(false);
            reserva.setDeleted_At(new Date());
            reservaRepository.save(reserva); // 🔐 Persistir la reserva
        }

        System.out.println("Reservas vencidas liberadas a las " + ahora);
    }
}
