package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EspacioTotalTest {

    @Test
    void onCreate_initializesTimestamps_and_DefaultDisponibles() {
        EspacioTotal e = new EspacioTotal();
        e.setEspacio_total(12);
        // asegurarnos de que 'disponibles' empiece null
        e.setDisponibles(null);

        e.onCreate();
        Date c = e.getCreated_At();
        Date u = e.getUpdate_At();
        assertNotNull(c);
        assertNotNull(u);
        assertEquals(c, u);

        // disponibles se iguala a espacio_total si era null
        assertEquals(12, e.getDisponibles());
    }

    @Test
    void onUpdate_updatesUpdateAt() throws InterruptedException {
        EspacioTotal e = new EspacioTotal();
        e.onCreate();
        Date before = e.getUpdate_At();
        Thread.sleep(5);
        e.onUpdate();
        assertTrue(e.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        EspacioTotal e = new EspacioTotal();
        assertNull(e.getDeleted_At());
        e.onDelete();
        assertNotNull(e.getDeleted_At());
    }

}