package com.sistemasolicitudesacademicas;

public class DatosSolicitud {
    private final TipoSolicitud tipo;
    private final String estudiante;
    private final int prioridad;
    private final String descripcion;

    public DatosSolicitud(TipoSolicitud tipo, String estudiante, int prioridad, String descripcion) {
        this.tipo = tipo;
        this.estudiante = estudiante;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
    }

    public TipoSolicitud getTipo() { return tipo; }
    public String getEstudiante() { return estudiante; }
    public int getPrioridad() { return prioridad; }
    public String getDescripcion() { return descripcion; }
}