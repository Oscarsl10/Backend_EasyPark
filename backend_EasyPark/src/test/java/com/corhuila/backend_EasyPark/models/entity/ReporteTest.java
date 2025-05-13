package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReporteTest {

    @Test
    void onCreate_debeInicializarFechas() {
        Reporte r = new Reporte();
        assertNull(r.getCreated_At());
        assertNull(r.getUpdate_At());
        assertNull(r.getFecha());

        r.onCreate();
        assertNotNull(r.getCreated_At());
        assertNotNull(r.getUpdate_At());
        assertNotNull(r.getFecha());
        assertEquals(r.getCreated_At(), r.getUpdate_At());
    }

    @Test
    void onUpdate_debeActualizarUpdateAt() throws InterruptedException {
        Reporte r = new Reporte();
        r.onCreate();
        Date antes = r.getUpdate_At();
        Thread.sleep(10);
        r.onUpdate();
        assertTrue(r.getUpdate_At().after(antes));
    }

    @Test
    void onDelete_debeSetearDeletedAt() {
        Reporte r = new Reporte();
        assertNull(r.getDeleted_At());
        r.onDelete();
        assertNotNull(r.getDeleted_At());
    }
}