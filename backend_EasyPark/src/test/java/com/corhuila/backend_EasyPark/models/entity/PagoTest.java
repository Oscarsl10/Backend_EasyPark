package com.corhuila.backend_EasyPark.models.entity;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class PagoTest {

    private RegistroVehiculo makeRegistro(Date entrada) {
        RegistroVehiculo rv = new RegistroVehiculo();
        rv.setEntrada(entrada);
        return rv;
    }

    private Tarifa makeTarifa(double precio) {
        Tarifa t = new Tarifa();
        t.setPrecio(precio);
        return t;
    }

    @Test
    void onCreate_setsCreatedAndUpdateAt() {
        Pago p = new Pago();
        assertNull(p.getCreated_At());
        assertNull(p.getUpdate_At());

        p.onCreate();
        assertNotNull(p.getCreated_At());
        assertNotNull(p.getUpdate_At());
        assertEquals(p.getCreated_At(), p.getUpdate_At());
    }

    @Test
    void onUpdate_updatesUpdateAt() throws InterruptedException {
        Pago p = new Pago();
        p.onCreate();
        Date before = p.getUpdate_At();
        Thread.sleep(5);
        p.onUpdate();
        assertTrue(p.getUpdate_At().after(before));
    }

    @Test
    void onDelete_setsDeletedAt() {
        Pago p = new Pago();
        assertNull(p.getDeleted_At());
        p.onDelete();
        assertNotNull(p.getDeleted_At());
    }

    @Test
    void calcularValorAPagar_fullDay() {
        // 1 day, tarifa=10 → total = 1*24*10 = 240
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 10, 8, 0);
        Date entrada = cal.getTime();
        cal.set(2025, Calendar.MAY, 11, 8, 0);
        Date salida = cal.getTime();

        Pago p = new Pago();
        p.setRegistroVehiculo(makeRegistro(entrada));
        p.setTarifa(makeTarifa(10.0));
        p.setSalida(salida);

        assertEquals(240.0, p.calcularValorAPagar(), 1e-6);
    }

    @Test
    void calcularValorAPagar_hoursAndMinutes_rounding() {
        // 2 hours 20 min, tarifa=5 →
        // 2h *5 + 0.5h*5 = 10 + 2.5 = 12.5
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 10, 8, 0);
        Date entrada = cal.getTime();
        cal.set(2025, Calendar.MAY, 10, 10, 20);
        Date salida = cal.getTime();

        Pago p = new Pago();
        p.setRegistroVehiculo(makeRegistro(entrada));
        p.setTarifa(makeTarifa(5.0));
        p.setSalida(salida);

        assertEquals(12.5, p.calcularValorAPagar(), 1e-6);
    }

    @Test
    void calcularValorAPagar_minutesAbove30_roundUpHour() {
        // 1h 45m, tarifa=8 → 1h*8 + 1h*8 = 16
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 10, 8, 0);
        Date entrada = cal.getTime();
        cal.set(2025, Calendar.MAY, 10, 9, 45);
        Date salida = cal.getTime();

        Pago p = new Pago();
        p.setRegistroVehiculo(makeRegistro(entrada));
        p.setTarifa(makeTarifa(8.0));
        p.setSalida(salida);

        assertEquals(16.0, p.calcularValorAPagar(), 1e-6);
    }

    @Test
    void getValorAPagar_usesCalcular() {
        Pago spy = spy(new Pago());
        doReturn(123.45).when(spy).calcularValorAPagar();
        assertEquals(123.45, spy.getValorAPagar(), 1e-6);
    }

}