package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TarifaTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Tarifa t = new Tarifa();
        assertNull(t.getCreated_At());
        assertNull(t.getUpdate_At());

        t.onCreate();
        Date c = t.getCreated_At();
        Date u = t.getUpdate_At();
        assertNotNull(c);
        assertNotNull(u);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesUpdateAt() throws InterruptedException {
        Tarifa t = new Tarifa();
        t.onCreate();
        Date before = t.getUpdate_At();
        Thread.sleep(5);
        t.onUpdate();
        assertTrue(t.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Tarifa t = new Tarifa();
        assertNull(t.getDeleted_At());
        t.onDelete();
        assertNotNull(t.getDeleted_At());
    }

}