package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.FacturaReserva;
import com.corhuila.backend_EasyPark.models.entity.PagoReserva;
import com.corhuila.backend_EasyPark.models.entity.Reserva;
import com.corhuila.backend_EasyPark.models.entity.Vehiculo;
import com.corhuila.backend_EasyPark.models.repository.IPagoReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoReservaServiceImplTest {

    @Mock
    private IPagoReservaRepository repo;

    @Mock
    private IFacturaReservaService facturaReservaService;

    @InjectMocks
    private PagoReservaServiceImpl service;

    private PagoReserva active;
    private PagoReserva inactive;

    @BeforeEach
    void setUp() {
        active = new PagoReserva();
        active.setId(1L);
        active.setStatus(true);

        inactive = new PagoReserva();
        inactive.setId(2L);
        inactive.setStatus(false);
    }

    @Test
    void findAll_returnsOnlyActive() {
        when(repo.findByStatusTrue()).thenReturn(List.of(active));
        List<PagoReserva> list = service.findAll();
        assertEquals(1, list.size());
        assertSame(active, list.get(0));
        verify(repo).findByStatusTrue();
    }

    @Test
    void findById_returnsActiveOrNull() {
        when(repo.findById(1L)).thenReturn(Optional.of(active));
        assertSame(active, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.of(inactive));
        assertNull(service.findById(2L));

        when(repo.findById(3L)).thenReturn(Optional.empty());
        assertNull(service.findById(3L));
    }

    @Test
    void save_savesAndCreatesFacturaReserva() {
        // Prepare PagoReserva with minimal data
        Reserva reserva = mock(Reserva.class);
        Vehiculo vehiculo = mock(Vehiculo.class);
        PagoReserva input = new PagoReserva();
        input.setReserva(reserva);
        input.setVehiculo(vehiculo);

        when(repo.save(input)).thenReturn(input);
        ArgumentCaptor<FacturaReserva> cap = ArgumentCaptor.forClass(FacturaReserva.class);
        when(facturaReservaService.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PagoReserva out = service.save(input);
        assertSame(input, out);
        verify(repo).save(input);
        verify(facturaReservaService).save(cap.capture());

        FacturaReserva created = cap.getValue();
        assertEquals(reserva, created.getPagoReserva().getReserva());
        assertEquals(vehiculo, created.getVehiculo());
        assertNotNull(created.getNumeroFactura());
        assertNotNull(created.getFechaEmision());
    }

    @Test
    void delete_marksAsInactiveAndSetsDeletedAt() {
        when(repo.findById(1L)).thenReturn(Optional.of(active));
        service.delete(1L);
        assertFalse(active.getStatus());
        assertNotNull(active.getDeleted_At());
        verify(repo).save(active);
    }

    @Test
    void delete_noopWhenNotFound() {
        when(repo.findById(5L)).thenReturn(Optional.empty());
        service.delete(5L);
        verify(repo, never()).save(any());
    }

}