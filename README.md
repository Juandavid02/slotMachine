# Slot Machine Simulator - DOPO (Ciclo 1)

Simulador interactivo de máquina tragamonedas orientado a objetos, desarrollado en **Java / BlueJ** como parte del curso **Desarrollo Orientado por Objetos (DOPO-POOB)** en la **Escuela Colombiana de Ingeniería Julio Garavito** (Periodo 2026-2).

Este proyecto toma como inspiración inicial el *Problem I: Slot Machine* de la Maratón Internacional de Programación 2025, centrándose en esta primera etapa en la **construcción y simulación visual y lógica del sistema**, garantizando **extensibilidad** y reutilización del paquete gráfico `shapes`.

---

## 📋 Tabla de Contenidos
- [Características y Requisitos Funcionales](#-características-y-requisitos-funcionales)
- [Estructura y Arquitectura](#-estructura-y-arquitectura)
- [Diagrama de Clases / API Principal](#-diagrama-de-clases--api-principal)
- [Requisitos de Usabilidad y Reglas de Negocio](#-requisitos-de-usabilidad-y-reglas-de-negocio)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Retrospectiva del Proyecto](#-retrospectiva-del-proyecto)
- [Autores y Licencia](#-autores-y-licencia)

---

## 🚀 Características y Requisitos Funcionales

El simulador permite realizar las siguientes operaciones principales:

1. **Gestión de la Máquina:** Crear e inicializar la máquina tragamonedas (`SlotMachine`).
2. **Gestión de Ruedas (`manage wheels`):** Adicionar y eliminar ruedas en posiciones específicas.
3. **Gestión de Símbolos (`manage symbols`):** Adicionar, posicionar y eliminar símbolos representados por colores estándar CSS.
4. **Giro de Ruedas (`spin wheels`):** Girar una rueda individual o todas las ruedas simultáneamente.
5. **Consultas (`consult symbols`):**
   - Consultar el orden de símbolos en una rueda.
   - Obtener el número total de símbolos distintos.
   - Consultar la configuración visible actual (de izquierda a derecha).
6. **Verificación de Jackpot (`check jackpot`):** Comprobar si la configuración actual es la ganadora (con cambio de estado visual).
7. **Visibilidad (`set visibility`):** Alternar entre modo visible e invisible para pruebas y simulación en segundo plano.
8. **Control de Estado:** Método `ok()` para verificar el éxito de la última operación ejecutada y `exit()` para finalizar el simulador.

---

## 🏗 Estructura y Arquitectura

El proyecto está organizado en paquetes siguiendo el paradigma de programación orientada a objetos (POO) y desacoplamiento de componentes gráficos:

```text
slot Machine/
├── shapes/              # Paquete base/extendido de componentes visuales (Canvas, Circle, Rectangle, etc.)
├── SlotMachine.java     # Clase principal controladora / fachada del simulador
├── Wheel.java           # Clase que representa una rueda individual
├── Symbol.java          # Clase que representa un símbolo de la rueda
└── README.md            # Documentación del proyecto
