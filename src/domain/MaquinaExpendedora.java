package domain;

import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MaquinaExpendedora extends Pieza {
	
	private JLabel displayExterno; // Para actualizar la pantalla del panel
	
	public MaquinaExpendedora(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, Color color, int fila, int columna, boolean haUsadoHabilidad) {
		super(nombre, movimiento, habilidad, color, fila, columna);
	}

	@Override
	public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
		return false;
	}

	@Override
	public void usarHabilidad(Tablero tablero) {
		usarHabilidadConDisplay(tablero, null);
	}
	
	// Método público para que PanelTablero pueda pasar el JLabel
	public void usarHabilidadConDisplay(Tablero tablero, JLabel display) {
		this.displayExterno = display;
		
		Thread hiloTragaperras = new Thread(new Runnable() {
			@Override
			public void run() {
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
						
						final String textoPantalla = " [ " + s1 + " | " + s2 + " | " + s3 + " ] ";
						
						// Actualizar el display si existe
						if (displayExterno != null) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									displayExterno.setText(textoPantalla);
								}
							});
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
					
					// Mostrar mensaje final en el display
					if (displayExterno != null) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								displayExterno.setText(mensaje);
							}
						});
					}
					
					// Mostrar diálogo
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(null, mensaje);
						}
					});

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});

		hiloTragaperras.start();
	}
}