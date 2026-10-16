package com.sistemasolicitudesacademicas;

public class SolicitudAcademica {
    private final String id;
    private final TipoSolicitud tipo;
    private final String estudiante;
    private final int prioridad;
    private final String descripcion;
    private EstadoSolicitud estado;
    private String responsable;

    public SolicitudAcademica(String id, TipoSolicitud tipo, String estudiante, int prioridad, String descripcion) {
        if (estudiante == null || estudiante.trim().isEmpty()) {
            throw new ValidacionSolicitudException("Estudiante inválido");
        }
        this.id = id;
        this.tipo = tipo;
        this.estudiante = estudiante;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.responsable = "Sin asignar";
    }

    public void cambiarEstado(EstadoSolicitud nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void asignarResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getId() { return id; }
    public TipoSolicitud getTipo() { return tipo; }
    public String getEstudiante() { return estudiante; }
    public int getPrioridad() { return prioridad; }
    public String getDescripcion() { return descripcion; }
    public EstadoSolicitud getEstado() { return estado; }
    public String getResponsable() { return responsable; }
}