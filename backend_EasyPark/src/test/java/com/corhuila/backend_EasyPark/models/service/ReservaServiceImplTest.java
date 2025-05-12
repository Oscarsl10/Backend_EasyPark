package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.EspacioTotal;
import com.corhuila.backend_EasyPark.models.entity.Reserva;
import com.corhuila.backend_EasyPark.models.repository.IEspacioTotalRepository;
import com.corhuila.backend_EasyPark.models.repository.IReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @Mock private IReservaRepository reservaRepo;
    @Mock private IEspacioTotalRepository espacioRepo;
    @InjectMocks private ReservaServiceImpl service;

    private EspacioTotal espacio;
    private Reserva reserva;

    @BeforeEach
    void setup() {
        espacio = new EspacioTotal();
        espacio.setId(1L);
        espacio.setEspacio_total(5);
        espacio.setDisponibles(3);
        reserva = new Reserva();
        reserva.setEspacio_total(espacio);
        reserva.setEspacios(2);
    }

    @Test
    void save_newReservation_reducesDisponiblesAndSaves() {
        when(espacioRepo.findById(1L)).thenReturn(Optional.of(espacio));
        when(reservaRepo.save(reserva)).thenReturn(reserva);

        Reserva out = service.save(reserva);
        assertSame(reserva, out);
        assertEquals(1, espacio.getDisponibles()); // 3 - 2
        verify(espacioRepo).save(espacio);
        verify(reservaRepo).save(reserva);
    }

    @Test
    void save_newReservation_insufficientSpaces_throws() {
        espacio.setDisponibles(1);
        when(espacioRepo.findById(1L)).thenReturn(Optional.of(espacio));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.save(reserva));
        assertEquals(400, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("No hay suficientes espacios"));
    }

    @Test
    void save_updateSameSpace_increaseWithinLimit() {
        Reserva existing = new Reserva();
        existing.setId(10L);
        existing.setEspacio_total(espacio);
        existing.setEspacios(1);
        when(reservaRepo.findById(10L)).thenReturn(Optional.of(existing));

        Reserva upd = new Reserva();
        upd.setId(10L);
        upd.setEspacio_total(espacio);
        upd.setEspacios(3);

        when(espacioRepo.findById(1L)).thenReturn(Optional.of(espacio));
        when(reservaRepo.save(upd)).thenReturn(upd);

        Reserva out = service.save(upd);
        assertSame(upd, out);
        assertEquals(1, espacio.getDisponibles()); // 3 - 2
        verify(espacioRepo).save(espacio);
        verify(reservaRepo).save(upd);
    }

    @Test
    void save_updateSameSpace_increaseTooMuch_throws() {
        Reserva existing = new Reserva();
        existing.setId(10L);
        existing.setEspacio_total(espacio);
        existing.setEspacios(1);
        espacio.setDisponibles(0);
        when(reservaRepo.findById(10L)).thenReturn(Optional.of(existing));
        when(espacioRepo.findById(1L)).thenReturn(Optional.of(espacio));

        Reserva upd = new Reserva();
        upd.setId(10L);
        upd.setEspacio_total(espacio);
        upd.setEspacios(3);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.save(upd));
        assertEquals(400, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("No hay suficientes espacios disponibles"));
    }

    @Test
    void save_updateSameSpace_decreaseFreesSpaces() {
        Reserva existing = new Reserva();
        existing.setId(10L);
        existing.setEspacio_total(espacio);
        existing.setEspacios(3);
        espacio.setDisponibles(1);
        when(reservaRepo.findById(10L)).thenReturn(Optional.of(existing));
        when(espacioRepo.findById(1L)).thenReturn(Optional.of(espacio));
        when(reservaRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Reserva upd = new Reserva();
        upd.setId(10L);
        upd.setEspacio_total(espacio);
        upd.setEspacios(1);

        Reserva out = service.save(upd);
        assertSame(upd, out);
        assertEquals(3, espacio.getDisponibles()); // 1 + 2
    }

    @Test
    void save_updateDifferentSpace_transfersSpaces() {
        EspacioTotal otra = new EspacioTotal();
        otra.setId(2L);
        otra.setEspacio_total(10);
        otra.setDisponibles(5);

        Reserva existing = new Reserva();
        existing.setId(20L);
        existing.setEspacio_total(espacio);
        existing.setEspacios(2);
        when(reservaRepo.findById(20L)).thenReturn(Optional.of(existing));
        when(espacioRepo.findById(1L)).thenReturn(Optional.of(espacio));
        when(espacioRepo.findById(2L)).thenReturn(Optional.of(otra));
        when(reservaRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Reserva upd = new Reserva();
        upd.setId(20L);
        upd.setEspacio_total(otra);
        upd.setEspacios(3);

        Reserva out = service.save(upd);
        assertSame(upd, out);
        assertEquals(5, espacio.getDisponibles()); // +2
        assertEquals(2, otra.getDisponibles());    // 5 - 3
    }

    @Test
    void delete_marksFalse() {
        Reserva r = new Reserva();
        r.setId(30L);
        r.setStatus(true);
        when(reservaRepo.findById(30L)).thenReturn(Optional.of(r));

        service.delete(30L);
        assertFalse(r.getStatus());
        assertNotNull(r.getDeleted_At());
        verify(reservaRepo).save(r);
    }

    @Test
    void restoreEspacios_restoresWhenActive() {
        Reserva r = new Reserva();
        r.setId(40L);
        r.setStatus(true);
        r.setEspacios(2);
        EspacioTotal et = new EspacioTotal();
        et.setId(3L);
        et.setDisponibles(1);
        r.setEspacio_total(et);

        when(reservaRepo.findById(40L)).thenReturn(Optional.of(r));
        when(espacioRepo.findById(3L)).thenReturn(Optional.of(et));

        service.restoreEspacios(40L);
        assertEquals(3, et.getDisponibles());
        verify(espacioRepo).save(et);
    }

    @Test
    void liberarEspaciosReservasExpiradas_processesAll() {
        Reserva exp = new Reserva();
        exp.setId(50L);
        exp.setStatus(true);
        exp.setEspacios(1);
        EspacioTotal et = new EspacioTotal();
        et.setId(4L);
        et.setDisponibles(2);
        exp.setEspacio_total(et);

        when(reservaRepo.findByFechaFinBeforeAndStatusTrue(any(Date.class)))
                .thenReturn(List.of(exp));
        when(espacioRepo.findById(4L)).thenReturn(Optional.of(et));
        when(reservaRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(espacioRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.liberarEspaciosReservasExpiradas();

        assertEquals(3, et.getDisponibles());
        assertFalse(exp.getStatus());
        assertNotNull(exp.getDeleted_At());
        verify(espacioRepo).save(et);
        verify(reservaRepo).save(exp);
    }
}