# Trabajo Práctico Integrador (TPI) — Metodología de Sistemas II

Repositorio oficial del Trabajo Práctico Integrador para la materia **Metodología de Sistemas II** de la **Tecnicatura Universitaria en Programación (TUP)** — **UTN**.

---

## 👥 Integrantes del Equipo
* **Cristian Paolucci**
* **Facundo Milano**
* **Emanuel Gonzalez**
* **Lautaro Pez**

---

## 🎯 Objetivo del Proyecto
Evaluar la aplicación integrada de los contenidos de la materia mediante el análisis y mejora de un sistema de software. El foco no está en acumular líneas de código, sino en evidenciar la diferencia entre **calidad externa e interna**, identificar **deuda técnica**, reconocer **code smells**, aplicar principios **SOLID** y **Clean Code**, ejecutar **refactorings seguros** y realizar una **revisión técnica profesional (Code Review)**.

---

## 🧩 Etapas Obligatorias del TPI

1. **Selección y descripción del sistema base:** Dominio de gestión de solicitudes académicas, actores, alcance, tecnologías y evidencia de funcionamiento.
2. **Calidad externa vs. calidad interna:** Análisis de lo que percibe el usuario frente a los problemas estructurales internos.
3. **Deuda técnica y code smells:** Identificación de al menos 8 problemas técnicos (evidencia, ubicación, impacto, riesgo y prioridad).
4. **Aplicación de SOLID y Clean Code:** Mejora de responsabilidades (SRP, OCP, LSP, ISP, DIP), nombres, funciones y cohesión.
5. **Refactoring seguro:** Aplicación documentada de técnicas como *Extract Method*, *Move Method* e *Introduce Parameter Object* sin alterar el comportamiento externo.
6. **Pruebas y verificación:** Casos de prueba y evidencias antes/después para garantizar la estabilidad funcional.
7. **Code review profesional:** Checklist técnico, observaciones, feedback y criterios de aceptación.
8. **Conclusiones y aprendizaje:** Reflexión grupal sobre decisiones, límites y mejoras futuras.

---

## 🏷️ Estructura de Tags en Git (Hitos Auditables)
La evolución del código se encuentra versionada mediante los siguientes tags oficiales en el repositorio:
* `estado-inicial`: Versión base monolítica con code smells (Punto de partida).
* `etapadiagnostico`: Hito de análisis de deuda técnica.
* `etapa-refactoring`: Aplicación de principios SOLID y refactorings.
* `etapa-pruebas`: Verificación y pruebas de comportamiento.
* `etapa-docs`: Documentación y checklist de Code Review.
* `estadorefactorizado`: Versión final limpia y orientada a objetos.

---

## 📁 Estructura del Repositorio
```text
TFI-Metodologia-de-Sistemas-2/
│
├── src/                      # Código fuente en Java
│   └── MainSistemaAcademico.java  # Sistema base / versiones refactorizadas
├── docs/                     # Documentación, evidencias y checklist de Code Review
└── README.md                 # Documentación principal del proyecto
