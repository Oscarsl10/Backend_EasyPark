package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CalificacionTest {

    @Test
    void onCreate_initializesTimestampsAndFecha() {
        Calificacion cal = new Calificacion();
        assertNull(cal.getCreated_At());
        assertNull(cal.getUpdate_At());
        assertNull(cal.getFecha());

        cal.onCreate();
        Date created = cal.getCreated_At();
        Date updated = cal.getUpdate_At();
        Date fecha   = cal.getFecha();

        assertNotNull(created);
        assertNotNull(updated);
        assertNotNull(fecha);
        assertEquals(created, updated);
        assertEquals(created, fecha);
    }

    @Test
    void onUpdate_advancesUpdateAt() throws InterruptedException {
        Calificacion cal = new Calificacion();
        cal.onCreate();
        Date before = cal.getUpdate_At();

        Thread.sleep(5);
        cal.onUpdate();
        Date after = cal.getUpdate_At();

        assertTrue(after.after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Calificacion cal = new Calificacion();
        assertNull(cal.getDeleted_At());

        cal.onDelete();
        assertNotNull(cal.getDeleted_At());
    }

}