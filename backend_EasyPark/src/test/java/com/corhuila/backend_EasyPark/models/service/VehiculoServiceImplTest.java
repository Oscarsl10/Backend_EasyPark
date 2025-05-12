package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Vehiculo;
import com.corhuila.backend_EasyPark.models.repository.IVehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceImplTest {

    @Mock
    private IVehiculoRepository repo;

    @InjectMocks
    private VehiculoServiceImpl service;

    private Vehiculo active;
    private Vehiculo inactive;

    @BeforeEach
    void setUp() {
        active = new Vehiculo();
        active.setId(1L);
        active.setPlaca("ABC123");
        active.setTipoVehiculo("Auto");
        active.setColor("Rojo");
        active.setStatus(true);

        inactive = new Vehiculo();
        inactive.setId(2L);
        inactive.setPlaca("XYZ789");
        inactive.setTipoVehiculo("Moto");
        inactive.setColor("Azul");
        inactive.setStatus(false);
    }

    @Test
    void findAll_returnsOnlyActive() {
        when(repo.findByStatusTrue()).thenReturn(List.of(active));
        List<Vehiculo> list = service.findAll();
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
    void save_delegatesToRepository() {
        Vehiculo v = new Vehiculo();
        v.setPlaca("NEW123");
        v.setTipoVehiculo("Camion");
        v.setColor("Verde");
        when(repo.save(v)).thenReturn(v);
        Vehiculo out = service.save(v);
        assertSame(v, out);
        verify(repo).save(v);
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