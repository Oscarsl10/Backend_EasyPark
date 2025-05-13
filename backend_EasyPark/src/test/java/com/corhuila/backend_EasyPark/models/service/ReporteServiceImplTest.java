package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Reporte;
import com.corhuila.backend_EasyPark.models.repository.IReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReporteServiceImplTest {

    @Mock
    private IReporteRepository repo;

    @InjectMocks
    private ReporteServiceImpl service;

    private Reporte activo;
    private Reporte inactivo;

    @BeforeEach
    void setUp() {
        activo = new Reporte();
        activo.setId(1L);
        activo.setAutor("Juan");
        activo.setMensaje("Todo bien");
        activo.setStatus(true);

        inactivo = new Reporte();
        inactivo.setId(2L);
        inactivo.setAutor("Ana");
        inactivo.setMensaje("Problema con acceso");
        inactivo.setStatus(false);
    }

    @Test
    void findAll_debeRetornarSoloActivos() {
        when(repo.findByStatusTrue()).thenReturn(List.of(activo));
        List<Reporte> result = service.findAll();
        assertEquals(1, result.size());
        assertSame(activo, result.get(0));
    }

    @Test
    void findById_activoDevuelto_inactivoONuloRetornaNull() {
        when(repo.findById(1L)).thenReturn(Optional.of(activo));
        assertSame(activo, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.of(inactivo));
        assertNull(service.findById(2L));

        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertNull(service.findById(99L));
    }

    @Test
    void save_delegaAlRepositorio() {
        Reporte nuevo = new Reporte();
        nuevo.setAutor("Carlos");
        nuevo.setMensaje("Sugerencia");
        when(repo.save(nuevo)).thenReturn(nuevo);
        Reporte result = service.save(nuevo);
        assertSame(nuevo, result);
        verify(repo).save(nuevo);
    }

    @Test
    void delete_cambiaStatusYSeteaFechaEliminacion() {
        when(repo.findById(1L)).thenReturn(Optional.of(activo));
        service.delete(1L);
        assertFalse(activo.getStatus());
        assertNotNull(activo.getDeleted_At());
        verify(repo).save(activo);
    }

    @Test
    void delete_noHaceNadaSiNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        service.delete(99L);
        verify(repo, never()).save(any());
    }

}