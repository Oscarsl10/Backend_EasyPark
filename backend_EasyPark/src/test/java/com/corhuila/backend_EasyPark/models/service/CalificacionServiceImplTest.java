package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Calificacion;
import com.corhuila.backend_EasyPark.models.repository.ICalificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceImplTest {

    @Mock
    private ICalificacionRepository repo;

    @InjectMocks
    private CalificacionServiceImpl service;

    private Calificacion c1;
    private Calificacion c2;

    @BeforeEach
    void setUp() {
        c1 = new Calificacion();
        c1.setId(1L);
        c1.setStatus(true);
        c2 = new Calificacion();
        c2.setId(2L);
        c2.setStatus(false);
    }

    @Test
    void findAll_returnsOnlyStatusTrue() {
        when(repo.findByStatusTrue()).thenReturn(List.of(c1));
        List<Calificacion> list = service.findAll();
        assertEquals(1, list.size());
        assertTrue(list.contains(c1));
        verify(repo).findByStatusTrue();
    }

    @Test
    void findById_returnsEntityWhenExistsAndActive() {
        when(repo.findById(1L)).thenReturn(Optional.of(c1));
        Calificacion found = service.findById(1L);
        assertSame(c1, found);
    }

    @Test
    void findById_returnsNullWhenNotFoundOrInactive() {
        when(repo.findById(42L)).thenReturn(Optional.empty());
        assertNull(service.findById(42L));

        when(repo.findById(2L)).thenReturn(Optional.of(c2));
        assertNull(service.findById(2L));
    }

    @Test
    void save_delegatesToRepository() {
        Calificacion toSave = new Calificacion();
        when(repo.save(toSave)).thenReturn(toSave);
        assertSame(toSave, service.save(toSave));
        verify(repo).save(toSave);
    }

    @Test
    void delete_marksAsInactiveAndSetsDeletedAt() {
        Calificacion toDelete = new Calificacion();
        toDelete.setId(99L);
        when(repo.findById(99L)).thenReturn(Optional.of(toDelete));

        service.delete(99L);

        assertFalse(toDelete.getStatus());
        assertNotNull(toDelete.getDeleted_At());
        verify(repo).save(toDelete);
    }

    @Test
    void delete_noopWhenNotFound() {
        when(repo.findById(7L)).thenReturn(Optional.empty());
        service.delete(7L);
        verify(repo, never()).save(any());
    }

}