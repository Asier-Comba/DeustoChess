package domain;



public class Tablero {
	private Casilla[][] casillas;
	
	public Tablero() {
		// === CREAMOS LA MATRIZ DE 8x8 ===
		casillas = new Casilla[8][8];
		
		// === Inicializamos las casillas vacías ===
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				casillas[i][j] = new Casilla(i,j);
			}
		}
		
		colocacionPiezas();
	}

	public Casilla getCasillas(int fila, int columna) {
		return casillas[fila][columna];
	}
	
	public void casillaMatriz() {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Pieza pieza = casillas[i][j].getPieza();
				if (pieza != null) {
					System.out.println(pieza);
				}
			}
		}
	}
	
	// === METEMOS LAS PIEZAS DENTRO DE LA MATRIZ ===
	private void colocacionPiezas() {
		// === BLANCAS (FILAS 0 y 1) ===
		// Torres (Máquinas Expendedoras)
		casillas[0][0].setPieza(new MaquinaExpendedora("Maquina", null, null, Color.BLANCA, 0, 0, false));
		casillas[0][7].setPieza(new MaquinaExpendedora("Maquina", null, null, Color.BLANCA, 0, 7, false));
		
		// Caballos (Becarios)
		casillas[0][1].setPieza(new Becario("Becario", null, null, Color.BLANCA, 0, 1, false));
		casillas[0][6].setPieza(new Becario("Becario", null, null, Color.BLANCA, 0, 6, false));
		
		// Alfiles (Secretarias)
		casillas[0][2].setPieza(new Secretaria("Secretaria", null, null, Color.BLANCA, 0, 2, false));
		casillas[0][5].setPieza(new Secretaria("Secretaria", null, null, Color.BLANCA, 0, 5, false));
		
		// Reina (Bedel) - Nota: En ajedrez real, reina blanca va en casilla blanca (d1 -> 0,3)
		casillas[0][3].setPieza(new Bedel("Bedel", null, null, Color.BLANCA, 0, 3, false));
		
		// Rey (Rector)
		casillas[0][4].setPieza(new Rector("Rector", null, null, Color.BLANCA, 0, 4, false, false, false));
		
		// Peones (Alumnos)
		for (int j = 0; j < 8; j++) {
			casillas[1][j].setPieza(new Alumno("Alumno", null, null, Color.BLANCA, 1, j, false));
		}

		// === NEGRAS (FILAS 7 y 6) ===
		casillas[7][0].setPieza(new MaquinaExpendedora("Maquina", null, null, Color.NEGRA, 7, 0, false));
		casillas[7][7].setPieza(new MaquinaExpendedora("Maquina", null, null, Color.NEGRA, 7, 7, false));
		
		casillas[7][1].setPieza(new Becario("Becario", null, null, Color.NEGRA, 7, 1, false));
		casillas[7][6].setPieza(new Becario("Becario", null, null, Color.NEGRA, 7, 6, false));
		
		casillas[7][2].setPieza(new Secretaria("Secretaria", null, null, Color.NEGRA, 7, 2, false));
		casillas[7][5].setPieza(new Secretaria("Secretaria", null, null, Color.NEGRA, 7, 5, false));
		
		casillas[7][3].setPieza(new Rector("Rector", null, null, Color.NEGRA, 7, 3, false, false, false));
		casillas[7][4].setPieza(new Bedel("Bedel", null, null, Color.NEGRA, 7, 4, false));

		for (int j = 0; j < 8; j++) {
			casillas[6][j].setPieza(new Alumno("Alumno", null, null, Color.NEGRA, 6, j, false));
		}
	}
}