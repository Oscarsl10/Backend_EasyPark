package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class ReservaTest {

    private Tarifa makeTarifa(double precio) {
        Tarifa t = new Tarifa();
        t.setPrecio(precio);
        return t;
    }

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Reserva r = new Reserva();
        assertNull(r.getCreated_At());
        assertNull(r.getUpdate_At());
        r.onCreate();
        Date c = r.getCreated_At(), u = r.getUpdate_At();
        assertNotNull(c);
        assertEquals(c, u);
    }

    @Test
    void onUpdate_updatesTimestamp() throws InterruptedException {
        Reserva r = new Reserva();
        r.onCreate();
        Date before = r.getUpdate_At();
        Thread.sleep(5);
        r.onUpdate();
        assertTrue(r.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Reserva r = new Reserva();
        assertNull(r.getDeleted_At());
        r.onDelete();
        assertNotNull(r.getDeleted_At());
    }

    @Test
    void calcularValorAPagar_fullDayAndPartial() {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 1, 8, 0);
        Date inicio = cal.getTime();
        cal.set(2025, Calendar.MAY, 2, 10, 45);
        Date fin = cal.getTime();

        Reserva r = new Reserva();
        r.setFechaInicio(inicio);
        r.setFechaFin(fin);
        r.setTarifa(makeTarifa(10.0));

        // 1 day (24h*10=240) + 2h45 => 2h*10 + 1h*10 = 20+10 =30 => total 270
        assertEquals(270.0, r.calcularValorAPagar(), 1e-6);
    }

    @Test
    void getPrecio_usesCalcular() {
        Reserva spy = spy(new Reserva());
        doReturn(123.0).when(spy).calcularValorAPagar();
        assertEquals(123.0, spy.getPrecio(), 1e-6);
    }

}