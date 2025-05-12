package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PagoReservaTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        PagoReserva pr = new PagoReserva();
        assertNull(pr.getCreated_At());
        assertNull(pr.getUpdate_At());

        pr.onCreate();
        Date c = pr.getCreated_At();
        Date u = pr.getUpdate_At();
        assertNotNull(c);
        assertNotNull(u);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesUpdateAt() throws InterruptedException {
        PagoReserva pr = new PagoReserva();
        pr.onCreate();
        Date before = pr.getUpdate_At();
        Thread.sleep(5);
        pr.onUpdate();
        assertTrue(pr.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        PagoReserva pr = new PagoReserva();
        assertNull(pr.getDeleted_At());
        pr.onDelete();
        assertNotNull(pr.getDeleted_At());
    }

}