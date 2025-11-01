package domain;

import javax.swing.JOptionPane;

public class Rector extends Pieza{
	protected boolean haUsadoHabilidad;
	protected boolean expediente;
	protected boolean expulsion;
	
	public Rector(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, Color color, int fila,
			int columna, boolean haUsadoHabilidad, boolean expediente, boolean expulsion) {
		super(nombre, movimiento, habilidad, color, fila, columna);
		this.haUsadoHabilidad = haUsadoHabilidad;
		this.expediente = expediente;
		this.expulsion = expulsion;
	}

	public boolean isHaUsadoHabilidad() {
		return haUsadoHabilidad;
	}

	public void setHaUsadoHabilidad(boolean haUsadoHabilidad) {
		this.haUsadoHabilidad = haUsadoHabilidad;
	}

	public boolean isExpediente() {
		return expediente;
	}

	public void setExpediente(boolean expediente) {
		this.expediente = expediente;
	}

	public boolean isExpulsion() {
		return expulsion;
	}

	public void setExpulsion(boolean expulsion) {
		this.expulsion = expulsion;
	}

	@Override
	public String toString() {
		return "Rector [haUsadoHabilidad=" + haUsadoHabilidad + ", expediente=" + expediente + ", expulsion="
				+ expulsion + "]";
	}

	@Override
	public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void usarHabilidad(Tablero tablero) {
		if (haUsadoHabilidad) {
		JOptionPane.showMessageDialog(null, "Ya se ha usado la habilidad en este turno");
		}
	}
	
	
	
}
