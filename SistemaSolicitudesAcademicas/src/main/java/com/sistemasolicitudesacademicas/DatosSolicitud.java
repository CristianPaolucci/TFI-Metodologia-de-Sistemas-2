package com.sistemasolicitudesacademicas;

public class DatosSolicitud {
    private final TipoSolicitud tipo;
    private final String estudiante;
    private final int prioridad;
    private final String descripcion;

    public DatosSolicitud(TipoSolicitud tipo, String estudiante, int prioridad, String descripcion) {
        if (tipo == null) {
            throw new ValidacionSolicitudException("El tipo de solicitud no puede ser nulo.");
        }
        if (estudiante == null || estudiante.trim().isEmpty()) {
            throw new ValidacionSolicitudException("El estudiante no puede ser nulo o vacío.");
        }
        if (prioridad < 1 || prioridad > 3) {
            throw new ValidacionSolicitudException("La prioridad debe estar entre 1 y 3.");
        }
        this.tipo = tipo;
        this.estudiante = estudiante;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
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
}