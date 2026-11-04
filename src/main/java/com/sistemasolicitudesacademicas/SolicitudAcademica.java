package com.sistemasolicitudesacademicas;

/**
 * Modelo de una solicitud académica.
 * Reemplaza la representación basada en arreglos String[] por un objeto con atributos tipados.
 */
public class SolicitudAcademica {

    public static final int PRIORIDAD_MINIMA = 1;
    public static final int PRIORIDAD_MAXIMA = 3;
    public static final String RESPONSABLE_INICIAL = "Sin asignar";

    private final String id;
    private final TipoSolicitud tipo;
    private final String estudiante;
    private final int prioridad;
    private final String descripcion;
    private EstadoSolicitud estado;
    private String responsable;

    public SolicitudAcademica(String id, TipoSolicitud tipo, String estudiante,
                              int prioridad, String descripcion) {
        this(validarTexto(id, "El identificador"),
                validarTipo(tipo),
                validarTexto(estudiante, "El estudiante"),
                validarPrioridad(prioridad),
                validarTexto(descripcion, "La descripción"),
                EstadoSolicitud.PENDIENTE,
                RESPONSABLE_INICIAL);
    }

    private SolicitudAcademica(String id, TipoSolicitud tipo, String estudiante,
                               int prioridad, String descripcion,
                               EstadoSolicitud estado, String responsable) {
        this.id = id;
        this.tipo = tipo;
        this.estudiante = estudiante;
        this.prioridad = prioridad;
        this.descripcion = descripcion;
        this.estado = estado;
        this.responsable = responsable;
    }

    public SolicitudAcademica copiar() {
        return new SolicitudAcademica(id, tipo, estudiante, prioridad,
                descripcion, estado, responsable);
    }

    public void cambiarEstado(EstadoSolicitud nuevoEstado) {
        if (nuevoEstado == null) {
            throw new ValidacionSolicitudException("El nuevo estado no puede ser nulo.");
        }
        this.estado = nuevoEstado;
    }

    public void asignarResponsable(String responsable) {
        if (responsable == null || responsable.trim().isEmpty()) {
            throw new ValidacionSolicitudException("El responsable asignado no es válido.");
        }
        this.responsable = responsable;
    }

    public String getId() { return id; }
    public TipoSolicitud getTipo() { return tipo; }
    public String getEstudiante() { return estudiante; }
    public int getPrioridad() { return prioridad; }
    public String getDescripcion() { return descripcion; }
    public EstadoSolicitud getEstado() { return estado; }
    public String getResponsable() { return responsable; }

    @Override
    public String toString() {
        return "SolicitudAcademica{id=" + id + ", tipo=" + tipo
                + ", estudiante=" + estudiante + ", prioridad=" + prioridad
                + ", descripcion=" + descripcion + ", estado=" + estado
                + ", responsable=" + responsable + "}";
    }

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionSolicitudException(campo + " no puede ser nulo ni vacío.");
        }
        return valor;
    }

    private static TipoSolicitud validarTipo(TipoSolicitud tipo) {
        if (tipo == null) {
            throw new ValidacionSolicitudException("El tipo de solicitud no puede ser nulo.");
        }
        return tipo;
    }

    private static int validarPrioridad(int prioridad) {
        if (prioridad < PRIORIDAD_MINIMA || prioridad > PRIORIDAD_MAXIMA) {
            throw new ValidacionSolicitudException(
                    "La prioridad no es válida. Debe estar entre 1 y 3.");
        }
        return prioridad;
    }
}