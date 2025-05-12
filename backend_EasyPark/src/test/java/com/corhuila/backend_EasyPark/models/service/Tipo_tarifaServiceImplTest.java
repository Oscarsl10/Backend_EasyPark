package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tipo_tarifa;
import com.corhuila.backend_EasyPark.models.repository.ITipo_tarifaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Tipo_tarifaServiceImplTest {

    @Mock
    private ITipo_tarifaRepository repo;

    @InjectMocks
    private Tipo_tarifaServiceImpl service;

    private Tipo_tarifa active;
    private Tipo_tarifa inactive;

    @BeforeEach
    void setUp() {
        active = new Tipo_tarifa();
        active.setId(1L);
        active.setTipo_tarifa("Tarifa A");
        active.setStatus(true);

        inactive = new Tipo_tarifa();
        inactive.setId(2L);
        inactive.setTipo_tarifa("Tarifa B");
        inactive.setStatus(false);
    }

    @Test
    void findAll_returnsOnlyActive() {
        when(repo.findByStatusTrue()).thenReturn(List.of(active));
        List<Tipo_tarifa> list = service.findAll();
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
        Tipo_tarifa t = new Tipo_tarifa();
        t.setTipo_tarifa("New");
        when(repo.save(t)).thenReturn(t);
        Tipo_tarifa out = service.save(t);
        assertSame(t, out);
        verify(repo).save(t);
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