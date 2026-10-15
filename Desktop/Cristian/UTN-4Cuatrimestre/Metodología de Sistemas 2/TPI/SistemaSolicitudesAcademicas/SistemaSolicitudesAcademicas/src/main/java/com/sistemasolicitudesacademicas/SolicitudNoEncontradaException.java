package com.sistemasolicitudesacademicas;

public class SolicitudNoEncontradaException extends RuntimeException {
    public SolicitudNoEncontradaException(String id) {
        super("Error: No se encontró la solicitud con ID: " + id);
    }
}
