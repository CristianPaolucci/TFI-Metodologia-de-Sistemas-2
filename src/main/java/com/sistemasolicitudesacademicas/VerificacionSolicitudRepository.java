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

        // 5. Modificar el original después de guardar no cambia lo almacenado
        SolicitudRepository repoGuardado = new InMemorySolicitudRepository();
        String idGuardado = repoGuardado.generarSiguienteId();

        SolicitudAcademica original = new SolicitudAcademica(
                idGuardado, TipoSolicitud.CERTIFICADO,
                "Ana Lopez", 1, "Constancia de alumno regular");

        repoGuardado.guardar(original);

        original.asignarResponsable("Cambio sobre el original");
        original.cambiarEstado(EstadoSolicitud.FINALIZADO);

        SolicitudAcademica despuesDeGuardar =
                repoGuardado.buscarPorId(idGuardado).get();

        assert despuesDeGuardar.getResponsable().equals("Sin asignar")
                : "Guardar no protegió el responsable mediante una copia";
        assert despuesDeGuardar.getEstado() == EstadoSolicitud.PENDIENTE
                : "Guardar no protegió el estado mediante una copia";

        System.out.println("[OK] Modificar el original no altera lo guardado.");

        // 6. Los objetos de buscarTodas también son copias
        SolicitudAcademica elementoDeLista = repo.buscarTodas().get(0);
        elementoDeLista.asignarResponsable("Cambio desde la lista");
        elementoDeLista.cambiarEstado(EstadoSolicitud.FINALIZADO);

        SolicitudAcademica despuesDeLista = repo.buscarPorId(id1).get();
        assert despuesDeLista.getResponsable().equals("Atención Alumnos")
                : "buscarTodas expuso el responsable del objeto interno";
        assert despuesDeLista.getEstado() == EstadoSolicitud.EN_PROCESO
                : "buscarTodas expuso el estado del objeto interno";
        System.out.println("[OK] Modificar elementos de la lista no altera el repositorio.");

        // 7. Actualizar también almacena una copia independiente
        SolicitudAcademica paraActualizar = repo.buscarPorId(id1).get();
        paraActualizar.asignarResponsable("Secretaría Académica");
        paraActualizar.cambiarEstado(EstadoSolicitud.FINALIZADO);
        repo.actualizar(paraActualizar);

        paraActualizar.asignarResponsable("Cambio después de actualizar");
        paraActualizar.cambiarEstado(EstadoSolicitud.PENDIENTE);

        SolicitudAcademica despuesDeActualizar = repo.buscarPorId(id1).get();
        assert despuesDeActualizar.getResponsable().equals("Secretaría Académica")
                : "Actualizar no guardó una copia independiente del responsable";
        assert despuesDeActualizar.getEstado() == EstadoSolicitud.FINALIZADO
                : "Actualizar no guardó una copia independiente del estado";
        System.out.println("[OK] Modificar el objeto actualizado no altera lo almacenado.");

        // 8. Actualizar una solicitud inexistente debe lanzar la excepción
        SolicitudAcademica inexistente = new SolicitudAcademica(
                "99", TipoSolicitud.CERTIFICADO,
                "Juan Perez", 1, "Constancia de alumno regular");

        int cantidadAntes = repo.buscarTodas().size();

        try {
            repo.actualizar(inexistente);
            throw new AssertionError(
                    "Actualizar una solicitud inexistente debió lanzar una excepción");
        } catch (SolicitudNoEncontradaException e) {
            assert e.getMessage().equals(
                    "Error: No se encontró la solicitud con ID: 99")
                    : "El mensaje de la excepción no contiene el ID esperado";
        }

        assert repo.buscarPorId("99").isEmpty()
                : "La actualización incorporó una solicitud inexistente";
        assert repo.buscarTodas().size() == cantidadAntes
                : "La actualización fallida cambió la cantidad de solicitudes";
        System.out.println("[OK] Actualizar una solicitud inexistente informa el error sin agregarla.");
        System.out.println("=== TODAS LAS VERIFICACIONES DEL REPOSITORIO PASARON CORRECTAMENTE ===");
    }
}
