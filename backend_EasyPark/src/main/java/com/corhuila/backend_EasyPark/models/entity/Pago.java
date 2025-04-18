package com.corhuila.backend_EasyPark.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "Salida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "America/Bogota")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;

    @Column(name = "Valor a Pagar")
    private Double valorAPagar;
    @Column(name = "status")
    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "registro_vehiculo_id", nullable = false)
    private RegistroVehiculo registroVehiculo;

    @ManyToOne
    @JoinColumn(name = "tarifa_id", nullable = false)
    private Tarifa tarifa;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_At")
    private Date created_At;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_At")
    private Date update_At;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_At")
    private Date deleted_At;

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        created_At = now;
        update_At = now;
    }

    @PreUpdate
    protected void onUpdate() {
        update_At = new Date();
    }

    @PreRemove
    protected void onDelete() {
        deleted_At = new Date();
    }


    public Double getValorAPagar() {
        return valorAPagar = calcularValorAPagar();
    }

    public void setValorAPagar(Double valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public double calcularValorAPagar() {
        if (registroVehiculo.getEntrada() != null && salida != null && tarifa != null) {
            long diffInMillis = salida.getTime() - registroVehiculo.getEntrada().getTime();
            long totalMinutes = diffInMillis / (1000 * 60);
            long totalHours = totalMinutes / 60;
            long remainingMinutes = totalMinutes % 60;
            long totalDays = totalHours / 24;
            long remainingHours = totalHours % 24;

            double precioBase = tarifa.getPrecio();
            double totalPagar = 0.0;

            // Calcular días completos
            totalPagar += totalDays * precioBase * 24;

            // Fracción de día
            if (remainingHours > 0 || remainingMinutes > 0) {
                double horasFinales = remainingHours;

                if (remainingMinutes > 30) {
                    horasFinales += 1; // Redondear hacia arriba
                } else if (remainingMinutes > 0) {
                    horasFinales += 0.5; // Fracción menor o igual a 30min
                }

                totalPagar += horasFinales * precioBase;
            }

            return totalPagar;
        }
        return 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public RegistroVehiculo getRegistroVehiculo() {
        return registroVehiculo;
    }

    public void setRegistroVehiculo(RegistroVehiculo registroVehiculo) {
        this.registroVehiculo = registroVehiculo;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    public Date getDeleted_At() {
        return deleted_At;
    }

    public void setDeleted_At(Date deleted_At) {
        this.deleted_At = deleted_At;
    }
}
