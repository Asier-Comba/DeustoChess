# ♟️ DeustoChess

**DeustoChess** es una reinterpretación del ajedrez clásico ambientada en la Universidad de Deusto.  
Cada pieza representa un personaje del campus (Rector, Bedel, Secretaria, Becario, Alumno...),  
con **habilidades únicas**, **reglas especiales** y **eventos aleatorios** que convierten cada partida en una experiencia única.

---

## 🏛️ Descripción general

- Lenguaje: **Java**
- Entorno: **Eclipse / Java Swing**
- Asignatura: **Programación III — Universidad de Deusto**
- Curso: 2025

**Idea principal:**  
Transformar el ajedrez tradicional en un juego universitario estratégico con toques de humor y azar.  
Las partidas pueden incluir minijuegos, restricciones de turno y poderes especiales según la pieza.

---

## 🎮 Piezas y habilidades

| Pieza | Habilidad especial | Descripción |
|--------|--------------------|--------------|
| 👑 **Rector** | *Reunión de urgencia* | En el siguiente turno, el rival solo puede mover Alumnos. |
| 👸 **Bedel** | *Limpieza general* | Elimina una pieza enemiga visible en línea recta o diagonal sin moverse. |
| 🧍‍♀️ **Secretaria** | *Reorganizar agenda* | Intercambia la posición de dos piezas aliadas (excepto Alumnos). |
| 🏪 **Máquina Expendedora** | *Café revitalizante* | Puede intentar dar energía a una pieza aliada mediante un minijuego tipo tragaperras. |
| 💻 **Becario** | *Corre que no llegas* | Hace dos saltos consecutivos; si come, activa un evento de azar con posibilidad de más movimientos. |
| 🎓 **Alumno** | *Graduación* | Al llegar al final del tablero, se convierte en Bedel, Secretaria o Becario. |

---

## 🧠 Mecánicas especiales

- **Expediente (Jaque):** el Rector está en peligro.  
- **Expulsión del Rector (Jaque Mate):** fin de la partida.  
- **Minijuegos:** algunos eventos abren ventanas interactivas o pruebas de azar.  
- **Habilidades únicas:** cada pieza puede usar su poder solo una vez por partida (excepto la Máquina Expendedora).
