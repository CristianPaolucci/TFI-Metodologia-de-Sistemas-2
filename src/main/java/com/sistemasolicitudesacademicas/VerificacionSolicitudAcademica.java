
package com.sistemasolicitudesacademicas;

/**
 * Verificación sin dependencias externas.
 * Ejecutar con: java com.sistemasolicitudesacademicas.VerificacionSolicitudAcademica
 * Termina con código 1 si alguna comprobación falla.
 */
public class VerificacionSolicitudAcademica {
    private static int ok = 0, fallos = 0;

    public static void main(String[] args) {
        System.out.println("== Datos ==");
        SolicitudAcademica s = new SolicitudAcademica("7", TipoSolicitud.PASE, "Ana Lopez", 2, "Pase a otra sede");
        check("id", "7".equals(s.getId()));
        check("tipo", s.getTipo() == TipoSolicitud.PASE);
        check("estudiante", "Ana Lopez".equals(s.getEstudiante()));
        check("prioridad", s.getPrioridad() == 2);
        check("descripcion", "Pase a otra sede".equals(s.getDescripcion()));

        System.out.println("== Valores iniciales ==");
        check("estado inicial PENDIENTE", s.getEstado() == EstadoSolicitud.PENDIENTE);
        check("responsable inicial 'Sin asignar'", "Sin asignar".equals(s.getResponsable()));

        System.out.println("== Prioridad 1..3 ==");
        for (int p : new int[]{1, 2, 3}) {
            final int pp = p;
            noLanza("prioridad " + p + " válida", () -> nueva(pp));
        }
        for (int p : new int[]{0, 4, -1, Integer.MIN_VALUE, Integer.MAX_VALUE}) {
            final int pp = p;
            lanza("prioridad " + p + " inválida", () -> nueva(pp));
        }

        System.out.println("== Validaciones de campos ==");
        lanza("id null", () -> new SolicitudAcademica(null, TipoSolicitud.PASE, "A", 1, "d"));
        lanza("id vacío", () -> new SolicitudAcademica("  ", TipoSolicitud.PASE, "A", 1, "d"));
        lanza("tipo null", () -> new SolicitudAcademica("1", null, "A", 1, "d"));
        lanza("estudiante null", () -> new SolicitudAcademica("1", TipoSolicitud.PASE, null, 1, "d"));
        lanza("estudiante vacío", () -> new SolicitudAcademica("1", TipoSolicitud.PASE, "", 1, "d"));
        lanza("estudiante en blanco", () -> new SolicitudAcademica("1", TipoSolicitud.PASE, "   ", 1, "d"));
        lanza("descripción null", () -> new SolicitudAcademica("1", TipoSolicitud.PASE, "A", 1, null));
        lanza("descripción vacía", () -> new SolicitudAcademica("1", TipoSolicitud.PASE, "A", 1, " "));
        lanza("cambiarEstado(null)", () -> nueva(1).cambiarEstado(null));
        lanza("asignarResponsable(null)", () -> nueva(1).asignarResponsable(null));
        lanza("asignarResponsable(vacío)", () -> nueva(1).asignarResponsable(" "));

        System.out.println("== Estado inválido no altera la solicitud ==");
        SolicitudAcademica t = nueva(1);
        t.asignarResponsable("Alumnos");
        t.cambiarEstado(EstadoSolicitud.EN_PROCESO);
        try { t.cambiarEstado(null); } catch (ValidacionSolicitudException e) { }
        try { t.asignarResponsable(""); } catch (ValidacionSolicitudException e) { }
        check("estado se conserva", t.getEstado() == EstadoSolicitud.EN_PROCESO);
        check("responsable se conserva", "Alumnos".equals(t.getResponsable()));

        System.out.println("== Copias ==");
        SolicitudAcademica orig = new SolicitudAcademica("9", TipoSolicitud.EQUIVALENCIA, "Luis Paz", 3, "Equivalencia de materia");
        orig.asignarResponsable("Dpto. de Alumnos");
        orig.cambiarEstado(EstadoSolicitud.EN_PROCESO);
        SolicitudAcademica c = orig.copiar();
        check("copia es otro objeto", c != orig);
        check("copia: id", orig.getId().equals(c.getId()));
        check("copia: tipo", orig.getTipo() == c.getTipo());
        check("copia: estudiante", orig.getEstudiante().equals(c.getEstudiante()));
        check("copia: prioridad", orig.getPrioridad() == c.getPrioridad());
        check("copia: descripción", orig.getDescripcion().equals(c.getDescripcion()));
        check("copia: estado (EN_PROCESO)", c.getEstado() == EstadoSolicitud.EN_PROCESO);
        check("copia: responsable", "Dpto. de Alumnos".equals(c.getResponsable()));
        c.cambiarEstado(EstadoSolicitud.FINALIZADO);
        c.asignarResponsable("Otro");
        check("modificar copia no cambia original (estado)", orig.getEstado() == EstadoSolicitud.EN_PROCESO);
        check("modificar copia no cambia original (responsable)", "Dpto. de Alumnos".equals(orig.getResponsable()));
        orig.cambiarEstado(EstadoSolicitud.PENDIENTE);
        check("modificar original no cambia copia", c.getEstado() == EstadoSolicitud.FINALIZADO);
        SolicitudAcademica cInicial = nueva(2).copiar();
        check("copia de solicitud nueva: PENDIENTE / Sin asignar",
                cInicial.getEstado() == EstadoSolicitud.PENDIENTE && "Sin asignar".equals(cInicial.getResponsable()));

        System.out.println("\nResultado: " + ok + " correctas, " + fallos + " fallidas");
        if (fallos > 0) System.exit(1);
    }

    private static SolicitudAcademica nueva(int prioridad) {
        return new SolicitudAcademica("1", TipoSolicitud.CERTIFICADO, "Maria Gomez", prioridad, "Constancia");
    }

    private static void check(String nombre, boolean cond) {
        if (cond) ok++; else fallos++;
        System.out.println((cond ? "  OK    " : "  FALLA ") + nombre);
    }

    private static void lanza(String nombre, Runnable r) {
        try { r.run(); check(nombre + " -> lanza ValidacionSolicitudException", false); }
        catch (ValidacionSolicitudException e) { check(nombre + " -> lanza ValidacionSolicitudException", true); }
        catch (RuntimeException e) { check(nombre + " -> excepción incorrecta " + e.getClass().getSimpleName(), false); }
    }

    private static void noLanza(String nombre, Runnable r) {
        try { r.run(); check(nombre, true); }
        catch (RuntimeException e) { check(nombre + " (lanzó " + e.getClass().getSimpleName() + ")", false); }
    }
}
