package com.sistemasolicitudesacademicas;

import java.util.List;

public class SolicitudService {
    private final SolicitudRepository repository;

    public SolicitudService(SolicitudRepository repository) {
        this.repository = repository;
    }

    public SolicitudAcademica registrarSolicitud(DatosSolicitud datos) {
        if (datos == null) {
            throw new ValidacionSolicitudException(
                    "Los datos de la solicitud no pueden ser nulos.");
        }

        String id = repository.generarSiguienteId();

        // El modelo valida los datos de la solicitud.
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

    public SolicitudAcademica obtenerPorId(String id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new SolicitudNoEncontradaException(id));
    }

    public List<SolicitudAcademica> obtenerTodas() {
        return repository.buscarTodas();
    }

    public void cambiarEstado(String id, EstadoSolicitud nuevoEstado) {
        SolicitudAcademica solicitud = obtenerPorId(id);
        solicitud.cambiarEstado(nuevoEstado);

        // La búsqueda devuelve una copia: hay que guardar el cambio.
        repository.actualizar(solicitud);
    }

    public void asignarResponsable(String id, String responsable) {
        SolicitudAcademica solicitud = obtenerPorId(id);
        solicitud.asignarResponsable(responsable);

        // La búsqueda devuelve una copia: hay que guardar el cambio.
        repository.actualizar(solicitud);
    }
}
