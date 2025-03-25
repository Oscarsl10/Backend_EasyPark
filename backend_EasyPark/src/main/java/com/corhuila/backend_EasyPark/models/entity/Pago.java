package com.corhuila.backend_EasyPark.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrada;

    @Column(name = "Salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;

    @Column(name = "Valor a Pagar")
    private Double valorAPagar;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "tarifa_id", nullable = false)
    private Tarifa tarifa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public Double getValorAPagar() {
        return valorAPagar = calcularValorAPagar();
    }

    public double calcularValorAPagar() {
        if (entrada != null && salida != null && tarifa != null) {
            long diffInMillies = salida.getTime() - entrada.getTime();
            double diffInHours = (double) diffInMillies / (1000 * 60 * 60); // Convertir a horas

            diffInHours = Math.ceil(diffInHours); // Cobrar al menos 1 hora

            return diffInHours * tarifa.getPrecio();
        }
        return 0.0; // Si falta información, retorna 0
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }
}
