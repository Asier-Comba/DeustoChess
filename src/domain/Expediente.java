package domain;

import java.util.ArrayList;
import java.util.List;


public class Expediente {
    private Tablero tablero;
    private Color turnoActual;
    private boolean partidaFinalizada;
    private Color ganador;
    
    public Expediente(Tablero tablero) {
        this.tablero = tablero;
        this.turnoActual = Color.BLANCA;
        this.partidaFinalizada = false;
        this.ganador = null;
    }
    
    public boolean estaEnJaque(Color color) {
        // Buscamos la posición del Rector del color especificado
        Rector rector = buscarRector(color);
        
        if (rector == null) {
            return false; 
        }
        
        int filaRector = rector.getFila();
        int columnaRector = rector.getColumna();
        
        // Verificar si alguna pieza enemiga amenaza al Rector
        return casillaAmenazadaPor(filaRector, columnaRector, colorContrario(color));
    }
    
    public boolean casillaAmenazadaPor(int fila, int columna, Color colorAtacante) {
        // Recorrer el tablero
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza pieza = tablero.getCasillas(i, j).getPieza();
                
                // Si hay una pieza del color atacante
                if (pieza != null && pieza.getColor() == colorAtacante) {
                    // Verificar si puede moverse a la casilla objetivo
                    if (pieza.movimientoValido(fila, columna, tablero)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    
    public boolean estaEnJaqueMate(Color color) {
        // Si no está en jaque, no puede estar en jaque mate
        if (!estaEnJaque(color)) {
            return false;
        }
        
        // Verificar si existe algún movimiento legal que salve al Rector
        return !tieneMovimientosLegales(color);
    }
    
   
    public boolean tieneMovimientosLegales(Color color) {
        // Recorrer todas las piezas del color especificado
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza pieza = tablero.getCasillas(i, j).getPieza();
                
                if (pieza != null && pieza.getColor() == color) {
                    // Probar todos los movimientos posibles de esta pieza
                    for (int destinoFila = 0; destinoFila < 8; destinoFila++) {
                        for (int destinoCol = 0; destinoCol < 8; destinoCol++) {
                            // Si el movimiento es válido según la pieza
                            if (pieza.movimientoValido(destinoFila, destinoCol, tablero)) {
                                // Simular el movimiento
                                if (esMovimientoLegalSimulado(pieza, destinoFila, destinoCol)) {
                                    return true; // Encontramos un movimiento legal
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return false; // No hay movimientos legales
    }
    
   
    private boolean esMovimientoLegalSimulado(Pieza pieza, int destinoFila, int destinoCol) {
        // Guardar estado original
        int origenFila = pieza.getFila();
        int origenCol = pieza.getColumna();
        Pieza piezaCapturada = tablero.getCasillas(destinoFila, destinoCol).getPieza();
        
        // Realizar movimiento temporal
        tablero.getCasillas(origenFila, origenCol).setPieza(null);
        tablero.getCasillas(destinoFila, destinoCol).setPieza(pieza);
        pieza.setFila(destinoFila);
        pieza.setColumna(destinoCol);
        
        // Verificar si el Rector queda en jaque
        boolean dejaEnJaque = estaEnJaque(pieza.getColor());
        
        // Deshacer movimiento
        tablero.getCasillas(destinoFila, destinoCol).setPieza(piezaCapturada);
        tablero.getCasillas(origenFila, origenCol).setPieza(pieza);
        pieza.setFila(origenFila);
        pieza.setColumna(origenCol);
        
        // El movimiento es legal si NO deja al Rector en jaque
        return !dejaEnJaque;
    }
    
    public boolean posibleJaque(Color color) { 	 
    	// posibleJaque es que no está en jaque pero no tiene movimientos legales
        return !estaEnJaque(color) && !tieneMovimientosLegales(color);
    }
    
   
    private Rector buscarRector(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza pieza = tablero.getCasillas(i, j).getPieza();
                if (pieza instanceof Rector && pieza.getColor() == color) {
                    return (Rector) pieza;
                }
            }
        }
        return null;
    }
    
   
    public List<Pieza> obtenerPiezasAtacantes(Color color) {
        List<Pieza> atacantes = new ArrayList<>();
        Rector rector = buscarRector(color);
        
        if (rector == null) {
            return atacantes;
        }
        
        int filaRector = rector.getFila();
        int columnaRector = rector.getColumna();
        Color colorEnemigo = colorContrario(color);
        
        // Buscar todas las piezas enemigas que amenazan al Rector
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza pieza = tablero.getCasillas(i, j).getPieza();
                
                if (pieza != null && pieza.getColor() == colorEnemigo) {
                    if (pieza.movimientoValido(filaRector, columnaRector, tablero)) {
                        atacantes.add(pieza);
                    }
                }
            }
        }
        
        return atacantes;
    }
    
    
    public boolean esMovimientoLegal(Pieza pieza, int destinoFila, int destinoCol) {
        // Primero verificar si el movimiento es válido según la pieza
        if (!pieza.movimientoValido(destinoFila, destinoCol, tablero)) {
            return false;
        }
        
        // Luego verificar si deja al propio Rector en jaque
        return esMovimientoLegalSimulado(pieza, destinoFila, destinoCol);
    }
    
   
    private Color colorContrario(Color color) {
        return (color == Color.BLANCA) ? Color.NEGRA : Color.BLANCA;
    }
    
   
    
    public void cambiarTurno() {
        turnoActual = colorContrario(turnoActual);
        
        // Verificar estados después del cambio de turno
        verificarEstadoPartida();
    }
    
    
    private void verificarEstadoPartida() {
        if (estaEnJaqueMate(turnoActual)) {
            partidaFinalizada = true;
            ganador = colorContrario(turnoActual);
        } else if (posibleJaque(turnoActual)) {
            partidaFinalizada = true;
            ganador = null; // Empate
        }
    }
    
  
    public String obtenerMensajeEstado() {
        if (partidaFinalizada) {
            if (ganador != null) {
                return "¡Expulsión del Rector del bando " + colorContrario(ganador) + "! Victoria para " + ganador;
            } else {
                return " Equilibrio administrativo (Ahogado)";
            }
        } else if (estaEnJaque(turnoActual)) {
            return " ¡Expediente al Rector del bando " + turnoActual + "!";
        } else {
            return "Turno del bando " + turnoActual;
        }
    }
    
    // === GETTERS Y SETTERS ===
    
    public Color getTurnoActual() {
        return turnoActual;
    }
    
    public void setTurnoActual(Color turnoActual) {
        this.turnoActual = turnoActual;
    }
    
    public boolean isPartidaFinalizada() {
        return partidaFinalizada;
    }
    
    public Color getGanador() {
        return ganador;
    }
    
    public Tablero getTablero() {
        return tablero;
    }
}