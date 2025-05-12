package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Soporte;
import com.corhuila.backend_EasyPark.models.repository.ISoporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SoporteServiceImplTest {

    @Mock
    private ISoporteRepository repo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private SoporteServiceImpl service;

    private Soporte valid;

    @BeforeEach
    void setUp() {
        valid = new Soporte();
        valid.setNombre("User");
        valid.setCorreo("user@example.com");
        valid.setAsunto("Help");
        valid.setMensaje("I need support");
    }

    @Test
    void findAll_delegatesToRepo() {
        List<Soporte> lista = List.of(valid);
        when(repo.findByStatusTrue()).thenReturn(lista);
        List<Soporte> out = service.findAll();
        assertSame(lista, out);
        verify(repo).findByStatusTrue();
    }

    @Test
    void findById_returnsActiveOrNull() {
        valid.setId(1L);
        valid.setStatus(true);
        when(repo.findById(1L)).thenReturn(Optional.of(valid));
        assertSame(valid, service.findById(1L));

        Soporte inact = new Soporte();
        inact.setId(2L);
        inact.setStatus(false);
        when(repo.findById(2L)).thenReturn(Optional.of(inact));
        assertNull(service.findById(2L));

        when(repo.findById(3L)).thenReturn(Optional.empty());
        assertNull(service.findById(3L));
    }

    @Test
    void save_withMissingCorreo_throws() {
        valid.setCorreo(null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.save(valid));
        assertEquals("El correo del remitente es obligatorio.", ex.getMessage());
    }

    @Test
    void save_withMissingNombre_throws() {
        valid.setNombre("   ");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.save(valid));
        assertEquals("El nombre del remitente es obligatorio.", ex.getMessage());
    }

    @Test
    void save_withMissingAsunto_throws() {
        valid.setAsunto("");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.save(valid));
        assertEquals("El asunto es obligatorio.", ex.getMessage());
    }

    @Test
    void save_withMissingMensaje_throws() {
        valid.setMensaje(" ");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.save(valid));
        assertEquals("El mensaje no puede estar vacío.", ex.getMessage());
    }

    @Test
    void save_validMessage_savesAndSendsEmail() {
        ArgumentCaptor<Soporte> cap = ArgumentCaptor.forClass(Soporte.class);
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Soporte out = service.save(valid);

        // fecha must be set to now (non-null)
        assertNotNull(out.getFecha());
        // persisted via repo
        verify(repo).save(cap.capture());
        Soporte saved = cap.getValue();
        assertEquals("User", saved.getNombre());
        assertEquals("user@example.com", saved.getCorreo());

        // emailService called once with same datos
        verify(emailService).enviarCorreoSoporte(
                eq("User"),
                eq("user@example.com"),
                eq("Help"),
                eq("I need support")
        );
    }

    @Test
    void delete_marksAsInactiveAndSetsDeletedAt() {
        Soporte s = new Soporte();
        s.setId(5L);
        s.setStatus(true);
        when(repo.findById(5L)).thenReturn(Optional.of(s));

        service.delete(5L);

        assertFalse(s.getStatus());
        assertNotNull(s.getDeleted_At());
        verify(repo).save(s);
    }

    @Test
    void delete_noopWhenNotFound() {
        when(repo.findById(9L)).thenReturn(Optional.empty());
        service.delete(9L);
        verify(repo, never()).save(any());
    }

}