package domain;

import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Secretaria extends Pieza {
    protected boolean haUsadoHabilidad;
    
    private int f1, c1, f2, c2;
    private boolean cancelado; // Para saber si el usuario le dio a "Cancelar"

    public Secretaria(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, Color color, int fila,
            int columna, boolean haUsadoHabilidad) {
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
        int difFila = Math.abs(nuevaFila - this.fila);
        int difCol = Math.abs(nuevaColumna - this.columna);
        
        // Es diagonal si avanza lo mismo en filas que en columnas
        return (difFila == difCol) && (difFila > 0);
    }
    
    @Override
    public void usarHabilidad(Tablero tablero) {
        usarHabilidad(tablero, (t) -> System.out.println(t), () -> {});
    }

    public void usarHabilidad(Tablero tablero, Consumer<String> actualizador, Runnable rb) {
      
    	if (haUsadoHabilidad) {
			JOptionPane.showMessageDialog(null, "Ya se ha usado la habilidad en este turno");
			return;
		}
    	
        Thread hiloSecretaria = new Thread(() -> {
            
            //Verificar si ya se ha usado
            if (haUsadoHabilidad) {
                msg("La agenda ya está cerrada por hoy.");
                return;
            }

            try {
                cancelado = false;

                //Texto en el panel izquierdo
                actualizar(actualizador, "Abriendo agenda...");
                Thread.sleep(600);
                actualizar(actualizador, "Revisando huecos...");
                Thread.sleep(600);

                //Pedir datos al usuario
                SwingUtilities.invokeAndWait(() -> {
                    try {
                        // Pieza 1
                        String sF1 = JOptionPane.showInputDialog("Fila de la 1ª pieza:");
                        if (sF1 == null) { cancelado = true; return; }
                        f1 = Integer.parseInt(sF1);

                        String sC1 = JOptionPane.showInputDialog("Columna de la 1ª pieza:");
                        if (sC1 == null) { cancelado = true; return; }
                        c1 = Integer.parseInt(sC1);
                        
                        // Pieza 2
                        String sF2 = JOptionPane.showInputDialog("Fila de la 2ª pieza:");
                        if (sF2 == null) { cancelado = true; return; }
                        f2 = Integer.parseInt(sF2);

                        String sC2 = JOptionPane.showInputDialog("Columna de la 2ª pieza:");
                        if (sC2 == null) { cancelado = true; return; }
                        c2 = Integer.parseInt(sC2);

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Introduce solo números (0-7).");
                        cancelado = true;
                    }
                });

                if (cancelado) {
                    actualizar(actualizador, "Cancelado.");
                    return;
                }

                actualizar(actualizador, "Llamando...");
                Thread.sleep(500);

                //Validaciones Lógicas
                Pieza p1 = tablero.getCasillas(f1, c1).getPieza();
                Pieza p2 = tablero.getCasillas(f2, c2).getPieza();

                if (p1 == null || p2 == null) {
                    msg("Error: Seleccionaste casillas vacías.");
                    actualizar(actualizador, "Error en datos.");
                } 
                else if (p1.getColor() != this.color || p2.getColor() != this.color) {
                    msg("Solo puedes mover a tu propio equipo.");
                    actualizar(actualizador, "Traición detectada.");
                }
                else if (esPiezaProhibida(p1) || esPiezaProhibida(p2)) {
                    msg("El Rector y los Alumnos no pueden moverse.");
                    actualizar(actualizador, "Rango insuficiente.");
                }
                else {
                    //Ejecutar el INTERCAMBIO
                    tablero.getCasillas(f1, c1).setPieza(p2);
                    tablero.getCasillas(f2, c2).setPieza(p1);

                    p1.moverPieza(f2, c2);
                    p2.moverPieza(f1, c1);
                    
                    haUsadoHabilidad = true;
                    
                    actualizar(actualizador, "¡Cambio realizado!");
                    msg("Agenda reorganizada:\n" + p1.getNombre() + " ⇄ " + p2.getNombre());
                    
                    //Refrescar el tablero visualmente
                    SwingUtilities.invokeLater(() -> {
                        if (rb != null) rb.run();
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        hiloSecretaria.start();
    }
    
    //No mover Alumnos ni Rector
    private boolean esPiezaProhibida(Pieza p) {
        return (p instanceof Alumno || p instanceof Rector);
    }

    private void actualizar(Consumer<String> consumer, String texto) {
        if (consumer != null) consumer.accept(texto);
    }
    
    private void msg(String texto) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, texto));
    }
}