package domain;

import javax.swing.JOptionPane;

public class Secretaria extends Pieza {
    protected boolean haUsadoHabilidad;
    
    private int f1, c1, f2, c2;
    private boolean cancelado;

    public Secretaria(String nombre, Color color, int fila, int columna, boolean haUsadoHabilidad) {
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
        // Dentro del tablero
        if (nuevaFila < 0 || nuevaFila > 7 || nuevaColumna < 0 || nuevaColumna > 7) return false;

        // No quedarse en la misma casilla
        if (nuevaFila == this.fila && nuevaColumna == this.columna) return false;

        int difFila = nuevaFila - this.fila;
        int difCol = nuevaColumna - this.columna;

        // Movimiento de alfil: diagonal pura
        if (Math.abs(difFila) != Math.abs(difCol)) return false;

        // Comprobar que el camino está libre (sin saltar piezas)
        int pasoFila = Integer.signum(difFila);
        int pasoCol = Integer.signum(difCol);

        int f = this.fila + pasoFila;
        int c = this.columna + pasoCol;

        while (f != nuevaFila) { // en diagonal, con esto vale
            if (tablero.getCasillas(f, c).getPieza() != null) return false;
            f += pasoFila;
            c += pasoCol;
        }

        // Destino: vacío o enemigo (nunca aliado)
        Pieza destino = tablero.getCasillas(nuevaFila, nuevaColumna).getPieza();
        return (destino == null || destino.getColor() != this.color);
    }

    @Override
    public void usarHabilidad(Tablero tablero) {
        if (haUsadoHabilidad) {
            JOptionPane.showMessageDialog(null, "Ya se ha usado la habilidad en este turno");
            return;
        }

        try {
            cancelado = false;

            System.out.println("Abriendo agenda...");
            Thread.sleep(600);
            System.out.println("Revisando huecos...");
            Thread.sleep(600);

            try {
                // Pieza 1
                String coord1 = JOptionPane.showInputDialog("Coordenada de la 1ª pieza (ej: A1, B3, H8):");
                if (coord1 == null) { 
                    cancelado = true; 
                    return; 
                }
                coord1 = coord1.trim().toUpperCase();
                
                if (!validarFormatoCoordenada(coord1)) {
                    JOptionPane.showMessageDialog(null, "Error: Formato inválido. Usa formato como A1, B3, etc.");
                    cancelado = true;
                    return;
                }
                
                c1 = coord1.charAt(0) - 'A'; // A=0, B=1, ..., H=7
                f1 = Character.getNumericValue(coord1.charAt(1)) - 1; // 1=0, 2=1, ..., 8=7
                
                // Pieza 2
                String coord2 = JOptionPane.showInputDialog("Coordenada de la 2ª pieza (ej: A1, B3, H8):");
                if (coord2 == null) { 
                    cancelado = true; 
                    return; 
                }
                coord2 = coord2.trim().toUpperCase();
                
                if (!validarFormatoCoordenada(coord2)) {
                    JOptionPane.showMessageDialog(null, "Error: Formato inválido. Usa formato como A1, B3, etc.");
                    cancelado = true;
                    return;
                }
                
                c2 = coord2.charAt(0) - 'A';
                f2 = Character.getNumericValue(coord2.charAt(1)) - 1;
                
                // Validar rango
                if (f1 < 0 || f1 > 7 || c1 < 0 || c1 > 7 || f2 < 0 || f2 > 7 || c2 < 0 || c2 > 7) {
                    JOptionPane.showMessageDialog(null, "Error: Las coordenadas deben estar entre A1 y H8.");
                    cancelado = true;
                    return;
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: Formato inválido. Usa formato como A1, B3, etc.");
                cancelado = true;
            }

            if (cancelado) {
                System.out.println("Cancelado.");
                return;
            }

            System.out.println("Llamando...");
            Thread.sleep(500);

            // Validaciones
            Pieza p1 = tablero.getCasillas(f1, c1).getPieza();
            Pieza p2 = tablero.getCasillas(f2, c2).getPieza();

            if (p1 == null || p2 == null) {
                JOptionPane.showMessageDialog(null, "Error: Seleccionaste casillas vacías.");
                System.out.println("Error en datos.");
            } 
            else if (p1.getColor() != this.color || p2.getColor() != this.color) {
                JOptionPane.showMessageDialog(null, "Solo puedes mover a tu propio equipo.");
                System.out.println("Traición detectada.");
            }
            else if (esPiezaProhibida(p1) || esPiezaProhibida(p2)) {
                JOptionPane.showMessageDialog(null, "El Rector y los Alumnos no pueden moverse.");
                System.out.println("Rango insuficiente.");
            }
            else {
                // INTERCAMBIO
                tablero.getCasillas(f1, c1).setPieza(p2);
                tablero.getCasillas(f2, c2).setPieza(p1);

                p1.moverPieza(f2, c2);
                p2.moverPieza(f1, c1);
                
                haUsadoHabilidad = true;
                
                System.out.println("¡Cambio realizado!");
                JOptionPane.showMessageDialog(null, "Agenda reorganizada:\n" + p1.getNombre() + " ⇄ " + p2.getNombre());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean esPiezaProhibida(Pieza p) {
        return (p instanceof Alumno || p instanceof Rector);
    }
    
    // Valida que la coordenada tenga formato válido (letra A-H + número 1-8)
    private boolean validarFormatoCoordenada(String coord) {
        if (coord == null || coord.length() != 2) {
            return false;
        }
        
        char letra = coord.charAt(0);
        char numero = coord.charAt(1);
        
        return (letra >= 'A' && letra <= 'H') && (numero >= '1' && numero <= '8');
    }
}