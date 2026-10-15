import java.util.ArrayList;
import java.util.List;

public class MainSistemaAcademico {
    public static class GestorAcademicoMonolitico {
        private List<String[]> solicitudesDb = new ArrayList<>();

        public String registrarSolicitud(String tipo, String estudiante, int prioridad, String descripcion) {
            if (estudiante == null || estudiante.trim().isEmpty()) {
                return "Error: Estudiante inválido";
            }
            if (!tipo.equals("Certificado") && !tipo.equals("Pase") && !tipo.equals("Equivalencia")) {
                return "Error: Tipo de solicitud no válido";
            }
            if (prioridad < 1 || prioridad > 3) {
                return "Error: Prioridad no válida";
            }
            String[] nuevaSolicitud = {
                String.valueOf(solicitudesDb.size() + 1),
                tipo,
                estudiante,
                String.valueOf(prioridad),
                descripcion,
                "Pendiente",
                "Sin asignar"
            };
            solicitudesDb.add(nuevaSolicitud);
            System.out.println("[Monolítico] Solicitud registrada para el alumno: " + estudiante);
            return "Éxito ID: " + nuevaSolicitud[0];
        }

        public String cambiarEstado(String idStr, String nuevoEstado) {
            for (String[] sol : solicitudesDb) {
                if (sol[0].equals(idStr)) {
                    sol[5] = nuevoEstado;
                    System.out.println("[Monolítico] Solicitud ID " + idStr + " actualizada al estado: " + nuevoEstado);
                    return "Éxito: Estado actualizado";
                }
            }
            return "Error: Solicitud no encontrada";
        }

        public String asignarResponsable(String idStr, String responsable) {
            for (String[] sol : solicitudesDb) {
                if (sol[0].equals(idStr)) {
                    sol[6] = responsable;
                    System.out.println("[Monolítico] Responsable " + responsable + " asignado a la solicitud ID " + idStr);
                    return "Éxito: Responsable asignado";
                }
            }
            return "Error: Solicitud no encontrada";
        }

        public void generarReportePDF() {
            System.out.println("[Monolítico] Generando reporte PDF de " + solicitudesDb.size() + " solicitudes directamente...");
            for (String[] sol : solicitudesDb) {
                System.out.println("ID: " + sol[0] + " | Tipo: " + sol[1] + " | Alumno: " + sol[2] + " | Estado: " + sol[5] + " | Responsable: " + sol[6]);
            }
        }

        public List<String[]> getSolicitudesDb() {
            return solicitudesDb;
        }
    }

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println(" EJECUCIÓN DEL SISTEMA BASE (ESTADO INICIAL)");
        System.out.println("==========================================\n");

        GestorAcademicoMonolitico sistemaMonolitico = new GestorAcademicoMonolitico();
        String resultado = sistemaMonolitico.registrarSolicitud("Certificado", "Maria Gomez", 1, "Constancia de alumno regular");
        System.out.println("Resultado de operación: " + resultado);

        sistemaMonolitico.asignarResponsable("1", "Departamento de Alumnos");
        sistemaMonolitico.cambiarEstado("1", "En Proceso");
        sistemaMonolitico.generarReportePDF();
    }
}
