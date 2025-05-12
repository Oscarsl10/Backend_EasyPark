package com.corhuila.backend_EasyPark.models.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calificacion")
    private String calificacion;

    @Column(name = "comentario", length = 1000)
    private String comentario;

    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

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
        fecha = now;
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

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
