package com.corhuila.backend_EasyPark.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "INTEGER DEFAULT 1")
    private Integer espacios = 1; // Número de espacios reservados

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "America/Bogota")
    @Column(nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "America/Bogota")
    @Column(nullable = false)
    private Date fechaFin;

    @Column(name = "precio")
    private Double precio; // Costo total de la reserva

    @Column(name = "confirmado")
    private Boolean confirmado = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "espacio_total_id", nullable = false)
    private EspacioTotal espacio_total;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "tarifa_id", nullable = false)
    private Tarifa tarifa;

    @ManyToOne
    @JoinColumn(name = "pago_id")
    private Pago pago;

    @Column(name = "status")
    private Boolean status = true;

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


    public Double getPrecio() {
        return precio = calcularValorAPagar();
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEspacios() {
        return espacios;
    }

    public void setEspacios(Integer espacios) {
        this.espacios = espacios;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public EspacioTotal getEspacio_total() {
        return espacio_total;
    }

    public void setEspacio_total(EspacioTotal espacio_total) {
        this.espacio_total = espacio_total;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public double calcularValorAPagar() {
        if (fechaInicio != null && fechaFin != null && tarifa != null && tarifa.getPrecio() != null) {
            long diffInMillis = fechaFin.getTime() - fechaInicio.getTime();
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
                    horasFinales += 1;
                } else if (remainingMinutes > 0) {
                    horasFinales += 0.5;
                }

                totalPagar += horasFinales * precioBase;
            }

            return totalPagar;
        }
        return 0.0;
    }
}
