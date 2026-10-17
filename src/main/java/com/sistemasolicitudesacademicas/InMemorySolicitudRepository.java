package com.sistemasolicitudesacademicas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemorySolicitudRepository implements SolicitudRepository {

    private final List<SolicitudAcademica> solicitudes = new ArrayList<>();
    private final AtomicInteger secuenciadorId = new AtomicInteger(1);

    @Override
    public String generarSiguienteId() {
        return String.valueOf(secuenciadorId.getAndIncrement());
    }

    @Override
    public void guardar(SolicitudAcademica solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("No se puede guardar una solicitud nula");
        }
        // Protección de datos al guardar: se almacena una copia
        solicitudes.add(solicitud.copiar());
    }

    @Override
    public Optional<SolicitudAcademica> buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        // Centralización de la búsqueda por ID y retorno de copia defensiva
        return solicitudes.stream()
                .filter(sol -> sol.getId().equals(id))
                .findFirst()
                .map(SolicitudAcademica::copiar);
    }

    @Override
    public void actualizar(SolicitudAcademica solicitud) {
        if (solicitud == null || solicitud.getId() == null) {
            throw new IllegalArgumentException("Solicitud o ID no válidos para actualización");
        }
        
        for (int i = 0; i < solicitudes.size(); i++) {
            if (solicitudes.get(i).getId().equals(solicitud.getId())) {
                // Actualización explícita reemplazando con una copia defensiva
                solicitudes.set(i, solicitud.copiar());
                return;
            }
        }
        throw new SolicitudNoEncontradaException(solicitud.getId());
    }

    @Override
    public List<SolicitudAcademica> buscarTodas() {
        // Retorna lista inmodificable compuesta por copias defensivas de los objetos
        return solicitudes.stream()
                .map(SolicitudAcademica::copiar)
                .collect(Collectors.toUnmodifiableList());
    }
}
