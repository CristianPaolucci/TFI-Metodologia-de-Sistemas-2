package com.sistemasolicitudesacademicas;

import java.util.List;
import java.util.Optional;

public class VerificacionSolicitudRepository {

    public static void main(String[] args) {
        System.out.println("=== INICIO DE VERIFICACIONES DEL REPOSITORIO ===");

        SolicitudRepository repo = new InMemorySolicitudRepository();

        // 1. Verificación de Generación de ID
        String id1 = repo.generarSiguienteId();
        String id2 = repo.generarSiguienteId();
        assert id1.equals("1") : "Error en generación de ID 1";
        assert id2.equals("2") : "Error en generación de ID 2";
        System.out.println("[OK] Generación de identificadores secuenciales correcta.");

        // 2. Guardado y Búsqueda por ID (existente e inexistente)
        SolicitudAcademica solicitud1 = new SolicitudAcademica(id1, TipoSolicitud.CERTIFICADO, "Maria Gomez", 1, "Constancia alumno regular");
        repo.guardar(solicitud1);

        Optional<SolicitudAcademica> encontrada = repo.buscarPorId("1");
        assert encontrada.isPresent() : "Debería encontrar la solicitud ID 1";
        assert encontrada.get().getEstudiante().equals("Maria Gomez") : "Error en datos guardados";

        Optional<SolicitudAcademica> noEncontrada = repo.buscarPorId("99");
        assert noEncontrada.isEmpty() : "No debería encontrar una solicitud inexistente";
        System.out.println("[OK] Búsqueda por ID existente e inexistente verificada.");

        // 3. Verificación de Actualización Explícita
        SolicitudAcademica aModificar = encontrada.get();
        aModificar.cambiarEstado(EstadoSolicitud.EN_PROCESO);
        aModificar.asignarResponsable("Atención Alumnos");
        repo.actualizar(aModificar);

        Optional<SolicitudAcademica> actualizada = repo.buscarPorId("1");
        assert actualizada.get().getEstado() == EstadoSolicitud.EN_PROCESO : "Error en actualización de estado";
        assert actualizada.get().getResponsable().equals("Atención Alumnos") : "Error en actualización de responsable";
        System.out.println("[OK] Actualización explícita de solicitud verificada.");

        // 4. Verificación de Protección del Estado Interno (Copias Defensivas)
        SolicitudAcademica obtenida = repo.buscarPorId("1").get();
        obtenida.asignarResponsable("Hack Externe"); // Intento de modificación externa directa
        
        Optional<SolicitudAcademica> sinModificar = repo.buscarPorId("1");
        assert sinModificar.get().getResponsable().equals("Atención Alumnos") : "Error: El repositorio permitió la mutación directa del objeto almacenado";

        List<SolicitudAcademica> listaTodas = repo.buscarTodas();
        try {
            listaTodas.clear(); // Intento de vaciar la lista interna
            throw new AssertionError("La lista devuelta debió ser inmodificable.");
        } catch (UnsupportedOperationException e) {
            System.out.println("[OK] La lista interna está protegida frente a modificaciones externas.");
        }

        System.out.println("=== TODAS LAS VERIFICACIONES DEL REPOSITORIO PASARON CORRECTAMENTE ===");
    }
}
