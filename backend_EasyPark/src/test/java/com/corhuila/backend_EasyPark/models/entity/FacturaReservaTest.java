package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FacturaReservaTest {

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        FacturaReserva fr = new FacturaReserva();
        assertNull(fr.getCreated_At());
        assertNull(fr.getUpdate_At());

        fr.onCreate();
        Date c = fr.getCreated_At();
        Date u = fr.getUpdate_At();
        assertNotNull(c);
        assertNotNull(u);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesUpdateAt() throws InterruptedException {
        FacturaReserva fr = new FacturaReserva();
        fr.onCreate();
        Date before = fr.getUpdate_At();
        Thread.sleep(5);
        fr.onUpdate();
        assertTrue(fr.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        FacturaReserva fr = new FacturaReserva();
        assertNull(fr.getDeleted_At());
        fr.onDelete();
        assertNotNull(fr.getDeleted_At());
    }

    @Test
    void getTotal_computesWithTax() {
        // stub PagoReserva and Reserva.calcularValorAPagar() = 200.0
        PagoReserva pagoStub = new PagoReserva() {
            @Override
            public Reserva getReserva() {
                return new Reserva() {
                    @Override
                    public double calcularValorAPagar() {
                        return 200.0;
                    }
                };
            }
        };
        FacturaReserva fr = new FacturaReserva();
        fr.setPagoReserva(pagoStub);

        // total = 200 + 200*0.16 = 232
        assertEquals(232.0, fr.getTotal(), 1e-6);
    }

}