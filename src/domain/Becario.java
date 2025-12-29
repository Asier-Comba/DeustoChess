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

        // Dentro del tablero
        if (nuevaFila < 0 || nuevaFila > 7 || nuevaColumna < 0 || nuevaColumna > 7) {
            return false;
        }

        // Movimiento en L (caballo)
        boolean esMovimientoL = (difFila == 2 && difCol == 1) || (difFila == 1 && difCol == 2);
        if (!esMovimientoL) return false;

        // Destino vacío o enemigo
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
        int saltosMaximos = 2; // dos primeros garantizados
        boolean capturo = false;

        // 2 saltos garantizados
        for (int i = 0; i < saltosMaximos; i++) {
            List<Casilla> movimientosDisponibles = obtenerMovimientosEnL(tablero);

            if (movimientosDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "El Becario no tiene más movimientos disponibles.\n" +
                    "Saltos realizados: " + saltosRealizados);
                break;
            }

            Casilla destino = movimientosDisponibles.get(random.nextInt(movimientosDisponibles.size()));

            Pieza piezaEnDestino = destino.getPieza();
            if (piezaEnDestino != null && piezaEnDestino.getColor() != this.color) {
                capturo = true;
                JOptionPane.showMessageDialog(null,
                    "💥 ¡El Becario capturó un " + piezaEnDestino.getNombre() + "!");
            }

            tablero.getCasillas(this.fila, this.columna).setPieza(null);
            this.fila = destino.getFila();
            this.columna = destino.getColumna();
            destino.setPieza(this);

            saltosRealizados++;

            char columnaLetra = (char) ('A' + this.columna);
            int filaHumana = this.fila + 1;

            JOptionPane.showMessageDialog(null,
                "✅ Salto " + saltosRealizados + " realizado.\n" +
                "Nueva posición: " + columnaLetra + filaHumana);
        }

        // Si capturó, saltos extra con azar
        if (capturo) {
            JOptionPane.showMessageDialog(null,
                "⚡ ¡El Becario tiene adrenalina!\n" +
                "Intentará saltos adicionales con azar...");

            double probabilidad = 0.5;

            while (probabilidad > 0) {
                double dado = random.nextDouble();

                if (dado > probabilidad) {
                    JOptionPane.showMessageDialog(null,
                        "😰 El Becario se quedó sin energía.\n" +
                        "Probabilidad era: " + (probabilidad * 100) + "%\n" +
                        "Saltos totales: " + saltosRealizados);
                    break;
                }

                List<Casilla> movimientosDisponibles = obtenerMovimientosEnL(tablero);

                if (movimientosDisponibles.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "No hay más movimientos disponibles.\n" +
                        "Saltos totales: " + saltosRealizados);
                    break;
                }

                Casilla destino = movimientosDisponibles.get(random.nextInt(movimientosDisponibles.size()));

                Pieza piezaEnDestino = destino.getPieza();
                if (piezaEnDestino != null && piezaEnDestino.getColor() != this.color) {
                    JOptionPane.showMessageDialog(null,
                        "💥 ¡Captura extra! " + piezaEnDestino.getNombre());
                }

                tablero.getCasillas(this.fila, this.columna).setPieza(null);
                this.fila = destino.getFila();
                this.columna = destino.getColumna();
                destino.setPieza(this);

                saltosRealizados++;

                char columnaLetra = (char) ('A' + this.columna);
                int filaHumana = this.fila + 1;

                JOptionPane.showMessageDialog(null,
                    "✅ ¡Salto extra " + (saltosRealizados - 2) + " logrado! (Probabilidad: " + (probabilidad * 100) + "%)\n" +
                    "Nueva posición: " + columnaLetra + filaHumana);

                probabilidad /= 2;

                if (saltosRealizados >= 5) {
                    JOptionPane.showMessageDialog(null,
                        "🏃‍♂️ ¡El Becario alcanzó el límite de resistencia!\n" +
                        "Saltos totales: " + saltosRealizados);
                    break;
                }
            }
        }

        haUsadoHabilidad = true;
    }

    private List<Casilla> obtenerMovimientosEnL(Tablero tablero) {
        List<Casilla> movimientos = new ArrayList<>();

        int[] filas = {2, 2, 1, 1, -1, -1, -2, -2};
        int[] cols  = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int i = 0; i < 8; i++) {
            int nuevaFila = this.fila + filas[i];
            int nuevaCol = this.columna + cols[i];

            if (nuevaFila >= 0 && nuevaFila <= 7 && nuevaCol >= 0 && nuevaCol <= 7) {
                Casilla casilla = tablero.getCasillas(nuevaFila, nuevaCol);
                Pieza piezaEnCasilla = casilla.getPieza();

                if (piezaEnCasilla == null || piezaEnCasilla.getColor() != this.color) {
                    movimientos.add(casilla);
                }
            }
        }

        return movimientos;
    }
}
