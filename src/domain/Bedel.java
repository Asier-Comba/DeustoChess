package domain;

import javax.swing.JOptionPane;

public class Bedel extends Pieza{
	protected boolean haUsadoHabilidad;
	protected boolean expediente;
	protected boolean expulsion;
	
	public Bedel(String nombre, Color color, int fila, int columna, boolean haUsadoHabilidad) {
		super(nombre, color, fila, columna);
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
		}
	}
}
