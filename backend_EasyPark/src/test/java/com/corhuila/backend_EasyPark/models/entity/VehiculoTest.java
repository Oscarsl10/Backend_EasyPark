package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class VehiculoTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Vehiculo v = new Vehiculo();
        assertNull(v.getCreated_At());
        assertNull(v.getUpdate_At());

        v.onCreate();
        Date created = v.getCreated_At();
        Date updated = v.getUpdate_At();
        assertNotNull(created);
        assertNotNull(updated);
        assertEquals(created, updated);
    }

    @Test
    void onUpdate_updatesUpdateAt() throws InterruptedException {
        Vehiculo v = new Vehiculo();
        v.onCreate();
        Date before = v.getUpdate_At();
        Thread.sleep(5);
        v.onUpdate();
        assertTrue(v.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Vehiculo v = new Vehiculo();
        assertNull(v.getDeleted_At());
        v.onDelete();
        assertNotNull(v.getDeleted_At());
    }

}