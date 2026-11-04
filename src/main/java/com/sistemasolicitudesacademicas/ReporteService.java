package com.sistemasolicitudesacademicas;

import java.util.List;

public class ReporteService {
    private final SolicitudRepository repository;

    public ReporteService(SolicitudRepository repository) {
        this.repository = repository;
    }

    public void generarReporteConsola() {
        List<SolicitudAcademica> solicitudes = repository.buscarTodas();
        System.out.println("Reporte de " + solicitudes.size() + " solicitudes:");
        for (SolicitudAcademica solicitud : solicitudes) {
            System.out.println("ID: " + solicitud.getId()
                    + " | Tipo: " + solicitud.getTipo()
                    + " | Alumno: " + solicitud.getEstudiante()
                    + " | Estado: " + solicitud.getEstado()
                    + " | Responsable: " + solicitud.getResponsable());
        }
    }
}
