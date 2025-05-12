package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FacturaTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Factura f = new Factura();
        assertNull(f.getCreated_At());
        assertNull(f.getUpdate_At());

        f.onCreate();
        Date c = f.getCreated_At();
        Date u = f.getUpdate_At();
        assertNotNull(c);
        assertNotNull(u);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesUpdateAt() throws InterruptedException {
        Factura f = new Factura();
        f.onCreate();
        Date before = f.getUpdate_At();
        Thread.sleep(5);
        f.onUpdate();
        assertTrue(f.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Factura f = new Factura();
        assertNull(f.getDeleted_At());
        f.onDelete();
        assertNotNull(f.getDeleted_At());
    }

    @Test
    void getTotal_computesWithTax() {
        // Creamos un Pago stub con calcularValorAPagar() = 100.0
        Pago pagoStub = new Pago() {
            @Override
            public double calcularValorAPagar() {
                return 100.0;
            }
        };
        Factura f = new Factura();
        f.setPago(pagoStub);

        // total = 100 + 100*0.16 = 116
        assertEquals(116.0, f.getTotal(), 0.0001);
    }

}