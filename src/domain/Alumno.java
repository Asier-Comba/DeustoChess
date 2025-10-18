package domain;

public class Alumno extends Pieza{
	protected boolean haUsadoHabilidad;
	protected boolean expediente;
	protected boolean expulsion;
	
	public Alumno(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, String color, int fila,
			int columna, boolean haUsadoHabilidad, boolean expediente, boolean expulsion) {
		super(nombre, movimiento, habilidad, color, fila, columna);
		// TODO Auto-generated constructor stub
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
	public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void usarHabilidad(Tablero tablero) {
		// TODO Auto-generated method stub
		
	}
}
