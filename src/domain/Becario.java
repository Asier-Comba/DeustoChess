package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Becario extends Pieza {
    protected boolean haUsadoHabilidad;
    
    public Becario(String nombre, Color color, int fila, int columna, boolean haUsadoHabilidad) {
        super(nombre, color, fila, columna);
        this.haUsadoHabilidad = haUsadoHabilidad;
    }
    
    public boolean isHaUsadoHabilidad() {
        return haUsadoHabilidad;
    }
    
    public void setHaUsadoHabilidad(boolean haUsadoHabilidad) {
        this.haUsadoHabilidad = haUsadoHabilidad;
    }
    
    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
        int difFila = Math.abs(nuevaFila - this.fila);
        int difCol = Math.abs(nuevaColumna - this.columna);
        
        // Verificar que esté dentro del tablero
        if (nuevaFila < 0 || nuevaFila > 7 || nuevaColumna < 0 || nuevaColumna > 7) {
            return false;
        }
        
        // Movimiento en L
        boolean esMovimientoL = (difFila == 2 && difCol == 1) || (difFila == 1 && difCol == 2);
        
        if (!esMovimientoL) {
            return false;
        }
        
        // Verificar que la casilla destino esté vacía o tenga una pieza enemiga
        Pieza piezaDestino = tablero.getCasillas(nuevaFila, nuevaColumna).getPieza();
        return piezaDestino == null || piezaDestino.getColor() != this.color;
    }
    
    @Override
    public void usarHabilidad(Tablero tablero) {
        if (haUsadoHabilidad) {
            JOptionPane.showMessageDialog(null, 
                "El Becario ya ha usado su habilidad 'Corre que no llegas' en esta partida.");
            return;
        }
        
        JOptionPane.showMessageDialog(null, 
            "¡CORRE QUE NO LLEGAS!\n\n" +
            "El Becario hará DOS saltos consecutivos en forma de L.\n" +
            "Si captura una pieza, podrá intentar más saltos con azar decreciente:\n" +
            "- 1er salto extra: 50% probabilidad\n" +
            "- 2do salto extra: 25% probabilidad\n" +
            "- 3er salto extra: 12.5% probabilidad");
        
        Random random = new Random();
        int saltosRealizados = 0;
        int saltosMaximos = 2; // Primeros dos saltos garantizados
        boolean capturo = false;
        
        // Realizar los dos primeros saltos garantizados
        for (int i = 0; i < saltosMaximos; i++) {
            List<Casilla> movimientosDisponibles = obtenerMovimientosEnL(tablero);
            
            if (movimientosDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "El Becario no tiene más movimientos disponibles.\n" +
                    "Saltos realizados: " + saltosRealizados);
                break;
            }
            
            // Elegir movimiento aleatorio
            Casilla destino = movimientosDisponibles.get(random.nextInt(movimientosDisponibles.size()));
            
            // Verificar si hay captura
            Pieza piezaEnDestino = destino.getPieza();
            if (piezaEnDestino != null && piezaEnDestino.getColor() != this.color) {
                capturo = true;
                JOptionPane.showMessageDialog(null, 
                    "💥 ¡El Becario capturó un " + piezaEnDestino.getNombre() + "!");
            }
            
            // Realizar el salto
            tablero.getCasillas(this.fila, this.columna).setPieza(null);
            this.fila = destino.getFila();
            this.columna = destino.getColumna();
            destino.setPieza(this);
            
            saltosRealizados++;
            
            // Convertir coordenadas a formato humano
            char columnaLetra = (char) ('A' + this.columna);
            int filaHumana = this.fila + 1;
            
            JOptionPane.showMessageDialog(null, 
                "💻 Salto " + saltosRealizados + " completado.\n" +
                "Nueva posición: " + columnaLetra + filaHumana);
        }
        
        // Si capturó alguna pieza, intentar saltos adicionales con probabilidad decreciente
        if (capturo) {
            JOptionPane.showMessageDialog(null, 
                "⚡ ¡El Becario tiene adrenalina!\n" +
                "Intentará saltos adicionales con azar...");
            
            double probabilidad = 0.5; // 50% para el primer salto extra
            
            while (probabilidad > 0) {
                // Verificar si el azar permite continuar
                double dado = random.nextDouble();
                
                if (dado > probabilidad) {
                    JOptionPane.showMessageDialog(null, 
                        "😰 El Becario se quedó sin energía.\n" +
                        "Probabilidad era: " + (probabilidad * 100) + "%\n" +
                        "Saltos totales: " + saltosRealizados);
                    break;
                }
                
                // ¡Logró continuar!
                List<Casilla> movimientosDisponibles = obtenerMovimientosEnL(tablero);
                
                if (movimientosDisponibles.isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                        "No hay más movimientos disponibles.\n" +
                        "Saltos totales: " + saltosRealizados);
                    break;
                }
                
                // Elegir movimiento aleatorio
                Casilla destino = movimientosDisponibles.get(random.nextInt(movimientosDisponibles.size()));
                
                // Realizar el salto
                tablero.getCasillas(this.fila, this.columna).setPieza(null);
                this.fila = destino.getFila();
                this.columna = destino.getColumna();
                destino.setPieza(this);
                
                saltosRealizados++;
                
                // Convertir coordenadas a formato humano
                char columnaLetra = (char) ('A' + this.columna);
                int filaHumana = this.fila + 1;
                
                JOptionPane.showMessageDialog(null, 
                    "✅ ¡Salto extra " + (saltosRealizados - 2) + " logrado! (Probabilidad: " + (probabilidad * 100) + "%)\n" +
                    "Nueva posición: " + columnaLetra + filaHumana);
                
                // Reducir probabilidad a la mitad para el siguiente intento
                probabilidad /= 2;
                
                // Límite de seguridad
                if (saltosRealizados >= 5) {
                    JOptionPane.showMessageDialog(null, 
                        "🏃‍♂️ ¡El Becario alcanzó el límite de resistencia!\n" +
                        "Saltos totales: " + saltosRealizados);
                    break;
                }
            }
        }
        
        // Marcar habilidad como usada
        haUsadoHabilidad = true;
        
        JOptionPane.showMessageDialog(null, 
            "Habilidad 'Corre que no llegas' finalizada.\n" +
            "Total de saltos realizados: " + saltosRealizados + "\n" +
            "¿Capturó piezas?: " + (capturo ? "SÍ" : "NO"));
    }
    
    // Obtener todos los movimientos en L disponibles
    private List<Casilla> obtenerMovimientosEnL(Tablero tablero) {
        List<Casilla> movimientos = new ArrayList<>();
        
        // Todos los posibles movimientos en L
        int[] filas = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] cols = {-1, 1, -2, 2, -2, 2, -1, 1};
        
        for (int i = 0; i < 8; i++) {
            int nuevaFila = this.fila + filas[i];
            int nuevaCol = this.columna + cols[i];
            
            // Verificar que esté dentro del tablero
            if (nuevaFila >= 0 && nuevaFila <= 7 && nuevaCol >= 0 && nuevaCol <= 7) {
                Casilla casilla = tablero.getCasillas(nuevaFila, nuevaCol);
                Pieza piezaEnCasilla = casilla.getPieza();
                
                // Agregar si está vacía o tiene una pieza enemiga
                if (piezaEnCasilla == null || piezaEnCasilla.getColor() != this.color) {
                    movimientos.add(casilla);
                }
            }
        }
        
        return movimientos;
    }
}