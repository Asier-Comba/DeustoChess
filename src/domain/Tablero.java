package domain;

public class Tablero {
	private Casilla[][] casillas;
	
	public Tablero() {
		// === CREAMOS LA MATRIZ DE 8x8 ===
		casillas = new Casilla[8][8];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				casillas[i][j] = new Casilla(i,j);
			}
		}
	}
	// === HACEMOS EL MÉTODO GETTER ===
	public Casilla getCasillas(int fila, int columna) {
		return casillas[fila][columna];
	}
	// === RECORREMOS LA MATRIZ ===
	public void casillaMatriz() {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				// === ASIGNAMOS LA PIEZA ===
				Pieza pieza = casillas[i][j].getPieza();
				// === LA MOSTRAMOS === 
				System.out.println(pieza);
			}
		}
	}
	
	// === PARA QUE NO DE ERROR ===
	@SuppressWarnings("unused")
	// === METEMOS LAS PIEZAS DENTRO DE LA MATRIZ ===
	private void colocacionPiezas() {
		casillas[0][0].setPieza(new MaquinaExpendedora(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[0][7].setPieza(new MaquinaExpendedora(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[7][0].setPieza(new MaquinaExpendedora(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[7][7].setPieza(new MaquinaExpendedora(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[0][1].setPieza(new Becario(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[0][6].setPieza(new Becario(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[7][1].setPieza(new Becario(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[7][6].setPieza(new Becario(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[0][2].setPieza(new Secretaria(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[0][5].setPieza(new Secretaria(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[7][2].setPieza(new Secretaria(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[7][5].setPieza(new Secretaria(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[0][3].setPieza(new Bedel(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[7][4].setPieza(new Bedel(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[0][4].setPieza(new Rector(null, null, null, Color.BLANCA, 0, 0, false, false, false));
		casillas[7][3].setPieza(new Rector(null, null, null, Color.NEGRA, 0, 0, false, false, false));
		casillas[1][0].setPieza(new Alumno(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[1][1].setPieza(new Alumno(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[1][2].setPieza(new Alumno(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[1][3].setPieza(new Alumno(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[1][4].setPieza(new Alumno(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[1][5].setPieza(new Alumno(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[1][6].setPieza(new Alumno(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[1][7].setPieza(new Alumno(null, null, null, Color.BLANCA, 0, 0, false));
		casillas[6][0].setPieza(new Alumno(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[6][1].setPieza(new Alumno(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[6][2].setPieza(new Alumno(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[6][3].setPieza(new Alumno(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[6][4].setPieza(new Alumno(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[6][5].setPieza(new Alumno(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[6][6].setPieza(new Alumno(null, null, null, Color.NEGRA, 0, 0, false));
		casillas[6][7].setPieza(new Alumno(null, null, null, Color.NEGRA, 0, 0, false));
	}
	
}
