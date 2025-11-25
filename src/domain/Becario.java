package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Becario extends Pieza {
    protected boolean haUsadoHabilidad;
    protected boolean expediente;
    protected boolean expulsion;
    
    public Becario(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, 
                   Color color, int fila, int columna, boolean haUsadoHabilidad) {
        super(nombre, movimiento, habilidad, color, fila, columna);
    }
    
    public boolean isHaUsadoHabilidad() {
        return haUsadoHabilidad;
    }
    
    public void setHaUsadoHabilidad(boolean haUsadoHabilidad) {
        this.haUsadoHabilidad = haUsadoHabilidad;
    }
    
    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
        return false;
    }
    
    @Override
    public void usarHabilidad(Tablero tablero) {
        if (haUsadoHabilidad) {
            JOptionPane.showMessageDialog(null, "Ya se ha usado la habilidad en este turno");
            return;
        }
        
        Random random = new Random();
        int pasos = 3;
        
        for (int i = 0; i < pasos; i++) {
            List<Casilla> candidatas = obtenerCasillasAdyacentesVacias(tablero);
            
            if (candidatas.isEmpty()) {
                break;
            }
            
            // Mover a casilla aleatoria
            Casilla destino = candidatas.get(random.nextInt(candidatas.size()));
            tablero.getCasillas(this.fila, this.columna).setPieza(null);
            
            this.fila = destino.getFila();
            this.columna = destino.getColumna();
            destino.setPieza(this);
        }
        
        haUsadoHabilidad = true;
    }
    
    private List<Casilla> obtenerCasillasAdyacentesVacias(Tablero tablero) {
        List<Casilla> lista = new ArrayList<>();
        int[] dFila = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int i = 0; i < 8; i++) {
            int f = this.fila + dFila[i];
            int c = this.columna + dCol[i];
            
            if (f >= 0 && f < 8 && c >= 0 && c < 8) {
                Casilla casilla = tablero.getCasillas(f, c);
                if (casilla.getPieza() == null) {
                    lista.add(casilla);
                }
            }
        }
        return lista;
    }
}