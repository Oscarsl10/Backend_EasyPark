package com.corhuila.backend_EasyPark.models.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "soporte")
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "mensaje", length = 2000)
    private String mensaje;

    @Column(name = "fecha")
    private LocalDateTime fecha;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
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
}
