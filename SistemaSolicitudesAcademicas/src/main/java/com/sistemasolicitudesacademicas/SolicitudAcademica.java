package com.sistemasolicitudesacademicas;

public class SolicitudAcademica {
    private String id;
    private TipoSolicitud tipo;
    private String estudiante;
    private int prioridad;
    private String descripcion;
    private EstadoSolicitud estado;
    private String responsable;

    public SolicitudAcademica(TipoSolicitud tipo, String estudiante, int prioridad, String descripcion) {
        this.tipo = tipo;
        this.estudiante = estudiante;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
        this.estado = EstadoSolicitud.values()[0]; // Estado inicial por defecto
    }

    // Métodos de negocio requeridos por el Service
    public void cambiarEstado(EstadoSolicitud nuevoEstado) {
        if (nuevoEstado != null) {
            this.estado = nuevoEstado;
        }
    }

    public void asignarResponsable(String responsable) {
        if (responsable != null && !responsable.trim().isEmpty()) {
            this.responsable = responsable;
        }
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoSolicitud getTipo() {
        return tipo;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}