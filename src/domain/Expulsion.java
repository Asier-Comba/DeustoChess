package domain;

import java.util.ArrayList;
import java.util.List;


public class Expulsion {
    
    private Tablero tablero;
    private Color turnoActual;
    private boolean partidaFinalizada;
    private Color ganador;
    
    public Expulsion(Tablero tablero) {
        this.tablero = tablero;
        this.turnoActual = Color.BLANCA;
        this.partidaFinalizada = false;
        this.ganador = null;
    }
    
    // ========== MÉTODOS DE JAQUE ==========
    
  
    public boolean estaEnJaque(Color color) {
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
        // Recorrer todo el tablero buscando piezas del color atacante
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza pieza = tablero.getCasillas(i, j).getPieza();
                
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
    
    // ========== MÉTODOS DE JAQUE MATE ==========
    
   
    public boolean estaEnJaqueMate(Color color) {
        // Si no está en jaque, no puede estar en jaque mate
        if (!estaEnJaque(color)) {
            return false;
        }
        
        // Verificar si tiene algún movimiento legal para salvarse
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
                                // Simular el movimiento para ver si salva al Rector
                                if (esMovimientoLegalSimulado(pieza, destinoFila, destinoCol)) {
                                    return true; // Encontró un movimiento legal
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
        
        // Deshacer movimiento (restaurar estado)
        tablero.getCasillas(destinoFila, destinoCol).setPieza(piezaCapturada);
        tablero.getCasillas(origenFila, origenCol).setPieza(pieza);
        pieza.setFila(origenFila);
        pieza.setColumna(origenCol);
        
        // El movimiento es legal si NO deja al Rector en jaque
        return !dejaEnJaque;
    }
    
    
    public boolean esMovimientoLegal(Pieza pieza, int destinoFila, int destinoCol) {
        // Primero verificar si el movimiento es válido según la pieza
        if (!pieza.movimientoValido(destinoFila, destinoCol, tablero)) {
            return false;
        }
        
        // Luego verificar si deja al propio Rector en jaque
        return esMovimientoLegalSimulado(pieza, destinoFila, destinoCol);
    }
    
    // ========== MÉTODOS DE AHOGADO ==========
    
    public boolean hayAhogado(Color color) {
        return !estaEnJaque(color) && !tieneMovimientosLegales(color);
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
   
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
    
  
    private Color colorContrario(Color color) {
        return (color == Color.BLANCA) ? Color.NEGRA : Color.BLANCA;
    }
    
    // ========== GESTIÓN DE TURNOS ==========
    
   
    public void cambiarTurno() {
        turnoActual = colorContrario(turnoActual);
        verificarEstadoPartida();
    }
    
    
    private void verificarEstadoPartida() {
        if (estaEnJaqueMate(turnoActual)) {
            partidaFinalizada = true;
            ganador = colorContrario(turnoActual);
            
            // Marcar al Rector como en jaque mate
            Rector rector = buscarRector(turnoActual);
            if (rector != null) {
                rector.setJaqueMate(true);
                rector.setEnExpediente(true);
            }
        } else if (hayAhogado(turnoActual)) {
            partidaFinalizada = true;
            ganador = null; // Empate
        }
    }
    
   
    public String obtenerMensajeEstado() {
        if (partidaFinalizada) {
            if (ganador != null) {
                return "Expulsión del Rector - Victoria: " + ganador;
            } else {
                return "Empate";
            }
        } else if (estaEnJaque(turnoActual)) {
            return "Expediente: " + turnoActual;
        } else {
            return "Turno: " + turnoActual;
        }
    }
    
  
    public void reiniciar() {
        this.turnoActual = Color.BLANCA;
        this.partidaFinalizada = false;
        this.ganador = null;
    }
    
    // ========== GETTERS Y SETTERS ==========
    
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