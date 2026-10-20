package com.sistemasolicitudesacademicas;

public class SolicitudService {
    private final SolicitudRepository repository;

    public SolicitudService(SolicitudRepository repository) {
        this.repository = repository;
    }

    public SolicitudAcademica registrarSolicitud(DatosSolicitud datos) {
        if (datos.getPrioridad() < 1 || datos.getPrioridad() > 3) {
            throw new ValidacionSolicitudException("La prioridad no es válida. Debe estar entre 1 y 3.");
        }

        String id = String.valueOf(repository.obtenerTodas().size() + 1);

        SolicitudAcademica nuevaSolicitud = new SolicitudAcademica(
                id,
                datos.getTipo(),
                datos.getEstudiante(),
                datos.getPrioridad(),
                datos.getDescripcion()
        );

        repository.guardar(nuevaSolicitud);
        return nuevaSolicitud;
    }

    public void cambiarEstado(String id, EstadoSolicitud nuevoEstado) {
        SolicitudAcademica solicitud = repository.buscarPorId(id)
                .orElseThrow(() -> new SolicitudNoEncontradaException(id));

        if (nuevoEstado == null) {
            throw new ValidacionSolicitudException("El nuevo estado no puede ser nulo.");
        }

        solicitud.cambiarEstado(nuevoEstado);
    }

    public void asignarResponsable(String id, String responsable) {
        if (responsable == null || responsable.trim().isEmpty()) {
            throw new ValidacionSolicitudException("El responsable asignado no es válido.");
        }

        SolicitudAcademica solicitud = repository.buscarPorId(id)
                .orElseThrow(() -> new SolicitudNoEncontradaException(id));

        solicitud.asignarResponsable(responsable);
    }
}