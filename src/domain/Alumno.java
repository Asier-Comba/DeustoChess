package domain;

import javax.swing.JOptionPane;

public class Alumno extends Pieza{
	protected boolean haUsadoHabilidad;
	protected boolean expediente;
	protected boolean expulsion;
	
	public Alumno(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, String color, int fila,
			int columna, boolean haUsadoHabilidad) {
		super(nombre, movimiento, habilidad, color, fila, columna);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean isHaUsadoHabilidad() {
		return haUsadoHabilidad;
	}


	public void setHaUsadoHabilidad(boolean haUsadoHabilidad) {
		this.haUsadoHabilidad = haUsadoHabilidad;
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
