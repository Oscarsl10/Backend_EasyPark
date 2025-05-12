package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Tarifa;
import com.corhuila.backend_EasyPark.models.repository.ITarifaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarifaServiceImplTest {

    @Mock
    private ITarifaRepository repo;

    @InjectMocks
    private TarifaServiceImpl service;

    private Tarifa active;
    private Tarifa inactive;

    @BeforeEach
    void setUp() {
        active = new Tarifa();
        active.setId(1L);
        active.setPrecio(10.0);
        active.setStatus(true);

        inactive = new Tarifa();
        inactive.setId(2L);
        inactive.setPrecio(20.0);
        inactive.setStatus(false);
    }

    @Test
    void findAll_returnsOnlyActive() {
        when(repo.findByStatusTrue()).thenReturn(List.of(active));
        List<Tarifa> list = service.findAll();
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
        Tarifa t = new Tarifa();
        t.setPrecio(30.0);
        when(repo.save(t)).thenReturn(t);
        Tarifa out = service.save(t);
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