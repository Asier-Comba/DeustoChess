package domain;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Rector extends Pieza {
	
	private boolean haUsadoHabilidad;

	private boolean enExpediente; 
	private boolean jaqueMate;

	public Rector(String nombre, Color color, int fila, int columna, boolean haUsadoHabilidad, boolean enExpediente, boolean jaqueMate) {
		super(nombre, color, fila, columna);
		this.haUsadoHabilidad = haUsadoHabilidad;
		this.enExpediente = enExpediente;
		this.setJaqueMate(jaqueMate);
	}

	public boolean isHaUsadoHabilidad() {
		return haUsadoHabilidad;
	}

	public void setHaUsadoHabilidad(boolean haUsadoHabilidad) {
		this.haUsadoHabilidad = haUsadoHabilidad;
	}
	
	public boolean isEnExpediente() {
		return enExpediente; 
	}
	
	public void setEnExpediente(boolean enExpediente) {
		this.enExpediente = enExpediente; 
	}

	// === MOVIMIENTO ===
	@Override
	public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
		int difFila = Math.abs(nuevaFila - this.fila);
		int difCol = Math.abs(nuevaColumna - this.columna);
		
		return (difFila <= 1 && difCol <= 1) && !(difFila == 0 && difCol == 0);
	}

	// === HABILIDAD: REUNIÓN DE URGENCIA ===
	@Override
	public void usarHabilidad(Tablero tablero) {
		
		new Thread(() -> {
			
			if (haUsadoHabilidad) {
				msg("El Rector ya ha convocado una reunión de urgencia en esta partida.");
				return;
			}
			
			if (enExpediente) {
				msg("¡No puedes convocar reunión mientras estás en Expediente!");
				return;
			}

			try {
		
				System.out.println("Convocando a los decanos...");
				Thread.sleep(700);
				System.out.println("Enviando circulares urgentes...");
				Thread.sleep(700);
				System.out.println("Cerrando facultades...");
				Thread.sleep(600);

				// === APLICAR EFECTO ===
				tablero.setReunionUrgencia(true); 
				
				haUsadoHabilidad = true;

				System.out.println("¡Reunión convocada!");
				
				// Mensaje final
				String mensaje = "¡REUNIÓN DE URGENCIA!\n"
						+ "El campus se paraliza.\n"
						+ "En el siguiente turno, tu rival SOLO podrá mover ALUMNOS.";
				
				msg(mensaje);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	private void msg(String texto) {
		SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, texto));
	}

	public boolean isJaqueMate() {
		return jaqueMate;
	}

	public void setJaqueMate(boolean jaqueMate) {
		this.jaqueMate = jaqueMate;
	}
}