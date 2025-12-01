package domain;

import javax.swing.JOptionPane;

public class Alumno extends Pieza {
    protected boolean haUsadoHabilidad;

    public Alumno(String nombre, Color color, int fila, int columna, boolean haUsadoHabilidad) {
        super(nombre, color, fila, columna);
        this.haUsadoHabilidad = haUsadoHabilidad;
    }

    public boolean isHaUsadoHabilidad() {
        return haUsadoHabilidad;
    }

    public void setHaUsadoHabilidad(boolean haUsadoHabilidad) {
        this.haUsadoHabilidad = haUsadoHabilidad;
    }

    // Verificamos si el movimiento es válido
    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
        int filaActual = this.getFila();
        int columnaActual = this.getColumna();
        
        // Dirección del movimiento según el color
        int direccion = (this.color == Color.BLANCA) ? 1 : -1;
        int filaInicial = (this.color == Color.BLANCA) ? 1 : 6;

        int deltaFila = nuevaFila - filaActual;
        int deltaColumna = Math.abs(nuevaColumna - columnaActual);

        // Límites del tablero
        if (nuevaFila < 0 || nuevaFila > 7 || nuevaColumna < 0 || nuevaColumna > 7) {
            return false;
        }

        Pieza piezaEnDestino = tablero.getCasillas(nuevaFila, nuevaColumna).getPieza();

        // CASO 1: Avance 1 casilla adelante
        if (deltaColumna == 0 && deltaFila == direccion) {
            return piezaEnDestino == null; // Debe estar vacío
        }

        // CASO 2: Avance 2 casillas adelante
        if (deltaColumna == 0 && deltaFila == 2 * direccion) {
            // Debe estar en fila inicial
            if (filaActual != filaInicial) return false;
            
            if (piezaEnDestino != null) return false;
            
            Pieza piezaIntermedia = tablero.getCasillas(filaActual + direccion, columnaActual).getPieza();
            return piezaIntermedia == null;
        }

        // CASO 3: Captura diagonal
        if (deltaColumna == 1 && deltaFila == direccion) {
            return (piezaEnDestino != null && piezaEnDestino.getColor() != this.color);
        }

        return false;
    }

    // Habilidad especial: GRADUACIÓN
    @Override
    public void usarHabilidad(Tablero tablero) {
        // Verificar si el Alumno ha llegado a la última fila
        int filaFinal = (this.color == Color.BLANCA) ? 7 : 0;
        
        if (this.getFila() != filaFinal) {
            JOptionPane.showMessageDialog(null, 
                "El Alumno debe llegar a la última fila para graduarse.");
            return;
        }

        // Verificar si ya se graduó
        if (haUsadoHabilidad) {
            JOptionPane.showMessageDialog(null, 
                "Este Alumno ya se ha graduado.");
            return;
        }

        // Opciones de graduación
        String[] opciones = {"Bedel", "Secretaria", "Becario"};
        
        int seleccion = JOptionPane.showOptionDialog(
            null,
            "¡El Alumno se ha graduado!\n¿En qué pieza desea convertirse?",
            "🎓 Graduación",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (seleccion == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(null, "Graduación cancelada.");
            return;
        }

        // Crear la nueva pieza según la elección
        Pieza nuevaPieza = null;
        String nombreNuevo = "";
        
        switch (seleccion) {
            case 0: // Bedel
                nuevaPieza = new Bedel("Bedel", this.color, this.fila, this.columna, true);
                nombreNuevo = "Bedel";
                break;
            case 1: // Secretaria
                nuevaPieza = new Secretaria("Secretaria", this.color, this.fila, this.columna, true);
                nombreNuevo = "Secretaria";
                break;
            case 2: // Becario
                nuevaPieza = new Becario("Becario", this.color, this.fila, this.columna, true);
                nombreNuevo = "Becario";
                break;
        }

        // Actualizar el tablero
        tablero.getCasillas(this.fila, this.columna).setPieza(nuevaPieza);
        
        // Marcar habilidad como usada
        this.haUsadoHabilidad = true;

        JOptionPane.showMessageDialog(null, 
            "🎓 ¡Graduación exitosa!\nEl Alumno asciende a " + nombreNuevo + ".\n" +
            "La nueva pieza no puede usar su habilidad en este turno.");
    }
}