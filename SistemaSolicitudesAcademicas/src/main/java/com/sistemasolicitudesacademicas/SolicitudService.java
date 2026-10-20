package com.sistemasolicitudesacademicas;

import java.util.List;

public class SolicitudService {
    private final SolicitudRepository repository;

    public SolicitudService(SolicitudRepository repository) {
        this.repository = repository;
    }

    public SolicitudAcademica registrarSolicitud(DatosSolicitud datos) {
        if (datos == null) {
            throw new ValidacionSolicitudException("Los datos de la solicitud no pueden ser nulos.");
        }

        // Se pasan exactamente los 4 argumentos requeridos por el constructor
        SolicitudAcademica nuevaSolicitud = new SolicitudAcademica(
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
        return repository.obtenerTodas();
    }

    public void cambiarEstado(String id, EstadoSolicitud nuevoEstado) {
        if (nuevoEstado == null) {
            throw new ValidacionSolicitudException("El nuevo estado no puede ser nulo.");
        }
        SolicitudAcademica solicitud = obtenerPorId(id);
        solicitud.cambiarEstado(nuevoEstado);
        repository.guardar(solicitud);
    }

    public void asignarResponsable(String id, String responsable) {
        if (responsable == null || responsable.trim().isEmpty()) {
            throw new ValidacionSolicitudException("El responsable asignado no es válido.");
        }
        SolicitudAcademica solicitud = obtenerPorId(id);
        solicitud.asignarResponsable(responsable);
        repository.guardar(solicitud);
    }
}