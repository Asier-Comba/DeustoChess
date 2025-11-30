package domain;

public abstract class  Pieza {
	protected String nombre;
	
	protected Color color;
	protected int fila;
	protected int columna;
	
	public abstract boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero);
	public abstract void usarHabilidad(Tablero tablero);
	
	public void moverPieza(int nuevaFila, int nuevaColumna) {
        this.fila = nuevaFila;
        this.columna = nuevaColumna;
    }
	
	
	public Pieza(String nombre, Color color, int fila, int columna) {
		super();
		this.nombre = nombre;
		this.color = color;
		this.fila = fila;
		this.columna = columna;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	
	@Override
	public String toString() {
		return "Pieza [nombre=" + nombre +  ", color=" + color + ", fila=" + fila + ", columna=" + columna + "]";
	}
	
	
}
