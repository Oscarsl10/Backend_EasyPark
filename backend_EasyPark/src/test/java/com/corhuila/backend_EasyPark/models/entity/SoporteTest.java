package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SoporteTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Soporte s = new Soporte();
        assertNull(s.getCreated_At());
        assertNull(s.getUpdate_At());

        s.onCreate();
        Date c = s.getCreated_At(), u = s.getUpdate_At();
        assertNotNull(c);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesTimestamp() throws InterruptedException {
        Soporte s = new Soporte();
        s.onCreate();
        Date before = s.getUpdate_At();
        Thread.sleep(5);
        s.onUpdate();
        assertTrue(s.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Soporte s = new Soporte();
        assertNull(s.getDeleted_At());
        s.onDelete();
        assertNotNull(s.getDeleted_At());
    }

}