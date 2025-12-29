package domain;

import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MaquinaExpendedora extends Pieza {

	private JLabel displayExterno; // Para actualizar la pantalla del panel
	private boolean premio;

	public MaquinaExpendedora(String nombre, Color color, int fila, int columna, boolean haUsadoHabilidad) {
		super(nombre, color, fila, columna);
		this.premio = false;
	}

	@Override
	public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
		// Movimiento tipo TORRE: misma fila o misma columna y camino libre
		if (nuevaFila < 0 || nuevaFila > 7 || nuevaColumna < 0 || nuevaColumna > 7) return false;
		if (nuevaFila == this.fila && nuevaColumna == this.columna) return false;

		boolean mismaFila = (nuevaFila == this.fila);
		boolean mismaCol = (nuevaColumna == this.columna);
		if (!mismaFila && !mismaCol) return false;

		int df = Integer.signum(nuevaFila - this.fila);
		int dc = Integer.signum(nuevaColumna - this.columna);

		int f = this.fila + df;
		int c = this.columna + dc;

		while (f != nuevaFila || c != nuevaColumna) {
			if (tablero.getCasillas(f, c).getPieza() != null) return false;
			f += df;
			c += dc;
		}

		Pieza destino = tablero.getCasillas(nuevaFila, nuevaColumna).getPieza();
		return (destino == null || destino.getColor() != this.color);
	}

	@Override
	public void usarHabilidad(Tablero tablero) {
		usarHabilidadConDisplay(tablero, null);
	}

	public void usarHabilidadConDisplay(Tablero tablero, JLabel display) {
		this.displayExterno = display;

		Thread hiloTragaperras = new Thread(new Runnable() {
			@Override
			public void run() {
				String[] simbolos = { "♕", "♔", "♙", "♖" };
				Random random = new Random();
				String resultadoFinal = "";
				premio = false;

				int giros = 15;

				try {
					for (int i = 0; i < giros; i++) {
						String s1 = simbolos[random.nextInt(simbolos.length)];
						String s2 = simbolos[random.nextInt(simbolos.length)];
						String s3 = simbolos[random.nextInt(simbolos.length)];

						final String textoPantalla = " [ " + s1 + " | " + s2 + " | " + s3 + " ] ";

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

					if (displayExterno != null) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								displayExterno.setText(mensaje);
							}
						});
					}

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

	public boolean isPremio() {
		return premio;
	}

	public void setPremio(boolean premio) {
		this.premio = premio;
	}
}
