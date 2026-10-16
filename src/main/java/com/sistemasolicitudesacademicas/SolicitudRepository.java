package com.sistemasolicitudesacademicas;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository {
    void guardar(SolicitudAcademica solicitud);
    Optional<SolicitudAcademica> buscarPorId(String id);
    List<SolicitudAcademica> obtenerTodas();
}