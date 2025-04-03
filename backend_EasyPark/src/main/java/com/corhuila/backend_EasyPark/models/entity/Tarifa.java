package com.corhuila.backend_EasyPark.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tarifa")
public class Tarifa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = (100), nullable = false)
    private Double precio;
    @Column(length = (100), nullable = false)
    private String fecha;
    @Column(length = (100), nullable = false)
    private String tipoVehiculo;
    @Column(length = 350)
    private String descripcion;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String nit) {
        this.tipoVehiculo = nit;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
