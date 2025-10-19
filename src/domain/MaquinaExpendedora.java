package domain;

public class MaquinaExpendedora extends Pieza{
	protected boolean haUsadoHabilidad;
	
	public MaquinaExpendedora(String nombre, Movimiento movimiento, HabilidadEspecial habilidad, String color, int fila,
			int columna, boolean haUsadoHabilidad) {
		super(nombre, movimiento, habilidad, color, fila, columna);
		this.haUsadoHabilidad = haUsadoHabilidad;

	}

	public boolean isHaUsadoHabilidad() {
		return haUsadoHabilidad;
	}

	public void setHaUsadoHabilidad(boolean haUsadoHabilidad) {
		this.haUsadoHabilidad = haUsadoHabilidad;
		
	}

	@Override
	public String toString() {
		return "MaquinaExpendedora [haUsadoHabilidad=" + haUsadoHabilidad + "]";
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
