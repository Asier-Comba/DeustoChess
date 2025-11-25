package domain;

import javax.swing.JOptionPane;

public class Alumno extends Pieza {
    protected boolean haUsadoHabilidad;
    // Campos para futura promoción
    protected boolean expediente; 
    protected boolean expulsion;

    public Alumno(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, Color color, int fila,
            int columna, boolean haUsadoHabilidad) {
        super(nombre, movimiento, habilidad, color, fila, columna);
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
        
        int direccion = (this.color == Color.BLANCA) ? 1 : -1; //1 si es blanco -1 si es azul
        int filaInicial = (this.color == Color.BLANCA) ? 1 : 6; //1 si es blanco 6 si es azul

        int fila = nuevaFila - filaActual;
        int columna = Math.abs(nuevaColumna - columnaActual);

        // Límites del tablero
        if (nuevaFila < 0 || nuevaFila > 7 || nuevaColumna < 0 || nuevaColumna > 7) {
            return false;
        }

        Pieza piezaEnDestino = tablero.getCasillas(nuevaFila, nuevaColumna).getPieza();

        // CASO 1: Avance 1 casilla adelante
        if (columna == 0 && fila == direccion) {
            return piezaEnDestino == null; // Debe estar vacío
        }

        // CASO 2: Avance 2 casillas adelante (inicial)
        if (columna == 0 && fila == 2 * direccion) {
            // Debe estar en fila inicial
            if (filaActual != filaInicial) return false;
            
            // La casilla destino debe estar vacía
            if (piezaEnDestino != null) return false;
            
            // La casilla intermedia debe estar vacía
            Pieza piezaIntermedia = tablero.getCasillas(filaActual + direccion, columnaActual).getPieza();
            return piezaIntermedia == null;
        }

        // CASO 3: Captura diagonal
        if (columna == 1 && fila == direccion) {
            // Debe haber una pieza enemiga
            if (piezaEnDestino != null && piezaEnDestino.getColor() != this.color) {
                return true;
            }
        }

        return false;
    }

    // Habilidad especial
    @Override
    public void usarHabilidad(Tablero tablero) {
        // Verificar si se usó antes
        if (haUsadoHabilidad) {
            JOptionPane.showMessageDialog(null, "El alumno ya ha hecho su sprint de estudio en esta partida.");
            return;
        }

        int filaActual = this.getFila();
        int columnaActual = this.getColumna();
        int direccion = (this.color == Color.BLANCA) ? 1 : -1;
        int nuevaFila = filaActual + (2 * direccion);

        //Verificar que esté dentro del tablero
        if (nuevaFila < 0 || nuevaFila > 7) {
            JOptionPane.showMessageDialog(null, "El sprint te llevaría fuera del campus (tablero).");
            return;
        }

        //Verificar que el camino esté libre
        Pieza p1 = tablero.getCasillas(filaActual + direccion, columnaActual).getPieza();
        Pieza p2 = tablero.getCasillas(nuevaFila, columnaActual).getPieza();

        if (p1 == null && p2 == null) {            
            // Actualizar tablero 
            tablero.getCasillas(filaActual, columnaActual).setPieza(null);
            tablero.getCasillas(nuevaFila, columnaActual).setPieza(this);

            // Actualizar pieza
            this.setFila(nuevaFila);
            this.haUsadoHabilidad = true;

            JOptionPane.showMessageDialog(null, "¡Sprint de estudio realizado! Has avanzado 2 casillas.");
            
        } else {
            JOptionPane.showMessageDialog(null, "No puedes hacer un sprint, hay obstáculos (piezas) en el camino.");
        }
    }
}