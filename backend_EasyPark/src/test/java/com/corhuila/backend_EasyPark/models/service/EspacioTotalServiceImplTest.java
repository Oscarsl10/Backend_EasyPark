package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.EspacioTotal;
import com.corhuila.backend_EasyPark.models.repository.IEspacioTotalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspacioTotalServiceImplTest {

    @Mock
    private IEspacioTotalRepository repo;

    @InjectMocks
    private EspacioTotalServiceImpl service;

    private EspacioTotal existing;

    @BeforeEach
    void setUp() {
        existing = new EspacioTotal();
        existing.setId(1L);
        existing.setEspacio_total(10);
        existing.setDisponibles(7);   // 3 reservados
        existing.setDescripcion("Antiguo");
        existing.setStatus(true);
    }

    @Test
    void findAll_delegatesToRepo() {
        when(repo.findByStatusTrue()).thenReturn(List.of(existing));
        List<EspacioTotal> list = service.findAll();
        assertEquals(1, list.size());
        assertSame(existing, list.get(0));
        verify(repo).findByStatusTrue();
    }

    @Test
    void findById_returnsActiveOrNull() {
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        assertSame(existing, service.findById(1L));

        // inactivo → null
        existing.setStatus(false);
        assertNull(service.findById(1L));

        // no existe → null
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));
    }

    @Test
    void save_newEntity_initializesDisponibles() {
        EspacioTotal nuevo = new EspacioTotal();
        nuevo.setEspacio_total(5);
        nuevo.setDescripcion("Nuevo");
        // id null → rama creación
        ArgumentCaptor<EspacioTotal> cap = ArgumentCaptor.forClass(EspacioTotal.class);
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        EspacioTotal guardado = service.save(nuevo);
        assertEquals(5, guardado.getDisponibles());
        assertEquals("Nuevo", guardado.getDescripcion());
        verify(repo).save(cap.capture());
    }

    @Test
    void save_updateEntity_withValidReduction() {
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        EspacioTotal upd = new EspacioTotal();
        upd.setId(1L);
        upd.setEspacio_total(8);
        upd.setDescripcion("Modificado");
        upd.setStatus(true);

        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        EspacioTotal result = service.save(upd);
        // había 3 reservados (10–7), nuevo total 8 → disponibles = 8–3 = 5
        assertEquals(5, result.getDisponibles());
        assertEquals("Modificado", result.getDescripcion());
        verify(repo).save(existing);
    }

    @Test
    void save_updateEntity_tooMuchReduction_throws() {
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        EspacioTotal upd = new EspacioTotal();
        upd.setId(1L);
        upd.setEspacio_total(2); // menor que reservados (3)
        upd.setDescripcion("Bad");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.save(upd));
        assertTrue(ex.getMessage().contains("No se puede reducir"));
        verify(repo, never()).save(any());
    }

    @Test
    void delete_marksAsInactiveAndSetsDeletedAt() {
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        service.delete(1L);
        assertFalse(existing.getStatus());
        assertNotNull(existing.getDeleted_At());
        verify(repo).save(existing);
    }

    @Test
    void delete_noopWhenNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        service.delete(2L);
        verify(repo, never()).save(any());
    }

    @Test
    void actualizarDisponibilidad_withinBounds() {
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        // aumenta en 2 → de 7 a 9
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
        EspacioTotal sube = service.actualizarDisponibilidad(1L, 2);
        assertEquals(9, sube.getDisponibles());

        // reduce en 20 → mínimo 0
        EspacioTotal baja = service.actualizarDisponibilidad(1L, -20);
        assertEquals(0, baja.getDisponibles());

        // aumenta a más que total → tope en 10
        EspacioTotal sobre = service.actualizarDisponibilidad(1L, 20);
        assertEquals(10, sobre.getDisponibles());
    }

    @Test
    void actualizarDisponibilidad_entityNotFound_throws() {
        when(repo.findById(5L))
                .thenThrow(new RuntimeException("Espacio no encontrado"));
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.actualizarDisponibilidad(5L, 1));
        assertEquals("Espacio no encontrado", ex.getMessage());
    }

}