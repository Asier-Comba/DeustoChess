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
    	for (int i = 0; i < 8; i++) {
    		for (int j = 0; j < 8; j++) {
    			Pieza pieza = tablero.getCasillas(i, j).getPieza();

    			if (pieza != null && pieza.getColor() == colorAtacante) {

    				if (pieza instanceof Alumno) {
    					if (esMovimientoRealmenteValido(pieza, fila, columna)) {
    						return true;
    					}
    				} 
    				else {
    					if (pieza.movimientoValido(fila, columna, tablero)) {
    						if (esMovimientoRealmenteValido(pieza, fila, columna)) {
    							return true;
    						}
    					}
    				}
    			}
    		}
    	}
    	return false;
    }

 
    private boolean esMovimientoRealmenteValido(Pieza pieza, int destinoFila, int destinoCol) {
    if (pieza instanceof Becario) {
        return true;
    }
    
    if (pieza instanceof Alumno) {
        int origenFila = pieza.getFila();
        int origenCol = pieza.getColumna();
        int deltaFila = destinoFila - origenFila;
        int deltaCol = Math.abs(destinoCol - origenCol);
        int direccion = (pieza.getColor() == Color.BLANCA) ? 1 : -1;
        
        return (deltaCol == 1 && deltaFila == direccion);
    }
    
    int origenFila = pieza.getFila();
    int origenCol = pieza.getColumna();
    
    int dirFila = Integer.compare(destinoFila, origenFila); // Usar Integer.compare o signum
    int dirCol = Integer.compare(destinoCol, origenCol);
    
    int filaActual = origenFila + dirFila;
    int colActual = origenCol + dirCol;
    
    while (filaActual != destinoFila || colActual != destinoCol) {
        if (tablero.getCasillas(filaActual, colActual).getPieza() != null) {
            return false;
        }
        filaActual += dirFila;
        colActual += dirCol;
    }
    
    return true;
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
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza pieza = tablero.getCasillas(i, j).getPieza();
                
                if (pieza != null && pieza.getColor() == colorEnemigo) {
                    if (pieza.movimientoValido(filaRector, columnaRector, tablero)) {
                        if (esMovimientoRealmenteValido(pieza, filaRector, columnaRector)) {
                            atacantes.add(pieza);
                        }
                    }
                }
            }
        }
        
        return atacantes;
    }
    
    
    public boolean estaEnJaqueMate(Color color) {
        if (!estaEnJaque(color)) {
            return false;
        }
        
        return !tieneMovimientosLegales(color);
    }
    
    public boolean tieneMovimientosLegales(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza pieza = tablero.getCasillas(i, j).getPieza();
                
                if (pieza != null && pieza.getColor() == color) {
                    for (int destinoFila = 0; destinoFila < 8; destinoFila++) {
                        for (int destinoCol = 0; destinoCol < 8; destinoCol++) {
                            if (pieza.movimientoValido(destinoFila, destinoCol, tablero)) {
                                if (esMovimientoLegalSimulado(pieza, destinoFila, destinoCol)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean esMovimientoLegalSimulado(Pieza pieza, int destinoFila, int destinoCol) {
        int origenFila = pieza.getFila();
        int origenCol = pieza.getColumna();
        Pieza piezaCapturada = tablero.getCasillas(destinoFila, destinoCol).getPieza();
        
        tablero.getCasillas(origenFila, origenCol).setPieza(null);
        tablero.getCasillas(destinoFila, destinoCol).setPieza(pieza);
        pieza.setFila(destinoFila);
        pieza.setColumna(destinoCol);
        
        boolean dejaEnJaque = estaEnJaque(pieza.getColor());
        
        tablero.getCasillas(destinoFila, destinoCol).setPieza(piezaCapturada);
        tablero.getCasillas(origenFila, origenCol).setPieza(pieza);
        pieza.setFila(origenFila);
        pieza.setColumna(origenCol);
        
        return !dejaEnJaque;
    }
    
    public boolean esMovimientoLegal(Pieza pieza, int destinoFila, int destinoCol) {
        if (!pieza.movimientoValido(destinoFila, destinoCol, tablero)) {
            return false;
        }
        
        return esMovimientoLegalSimulado(pieza, destinoFila, destinoCol);
    }
    
    
    public boolean hayAhogado(Color color) {
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
    
    private Color colorContrario(Color color) {
        return (color == Color.BLANCA) ? Color.NEGRA : Color.BLANCA;
    }
    
    
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
            ganador = null;
        }
    }
    
    public String obtenerMensajeEstado() {
        if (partidaFinalizada) {
            if (ganador != null) {
                return "Expulsión del Rector - Victoria: " + ganador;
            } else {
                return "Empate por ahogado";
            }
        } else if (estaEnJaque(turnoActual)) {
            return "¡Expediente! - Turno: " + turnoActual;
        } else {
            return "Turno: " + turnoActual;
        }
    }
    
    public void reiniciar() {
        this.turnoActual = Color.BLANCA;
        this.partidaFinalizada = false;
        this.ganador = null;
    }
    
    
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