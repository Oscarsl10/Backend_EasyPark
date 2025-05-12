package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Factura;
import com.corhuila.backend_EasyPark.models.entity.Pago;
import com.corhuila.backend_EasyPark.models.entity.RegistroVehiculo;
import com.corhuila.backend_EasyPark.models.entity.Tarifa;
import com.corhuila.backend_EasyPark.models.repository.IPagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceImplTest {

    @Mock
    private IPagoRepository repo;

    @Mock
    private IFacturaService facturaService;

    @InjectMocks
    private PagoServiceImpl service;

    private Pago pActive;
    private Pago pInactive;

    @BeforeEach
    void setUp() {
        pActive = new Pago();
        pActive.setId(1L);
        pActive.setStatus(true);

        pInactive = new Pago();
        pInactive.setId(2L);
        pInactive.setStatus(false);
    }

    @Test
    void findAll_returnsOnlyActive() {
        when(repo.findByStatusTrue()).thenReturn(List.of(pActive));
        List<Pago> list = service.findAll();
        assertEquals(1, list.size());
        assertSame(pActive, list.get(0));
        verify(repo).findByStatusTrue();
    }

    @Test
    void findById_returnsActiveOrNull() {
        when(repo.findById(1L)).thenReturn(Optional.of(pActive));
        assertSame(pActive, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.of(pInactive));
        assertNull(service.findById(2L));

        when(repo.findById(3L)).thenReturn(Optional.empty());
        assertNull(service.findById(3L));
    }

    @Test
    void save_savesPagoAndCreatesFactura() {
        // Prepare pago with minimal data
        RegistroVehiculo rv = mock(RegistroVehiculo.class);
        Tarifa tarifa = mock(Tarifa.class);
        Pago input = new Pago();
        input.setRegistroVehiculo(rv);
        input.setTarifa(tarifa);
        when(repo.save(input)).thenReturn(input);

        // Capture the factura passed to facturaService.save(...)
        ArgumentCaptor<Factura> facturaCap = ArgumentCaptor.forClass(Factura.class);
        when(facturaService.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Pago out = service.save(input);
        assertSame(input, out);

        verify(repo).save(input);
        verify(facturaService).save(facturaCap.capture());

        Factura created = facturaCap.getValue();
        assertEquals(input, created.getPago());
        assertEquals(rv, created.getRegistroVehiculo());
        assertNotNull(created.getNumeroFactura());
        assertNotNull(created.getFechaEmision());
    }

    @Test
    void delete_marksAsInactiveAndSetsDeletedAt() {
        when(repo.findById(1L)).thenReturn(Optional.of(pActive));
        service.delete(1L);
        assertFalse(pActive.getStatus());
        assertNotNull(pActive.getDeleted_At());
        verify(repo).save(pActive);
    }

    @Test
    void delete_noopWhenNotFound() {
        when(repo.findById(5L)).thenReturn(Optional.empty());
        service.delete(5L);
        verify(repo, never()).save(any());
    }

}