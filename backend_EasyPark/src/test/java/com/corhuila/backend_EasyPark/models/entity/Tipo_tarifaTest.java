package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class Tipo_tarifaTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Tipo_tarifa tt = new Tipo_tarifa();
        assertNull(tt.getCreated_At());
        assertNull(tt.getUpdate_At());

        tt.onCreate();
        Date c = tt.getCreated_At();
        Date u = tt.getUpdate_At();
        assertNotNull(c);
        assertNotNull(u);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesTimestamp() throws InterruptedException {
        Tipo_tarifa tt = new Tipo_tarifa();
        tt.onCreate();
        Date before = tt.getUpdate_At();
        Thread.sleep(5);
        tt.onUpdate();
        assertTrue(tt.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Tipo_tarifa tt = new Tipo_tarifa();
        assertNull(tt.getDeleted_At());
        tt.onDelete();
        assertNotNull(tt.getDeleted_At());
    }

}