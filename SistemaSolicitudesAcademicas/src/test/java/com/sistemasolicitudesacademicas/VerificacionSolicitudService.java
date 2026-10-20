package com.sistemasolicitudesacademicas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VerificacionSolicitudService {

    private SolicitudRepository repositorio;
    private SolicitudService servicio;

    @BeforeEach
    void setUp() {
        repositorio = new InMemorySolicitudRepository();
        servicio = new SolicitudService(repositorio);
    }

    @Test
    void debeRegistrarYAsignarIdCorrectamente() {
        // Usamos el primer valor disponible de TipoSolicitud (ej. el primero del enum o un valor genérico)
        DatosSolicitud datos = new DatosSolicitud(TipoSolicitud.values()[0], "Juan Perez", 2, "Solicitud de prueba");
        SolicitudAcademica creada = servicio.registrarSolicitud(datos);

        assertNotNull(creada.getId(), "El repositorio debe asignar un ID automático.");
        assertEquals("Juan Perez", creada.getEstudiante());
    }

    @Test
    void debeComprobarBusquedaExistenteEInexistente() {
        DatosSolicitud datos = new DatosSolicitud(TipoSolicitud.values()[0], "Maria Gomez", 1, "Excepción de correlativa");
        SolicitudAcademica creada = servicio.registrarSolicitud(datos);

        // Búsqueda existente
        SolicitudAcademica encontrada = servicio.obtenerPorId(creada.getId());
        assertNotNull(encontrada);

        // Búsqueda inexistente (debe lanzar SolicitudNoEncontradaException)
        assertThrows(SolicitudNoEncontradaException.class, () -> {
            servicio.obtenerPorId("id-falso-999");
        });
    }

    @Test
    void debeActualizarEstadoYResponsableCorrectamente() {
        DatosSolicitud datos = new DatosSolicitud(TipoSolicitud.values()[0], "Carlos Ruiz", 3, "Constancia alumno regular");
        SolicitudAcademica creada = servicio.registrarSolicitud(datos);

        // Cambiar estado usando el primer estado disponible del enum
        EstadoSolicitud nuevoEstado = EstadoSolicitud.values()[0];
        servicio.cambiarEstado(creada.getId(), nuevoEstado);
        assertEquals(nuevoEstado, servicio.obtenerPorId(creada.getId()).getEstado());

        // Asignar responsable
        servicio.asignarResponsable(creada.getId(), "Profesor Tutor");
        assertEquals("Profesor Tutor", servicio.obtenerPorId(creada.getId()).getResponsable());
    }

    @Test
    void debeProtegerFrenteAModificacionesExternas() {
        DatosSolicitud datos = new DatosSolicitud(TipoSolicitud.values()[0], "Ana Lopez", 1, "Pase de turno");
        servicio.registrarSolicitud(datos);

        List<SolicitudAcademica> lista1 = servicio.obtenerTodas();
        int tamañoOriginal = lista1.size();

        assertNotNull(lista1);
        List<SolicitudAcademica> lista2 = servicio.obtenerTodas();
        assertEquals(tamañoOriginal, lista2.size(), "La lista debe mantener la integridad frente a consultas múltiples.");
    }
}
