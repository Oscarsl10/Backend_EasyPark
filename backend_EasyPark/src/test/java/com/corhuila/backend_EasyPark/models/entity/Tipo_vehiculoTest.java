package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class Tipo_vehiculoTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Tipo_vehiculo tv = new Tipo_vehiculo();
        assertNull(tv.getCreated_At());
        assertNull(tv.getUpdate_At());

        tv.onCreate();
        Date c = tv.getCreated_At(), u = tv.getUpdate_At();
        assertNotNull(c);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesTimestamp() throws InterruptedException {
        Tipo_vehiculo tv = new Tipo_vehiculo();
        tv.onCreate();
        Date before = tv.getUpdate_At();
        Thread.sleep(5);
        tv.onUpdate();
        assertTrue(tv.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Tipo_vehiculo tv = new Tipo_vehiculo();
        assertNull(tv.getDeleted_At());
        tv.onDelete();
        assertNotNull(tv.getDeleted_At());
    }

}