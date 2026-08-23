# 🎰 SlotMachine

Simulador de una máquina tragamonedas desarrollado como proyecto inicial del curso **Desarrollo Orientado por Objetos [DOPO-POOB]**
Escuela Colombiana de Ingeniería — Ciclo No. 1, 2026-2.

El proyecto está inspirado en el *Problem I* de la Maratón de Programación Internacional 2025: **Slot Machine**.

> ⚠️ **Nota:** en esta entrega no se resuelve el problema de la maratón; únicamente se construye el simulador de la máquina.

---

## 📑 Contenido

- [Autores](#-autores)
- [Descripción](#-descripción)
- [Diseño](#-diseño)
- [Reglas de diseño](#-reglas-de-diseño)
- [Requisitos de usabilidad](#-requisitos-de-usabilidad)
- [Cómo ejecutar el proyecto](#-cómo-ejecutar-el-proyecto)
- [Construcción](#-construcción)
- [Diagramas](#-diagramas)
- [Mini-ciclos de desarrollo](#-mini-ciclos-de-desarrollo)
- [Uso de IA generativa](#-uso-de-ia-generativa)
- [Referencias](#-referencias)
- [Licencia](#-licencia)

---

## 👥 Autores

- Juan David Rojas
- César Morales

---

## 📋 Descripción

El simulador permite crear una máquina tragamonedas compuesta por múltiples ruedas (`Wheel`), cada una capaz de mostrar un símbolo representado por un color (siguiendo el estándar CSS). La máquina permite:

1. Crear una máquina tragamonedas.
2. Adicionar o eliminar una rueda.
3. Adicionar o eliminar un símbolo.
4. Girar las ruedas de la máquina (una, todas, o colocar un símbolo específico).
5. Consultar los símbolos de la máquina (colores, distintos, configuración).
6. Consultar si la configuración actual es la ganadora (jackpot).
7. Hacer visible o invisible el simulador (funciona también en modo invisible).
8. Terminar el simulador.

---

## 🧩 Diseño

### Clase principal: `SlotMachine`

Es la clase central del simulador. Administra la colección de ruedas (`Wheel`), la lista de símbolos disponibles (colores) y el estado general de la máquina (visibilidad, resultado de la última operación y si se alcanzó un jackpot).

### Clase auxiliar: `Wheel`

Representa una rueda individual con un símbolo circular visible en su interior. Administra su propia posición, color y visibilidad, y expone el índice del símbolo actualmente mostrado.

---

## 📏 Reglas de diseño

- Los símbolos se identifican mediante colores. Los nombres deben corresponder al estándar CSS (`red`, `black`, `blue`, `yellow`, `green`, `white`, `orange`, `cyan`).
- Las posiciones se enumeran a partir de **1**. Si la posición es menor a 1, se usa la posición 1; si es mayor al número máximo de elementos, se usa el máximo (ajuste automático a los límites válidos, sin lanzar excepciones).
- `symbols()` retorna los colores de los símbolos en el orden en que fueron agregados.
- `configuration()` retorna los colores visibles en todas las ruedas de la máquina, ordenados de izquierda a derecha.
- `ok()` indica si la última operación se realizó con éxito.
- Un **jackpot** ocurre cuando hay al menos dos ruedas y todas muestran el mismo símbolo. En ese caso, la máquina cambia su color a verde y se despliega un mensaje de felicitación.

---

## 🎨 Requisitos de usabilidad

- Todos los elementos tienen una representación visual adecuada (a través del paquete `shapes`).
- Los símbolos tienen colores diferentes entre sí.
- La máquina cambia de apariencia (color verde) al llegar a un estado ganador.
- Si una acción no se puede realizar, se presenta un mensaje mediante `JOptionPane`, únicamente si el simulador está visible.

---

## ▶️ Cómo ejecutar el proyecto

1. Abrir **BlueJ** y cargar el proyecto `slotMachine`.
2. Compilar todas las clases (`Project → Compile`, o el botón *Compile* de la barra de herramientas).
3. Hacer clic derecho sobre la clase `SlotMachine` en el diagrama y seleccionar `new SlotMachine()` para crear una instancia.
4. Sobre el objeto creado en la banca de objetos, hacer clic derecho para invocar sus métodos (`addWheel`, `addSymbol`, `spin`, `isJackpot`, etc.) y observar los cambios en el simulador gráfico.
5. Consultar `configuration()`, `symbols()` o `ok()` desde el mismo menú contextual para verificar el estado de la máquina tras cada operación.

---

## 🛠️ Construcción

- El proyecto reutiliza y extiende los componentes gráficos del paquete `shapes` (`Rectangle`, `Circle`).
- Desarrollado en **BlueJ**, bajo el nombre de proyecto `slotMachine`.
- Documentado siguiendo el estándar Javadoc.

---

## 📐 Diagramas

Los diagramas de clases y de secuencia se encuentran en el archivo del proyecto **Astah** incluido en este repositorio.

---

## 🔄 Mini-ciclos de desarrollo

Se definieron 7 mini-ciclos, uno por cada requisito funcional, siguiendo el orden natural de dependencia entre operaciones:

| # | Mini-ciclo | Estado |
|---|------------|--------|
| 1 | Crear la máquina tragamonedas | ✅ Completo |
| 2 | Gestionar ruedas (agregar/eliminar) | ✅ Completo |
| 3 | Gestionar símbolos (agregar/eliminar) | ✅ Completo |
| 4 | Girar las ruedas (una, todas, colocar símbolo) | ✅ Completo |
| 5 | Consultar símbolos de la máquina | ✅ Completo |
| 6 | Verificar jackpot | ✅ Completo |
| 7 | Visibilidad del simulador | ✅ Completo |

Para más detalle sobre el proceso de desarrollo, decisiones técnicas y prácticas XP aplicadas, ver [`Retrospectiva.pdf`](./Retrospectiva.pdf).

---

## 🤖 Uso de IA generativa

Durante el desarrollo se usaron herramientas de IA generativa como apoyo para comprender APIs de Java y resolver dudas puntuales de implementación.

- Anthropic. (2026). *Claude* (Sonnet 5) [Modelo de lenguaje de gran escala]. https://claude.ai
- OpenAI. (2026). *ChatGPT* [Modelo de lenguaje de gran escala]. https://chat.openai.com

---

## 📚 Referencias

- Oracle. (s.f.). *Class Random*. Java Platform SE 17 Documentation. Recuperado de https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Random.html

---

## 📄 Licencia

Proyecto académico desarrollado para el curso DOPO-POOB, Escuela Colombiana de Ingeniería.
