package com.sistemasolicitudesacademicas;

public class Main {
    public static void main(String[] args) {
        SolicitudRepository repositorio = new InMemorySolicitudRepository();
        SolicitudService servicio = new SolicitudService(repositorio);
        ReporteService reporte = new ReporteService(repositorio);

        // TODO (Cristian): adaptar SolicitudService para obtener el ID con
        // generarSiguienteId() y persistir cada cambio mediante actualizar().
        // TODO (Facundo): comprobar el recorrido completo cuando se integre el servicio.
        try {
            DatosSolicitud datos = new DatosSolicitud(
                    TipoSolicitud.CERTIFICADO,
                    "Maria Gomez",
                    1,
                    "Constancia de alumno regular"
            );

            SolicitudAcademica solicitud = servicio.registrarSolicitud(datos);
            String id = solicitud.getId();
            System.out.println("Solicitud registrada con ID: " + id);

            servicio.asignarResponsable(id, "Departamento de Alumnos");
            servicio.cambiarEstado(id, EstadoSolicitud.EN_PROCESO);

            // El reporte consulta el repositorio para mostrar los datos actuales.
            reporte.generarReporteConsola();
        } catch (ValidacionSolicitudException | SolicitudNoEncontradaException e) {
            System.out.println("No se pudo completar la ejecución: " + e.getMessage());
        }
    }
}
