package com.sistemasolicitudesacademicas;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository {
    String generarSiguienteId();
    void guardar(SolicitudAcademica solicitud);
    Optional<SolicitudAcademica> buscarPorId(String id);
    void actualizar(SolicitudAcademica solicitud);
    List<SolicitudAcademica> buscarTodas();
}
