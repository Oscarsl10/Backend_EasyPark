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
        EspacioTotal espacioActual = espacioTotalRepository.findById(espacioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espacio no encontrado"));

        if (espacioActual.getDisponibles() == null) {
            espacioActual.setDisponibles(espacioActual.getEspacio_total());
        }

        // Si es actualización
        if (reserva.getId() != null) {
            Reserva reservaExistente = reservaRepository.findById(reserva.getId()).orElse(null);

            if (reservaExistente != null && reservaExistente.getStatus()) {
                Long espacioAnteriorId = reservaExistente.getEspacio_total().getId();
                int espaciosAnterior = reservaExistente.getEspacios();
                int espaciosNuevo = reserva.getEspacios();

                // Si se mantiene el mismo espacio
                if (espacioAnteriorId.equals(espacioId)) {
                    int diferencia = espaciosNuevo - espaciosAnterior;

                    if (diferencia > 0) {
                        // Se requieren más espacios
                        if (espacioActual.getDisponibles() < diferencia) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay suficientes espacios disponibles");
                        }
                        espacioActual.setDisponibles(espacioActual.getDisponibles() - diferencia);
                    } else if (diferencia < 0) {
                        // Se liberan espacios
                        espacioActual.setDisponibles(espacioActual.getDisponibles() + Math.abs(diferencia));
                    }

                    espacioTotalRepository.save(espacioActual);
                    reserva.setEspacio_total(espacioActual);
                    return reservaRepository.save(reserva);

                } else {
                    // Cambió de espacio, revertimos en el anterior y descontamos del nuevo
                    EspacioTotal espacioAnterior = espacioTotalRepository.findById(espacioAnteriorId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espacio anterior no encontrado"));

                    if (espacioAnterior.getDisponibles() == null) {
                        espacioAnterior.setDisponibles(espacioAnterior.getEspacio_total());
                    }

                    // Liberar en el anterior
                    espacioAnterior.setDisponibles(espacioAnterior.getDisponibles() + espaciosAnterior);
                    espacioTotalRepository.save(espacioAnterior);

                    // Validar en el nuevo
                    if (espacioActual.getDisponibles() < espaciosNuevo) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay suficientes espacios en el nuevo espacio");
                    }

                    espacioActual.setDisponibles(espacioActual.getDisponibles() - espaciosNuevo);
                    espacioTotalRepository.save(espacioActual);

                    reserva.setEspacio_total(espacioActual);
                    return reservaRepository.save(reserva);
                }
            }
        } else {
            // Nueva reserva
            if (espacioActual.getDisponibles() >= reserva.getEspacios()) {
                espacioActual.setDisponibles(espacioActual.getDisponibles() - reserva.getEspacios());
                espacioTotalRepository.save(espacioActual);
                reserva.setEspacio_total(espacioActual);
                return reservaRepository.save(reserva);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay suficientes espacios disponibles");
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo procesar la reserva");
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

            // Validación aquí
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

            // Usar entidad gestionada directamente desde el repositorio
            EspacioTotal espacio = espacioTotalRepository.findById(reserva.getEspacio_total().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Espacio no encontrado"));

            // Validar disponibles null
            if (espacio.getDisponibles() == null) {
                espacio.setDisponibles(espacio.getEspacio_total());
            }

            // Calcular y actualizar disponibles
            int nuevosDisponibles = espacio.getDisponibles() + reserva.getEspacios();
            espacio.setDisponibles(nuevosDisponibles);
            espacioTotalRepository.save(espacio); // Persistir el cambio

            // Marcar reserva como expirada
            reserva.setStatus(false);
            reserva.setDeleted_At(new Date());
            reservaRepository.save(reserva); // Persistir la reserva
        }

        System.out.println("Reservas vencidas liberadas a las " + ahora);
    }
}
