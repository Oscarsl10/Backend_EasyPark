package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Factura;
import com.corhuila.backend_EasyPark.models.repository.IFacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceImplTest {

    @Mock
    private IFacturaRepository repo;

    @InjectMocks
    private FacturaServiceImpl service;

    private Factura fActive;
    private Factura fInactive;

    @BeforeEach
    void setUp() {
        fActive = new Factura();
        fActive.setId(1L);
        fActive.setStatus(true);
        fInactive = new Factura();
        fInactive.setId(2L);
        fInactive.setStatus(false);
    }

    @Test
    void findAll_returnsOnlyActive() {
        when(repo.findByStatusTrue()).thenReturn(List.of(fActive));
        List<Factura> list = service.findAll();
        assertEquals(1, list.size());
        assertTrue(list.contains(fActive));
        verify(repo).findByStatusTrue();
    }

    @Test
    void findById_returnsActiveOrNull() {
        when(repo.findById(1L)).thenReturn(Optional.of(fActive));
        assertSame(fActive, service.findById(1L));

        // inactive → null
        when(repo.findById(2L)).thenReturn(Optional.of(fInactive));
        assertNull(service.findById(2L));

        // not found → null
        when(repo.findById(3L)).thenReturn(Optional.empty());
        assertNull(service.findById(3L));
    }

    @Test
    void save_delegatesToRepository() {
        Factura input = new Factura();
        when(repo.save(input)).thenReturn(input);
        Factura out = service.save(input);
        assertSame(input, out);
        verify(repo).save(input);
    }

    @Test
    void delete_marksAsInactiveAndSetsDeletedAt() {
        when(repo.findById(1L)).thenReturn(Optional.of(fActive));
        service.delete(1L);
        assertFalse(fActive.getStatus());
        assertNotNull(fActive.getDeleted_At());
        verify(repo).save(fActive);
    }

    @Test
    void delete_noopWhenNotFound() {
        when(repo.findById(5L)).thenReturn(Optional.empty());
        service.delete(5L);
        verify(repo, never()).save(any());
    }

    @Test
    void findByRegistroVehiculoId_delegatesToRepo() {
        Factura f1 = new Factura();
        when(repo.findByRegistroVehiculoId(10L)).thenReturn(List.of(f1));
        List<Factura> result = service.findByRegistroVehiculoId(10L);
        assertEquals(1, result.size());
        assertSame(f1, result.get(0));
        verify(repo).findByRegistroVehiculoId(10L);
    }

    @Test
    void findByPagoId_delegatesToRepo() {
        Factura f2 = new Factura();
        when(repo.findByPagoId(20L)).thenReturn(f2);
        Factura result = service.findByPagoId(20L);
        assertSame(f2, result);
        verify(repo).findByPagoId(20L);
    }

}