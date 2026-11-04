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
        TipoSolicitud tipo = TipoSolicitud.values()[0];
        DatosSolicitud datos = new DatosSolicitud(tipo, "Juan Perez", 2, "Solicitud de prueba");
        SolicitudAcademica creada = servicio.registrarSolicitud(datos);

        assertNotNull(creada.getId());
        assertEquals("Juan Perez", creada.getEstudiante());
        assertEquals(EstadoSolicitud.PENDIENTE, creada.getEstado());
        assertEquals("Sin asignar", creada.getResponsable());
    }

    @Test
    void debeLanzarExcepcionCuandoPrioridadEsInvalida() {
        TipoSolicitud tipo = TipoSolicitud.values()[0];

        // Prioridad menor a 1
        DatosSolicitud datosBaja = new DatosSolicitud(tipo, "Ana Gomez", 0, "Prioridad inválida baja");
        assertThrows(ValidacionSolicitudException.class, () -> {
            servicio.registrarSolicitud(datosBaja);
        });

        // Prioridad mayor a 3
        DatosSolicitud datosAlta = new DatosSolicitud(tipo, "Carlos Ruiz", 4, "Prioridad inválida alta");
        assertThrows(ValidacionSolicitudException.class, () -> {
            servicio.registrarSolicitud(datosAlta);
        });
    }

    @Test
    void debeComprobarBusquedaExistenteEInexistente() {
        TipoSolicitud tipo = TipoSolicitud.values()[0];
        DatosSolicitud datos = new DatosSolicitud(tipo, "Maria Gomez", 1, "Excepción de correlativa");
        SolicitudAcademica creada = servicio.registrarSolicitud(datos);

        SolicitudAcademica encontrada = repositorio.buscarPorId(creada.getId()).orElse(null);
        assertNotNull(encontrada);
        assertEquals(creada.getId(), encontrada.getId());

        assertThrows(SolicitudNoEncontradaException.class, () -> {
            servicio.cambiarEstado("id-falso-999", EstadoSolicitud.values()[0]);
        });
    }

    @Test
    void debeActualizarEstadoYResponsableCorrectamente() {
        TipoSolicitud tipo = TipoSolicitud.values()[0];
        DatosSolicitud datos = new DatosSolicitud(tipo, "Lucia Fernandez", 3, "Constancia alumno regular");
        SolicitudAcademica creada = servicio.registrarSolicitud(datos);

        EstadoSolicitud nuevoEstado = EstadoSolicitud.values()[0];
        servicio.cambiarEstado(creada.getId(), nuevoEstado);
        assertEquals(nuevoEstado, repositorio.buscarPorId(creada.getId()).get().getEstado());

        servicio.asignarResponsable(creada.getId(), "Profesor Tutor");
        SolicitudAcademica actualizada = repositorio.buscarPorId(creada.getId()).get();
        assertEquals("Profesor Tutor", actualizada.getResponsable());
    }

    @Test
    void debeProtegerFrenteAModificacionesExternas() {
        TipoSolicitud tipo = TipoSolicitud.values()[0];
        DatosSolicitud datos = new DatosSolicitud(tipo, "Pedro Picapiedra", 1, "Pase de turno");
        servicio.registrarSolicitud(datos);

        List<SolicitudAcademica> lista1 = repositorio.buscarTodas();
        int tamañoOriginal = lista1.size();
        assertNotNull(lista1);

        // Intentamos mutar la lista obtenida externamente (debe estar protegida por copias defensivas)
        List<SolicitudAcademica> lista2 = repositorio.buscarTodas();
        assertEquals(tamañoOriginal, lista2.size());
        assertNotSame(lista1.get(0), lista2.get(0), "Las instancias devueltas deben ser copias defensivas independientes");
    }
}