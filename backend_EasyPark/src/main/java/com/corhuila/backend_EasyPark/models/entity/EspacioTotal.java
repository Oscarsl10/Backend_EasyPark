package com.corhuila.backend_EasyPark.models.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "espacio_total")
public class EspacioTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer espacio_total;

    @Column(name = "descripcion", nullable = false, length = 250)
    private String descripcion;

    @Column(name = "disponibles")
    private Integer disponibles;

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

        if (disponibles == null) {
            disponibles = espacio_total;
        }
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

    public Integer getEspacio_total() {
        return espacio_total;
    }

    public void setEspacio_total(Integer espacio_total) {
        this.espacio_total = espacio_total;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getDeleted_At() {
        return deleted_At;
    }

    public void setDeleted_At(Date deleted_At) {
        this.deleted_At = deleted_At;
    }

    public Integer getDisponibles() {
        return disponibles != null ? disponibles : 0;
    }

    public void setDisponibles(Integer disponibles) {
        this.disponibles = disponibles;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
