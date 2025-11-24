package domain;

import java.util.Random;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MaquinaExpendedora extends Pieza {
	
	public MaquinaExpendedora(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, Color color, int fila, int columna, boolean haUsadoHabilidad) {
		super(nombre, movimiento, habilidad, color, fila, columna);
	}

	@Override
	public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
		return false;
	}

	@Override
	public void usarHabilidad(Tablero tablero) {
		usarHabilidad(tablero, (texto) -> System.out.println(texto), () -> {});
	}

	// === TRAGAPERRAS ===
	public void usarHabilidad(Tablero tablero, Consumer<String> actualizador, Runnable rb) { //IA generativa utilizamos el consumer para actualizar la pantalla y no tener así que creear una nueva ventana
		
		Thread hiloTragaperras = new Thread(() -> {
			String[] simbolos = {"♕", "♔", "♙", "♖"}; 
			Random random = new Random();
			String resultadoFinal = "";
			boolean premio = false;

			int giros = 15; 
			
			try {
				for (int i = 0; i < giros; i++) {
					String s1 = simbolos[random.nextInt(simbolos.length)];
					String s2 = simbolos[random.nextInt(simbolos.length)];
					String s3 = simbolos[random.nextInt(simbolos.length)];
					
					String textoPantalla = " [ " + s1 + " | " + s2 + " | " + s3 + " ] ";
					
					if (actualizador != null) {
						actualizador.accept(textoPantalla);
					}
					
					Thread.sleep(100 + (i * 20)); 
					
					if (i == giros - 1) {
						if (s1.equals(s2) && s2.equals(s3)) {
							premio = true;
							resultadoFinal = "¡PREMIO! DOBLE MOVIMIENTO";
						} else {
							premio = false;
							resultadoFinal = "FALLO - PIERDES TURNO";
						}
					}
				}
				
				final String mensaje = resultadoFinal;
				
				if (actualizador != null) {
					actualizador.accept(mensaje);
				}
				
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, mensaje);
					if (rb != null) rb.run();
				});

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});

		hiloTragaperras.start();
	}
}