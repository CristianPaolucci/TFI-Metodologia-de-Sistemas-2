package com.sistemasolicitudesacademicas;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println(" PRUEBAS DE SERVICIO Y EXCEPCIONES - PAOLUCCI");
        System.out.println("=========================================\n");

        // 1. Inicializar repositorio y servicio
        SolicitudRepository repository = new InMemorySolicitudRepository();
        SolicitudService service = new SolicitudService(repository);

        try {
            // 2. Registro exitoso utilizando el Parameter Object (DatosSolicitud)
            DatosSolicitud datos = new DatosSolicitud(
                    TipoSolicitud.CERTIFICADO,
                    "Maria Gomez",
                    1,
                    "Constancia de alumno regular"
            );

            SolicitudAcademica nueva = service.registrarSolicitud(datos);
            System.out.println("Éxito al registrar solicitud ID: " + nueva.getId()
                    + " | Alumno: " + nueva.getEstudiante()
                    + " | Estado: " + nueva.getEstado());

            // 3. Probar asignación de responsable
            service.asignarResponsable(nueva.getId(), "Departamento de Alumnos");
            System.out.println("Responsable asignado correctamente.");

            // 4. Probar cambio de estado
            service.cambiarEstado(nueva.getId(), EstadoSolicitud.EN_PROCESO);
            System.out.println("Estado actualizado a: " + nueva.getEstado());

            // 5. Verificar control de excepciones (Prioridad inválida)
            System.out.println("\nProbando validación de prioridad inválida (debe lanzar excepción):");
            DatosSolicitud datosInvalidos = new DatosSolicitud(
                    TipoSolicitud.PASE,
                    "Juan Perez",
                    5, // Prioridad inválida (> 3)
                    "Pase de facultad"
            );
            service.registrarSolicitud(datosInvalidos);

        } catch (ValidacionSolicitudException | SolicitudNoEncontradaException e) {
            System.out.println("[Excepción controlada capturada]: " + e.getMessage());
        }

        System.out.println("\n=========================================");
        System.out.println(" VERIFICACIÓN FINALIZADA CON ÉXITO");
        System.out.println("=========================================");
    }
}