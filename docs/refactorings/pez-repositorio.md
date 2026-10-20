# Justificación de los cambios del repositorio

Implementación: Lautaro Pez.
Revisión y correcciones: Facundo Milano, con asistencia de IA.

## Problema identificado

El almacenamiento necesita controlar cómo se guardan, consultan y
actualizan las solicitudes. Si entrega directamente sus objetos internos,
otra parte del programa puede modificarlos sin utilizar una operación
explícita del repositorio.

También es necesario concentrar la generación de identificadores y
establecer qué ocurre cuando se intenta actualizar una solicitud inexistente.

## Cambios realizados

Se definieron las operaciones del almacenamiento en SolicitudRepository
y se implementaron en InMemorySolicitudRepository:

- generarSiguienteId(): genera identificadores secuenciales.
- guardar(...): almacena una copia de la solicitud.
- buscarPorId(...): busca por identificador y devuelve una copia cuando
  encuentra la solicitud.
- actualizar(...): reemplaza una solicitud existente por una copia de
  los datos recibidos.
- buscarTodas(): devuelve una lista inmodificable con copias de las
  solicitudes almacenadas.

Las copias se obtienen mediante copiar(), definido en el modelo de
González. Una actualización de un identificador inexistente lanza
SolicitudNoEncontradaException.

## Justificación teórica

La separación del almacenamiento responde al principio de responsabilidad
única trabajado en SEMANA_II_SOLID: el repositorio se ocupa de conservar
y recuperar solicitudes, mientras que el servicio coordina las operaciones
y el reporte presenta la información.

La interfaz permite que el servicio utilice las operaciones del repositorio
sin depender directamente de cómo se almacena la lista en memoria.

El uso de copias protege los datos internos. Para modificar lo almacenado
es necesario utilizar actualizar(...), lo que hace explícita esa operación.

SEMANA_III_CLEAN_CODE plantea que el refactoring debe estar respaldado
por pruebas y que los errores no deben ocultarse. Por eso se agregaron
verificaciones de los resultados y de la excepción esperada.

## Cambios de comportamiento y compatibilidad

Las consultas devuelven copias. Modificar una solicitud consultada ya no
modifica automáticamente el objeto almacenado: el servicio debe llamar
a actualizar(...).

La lista de buscarTodas() no admite modificaciones. Intentar vaciarla
produce UnsupportedOperationException.

Estos cambios en el uso del repositorio se declaran expresamente;
no se presenta la entrega como un cambio que conserve todas las
interacciones anteriores.

El servicio existente todavía utiliza obtenerTodas(). Su adaptación a
la interfaz actual corresponde a Cristian Paolucci.

## Correcciones realizadas durante la revisión

- Se reemplazaron las llamadas a setEstado y setResponsable por
  cambiarEstado y asignarResponsable, disponibles en el modelo.
- Se reemplazó una impresión de error por AssertionError para impedir
  que una verificación fallida llegue al mensaje final de éxito.
- Se corrigió la construcción de SolicitudNoEncontradaException para
  pasar solamente el ID, evitando duplicar el texto del mensaje.
- Se completaron las verificaciones de copias y actualización inexistente.
- La prueba de copia al guardar utiliza un repositorio separado para
  evitar que una actualización posterior oculte un problema de guardado.

## Verificación

Se comprobaron generación de IDs, búsquedas existentes e inexistentes,
actualización de datos, protección de la lista y sus elementos,
independencia de las copias al guardar y actualizar, y excepción ante
una actualización inexistente.

Las verificaciones del modelo y del repositorio se ejecutaron con JDK 21
y con -ea para activar los assert. La salida real está en
docs/evidencias/pez-repositorio.txt.

La compilación completa y las verificaciones del sistema integrado quedan
pendientes hasta completar las entregas siguientes. El equipo acepta
este estado intermedio durante la refactorización.
