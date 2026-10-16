package com.sistemasolicitudesacademicas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemorySolicitudRepository implements SolicitudRepository {
    private final List<SolicitudAcademica> solicitudes = new ArrayList<>();

    @Override
    public void guardar(SolicitudAcademica solicitud) {
        solicitudes.add(solicitud);
    }

    @Override
    public Optional<SolicitudAcademica> buscarPorId(String id) {
        return solicitudes.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public List<SolicitudAcademica> obtenerTodas() {
        return Collections.unmodifiableList(new ArrayList<>(solicitudes));
    }
}