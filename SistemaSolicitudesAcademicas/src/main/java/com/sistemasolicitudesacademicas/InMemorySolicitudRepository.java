package com.sistemasolicitudesacademicas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemorySolicitudRepository implements SolicitudRepository {
    private final List<SolicitudAcademica> solicitudes = new ArrayList<>();

    @Override
    public void guardar(SolicitudAcademica solicitud) {
        // Asignar un ID automático si la solicitud todavía no lo tiene
        if (solicitud.getId() == null) {
            solicitud.setId(UUID.randomUUID().toString());
        }
        solicitudes.add(solicitud);
    }

    @Override
    public Optional<SolicitudAcademica> buscarPorId(String id) {
        return solicitudes.stream()
                .filter(s -> s.getId() != null && s.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<SolicitudAcademica> obtenerTodas() {
        return Collections.unmodifiableList(new ArrayList<>(solicitudes));
    }
}