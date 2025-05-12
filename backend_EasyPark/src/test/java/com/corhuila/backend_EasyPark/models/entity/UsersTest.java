package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Users u = new Users();
        assertNull(u.getCreated_At());
        assertNull(u.getUpdate_At());

        u.onCreate();
        Date c = u.getCreated_At();
        Date uAt = u.getUpdate_At();
        assertNotNull(c);
        assertNotNull(uAt);
        assertEquals(c, uAt);
    }

    @Test
    void onUpdate_advancesUpdateAt() throws InterruptedException {
        Users u = new Users();
        u.onCreate();
        Date before = u.getUpdate_At();
        Thread.sleep(5);
        u.onUpdate();
        Date after = u.getUpdate_At();
        assertTrue(after.after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Users u = new Users();
        assertNull(u.getDeleted_At());
        u.onDelete();
        assertNotNull(u.getDeleted_At());
    }

}