package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void onCreate_setsCreatedAtAndUpdateAt() {
        Admin admin = new Admin();
        assertNull(admin.getCreated_At());
        assertNull(admin.getUpdate_At());

        admin.onCreate();
        Date created = admin.getCreated_At();
        Date updated = admin.getUpdate_At();

        assertNotNull(created);
        assertNotNull(updated);
        assertEquals(created, updated);
    }

    @Test
    void onUpdate_updatesTimestamp() throws InterruptedException {
        Admin admin = new Admin();
        admin.onCreate();
        Date before = admin.getUpdate_At();

        Thread.sleep(10); // asegurar diferencia
        admin.onUpdate();
        Date after = admin.getUpdate_At();

        assertNotNull(after);
        assertTrue(after.after(before));
    }

    @Test
    void onDelete_setsDeletedAt() throws InterruptedException {
        Admin admin = new Admin();
        assertNull(admin.getDeleted_At());

        admin.onDelete();
        Date deleted = admin.getDeleted_At();
        assertNotNull(deleted);
    }

}