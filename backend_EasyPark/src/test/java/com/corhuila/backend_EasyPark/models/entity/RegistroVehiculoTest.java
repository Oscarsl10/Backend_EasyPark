package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RegistroVehiculoTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        RegistroVehiculo r = new RegistroVehiculo();
        assertNull(r.getCreated_At());
        assertNull(r.getUpdate_At());

        r.onCreate();
        Date c = r.getCreated_At();
        Date u = r.getUpdate_At();
        assertNotNull(c);
        assertNotNull(u);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesUpdateAt() throws InterruptedException {
        RegistroVehiculo r = new RegistroVehiculo();
        r.onCreate();
        Date before = r.getUpdate_At();
        Thread.sleep(5);
        r.onUpdate();
        assertTrue(r.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        RegistroVehiculo r = new RegistroVehiculo();
        assertNull(r.getDeleted_At());
        r.onDelete();
        assertNotNull(r.getDeleted_At());
    }

}