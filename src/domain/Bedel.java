package domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Bedel extends Pieza {
	
	protected boolean haUsadoHabilidad;
	protected boolean expediente;
	protected boolean expulsion;
	
	public Bedel(String nombre, Color color, int fila, int columna, boolean haUsadoHabilidad) {
		super(nombre, color, fila, columna);
		this.haUsadoHabilidad = haUsadoHabilidad;
	}
	
	public boolean isHaUsadoHabilidad() {
		return haUsadoHabilidad;
	}
	
	public void setHaUsadoHabilidad(boolean haUsadoHabilidad) {
		this.haUsadoHabilidad = haUsadoHabilidad;
	}

	@Override
	public boolean movimientoValido(int nuevaFila, int nuevaColumna, Tablero tablero) {
		if (nuevaFila < 0 || nuevaFila > 7 || nuevaColumna < 0 || nuevaColumna > 7) {
			return false;
		}
		
		if (nuevaFila == this.fila && nuevaColumna == this.columna) {
			return false;
		}
		
		int difFila = Math.abs(nuevaFila - this.fila);
		int difColumna = Math.abs(nuevaColumna - this.columna);
		
		// Movimiento en línea recta
		boolean movimientoRecto = (nuevaFila == this.fila || nuevaColumna == this.columna);
		
		// Movimiento en diagonal
		boolean movimientoDiagonal = (difFila == difColumna && difFila > 0);
		
		// El movimiento debe ser recto o diagonal
		if (!movimientoRecto && !movimientoDiagonal) {
			return false;
		}
		
		int direccionFila = Integer.signum(nuevaFila - this.fila);
		int direccionColumna = Integer.signum(nuevaColumna - this.columna);
		
		int filaActual = this.fila + direccionFila;
		int columnaActual = this.columna + direccionColumna;
		
		while (filaActual != nuevaFila || columnaActual != nuevaColumna) {
			// Si hay una pieza en el camino, el movimiento no es válido
			if (tablero.getCasillas(filaActual, columnaActual).getPieza() != null) {
				return false;
			}
			
			// Avanzar a la siguiente casilla
			filaActual += direccionFila;
			columnaActual += direccionColumna;
		}
		

		Pieza piezaDestino = tablero.getCasillas(nuevaFila, nuevaColumna).getPieza();
		
		return (piezaDestino == null || piezaDestino.getColor() != this.color);
	}

	@Override
	public void usarHabilidad(Tablero tablero) {
		//comprobar si ya se ha usado
		if (haUsadoHabilidad) {
			JOptionPane.showMessageDialog(null, "Este Bedel ya ha hecho una limpieza general.", "Habilidad ya usada", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		//calcular todas las piezas enemigas visibles
		List<Casilla> objetivos = obtenerObjetivosVisibles(tablero);

		if (objetivos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay ninguna pieza enemiga visible en línea recta o diagonal.\n" + 
			"El Bedel no tiene nada que limpiar por ahora.", "Sin objetivos", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		//construir lista de opciones para el jugador
		String[] opciones = new String[objetivos.size()];
		for (int i = 0; i < objetivos.size(); i++) {
			Casilla c = objetivos.get(i);
			Pieza p = c.getPieza();
			
			
			char letraColumna = (char) ('A' + c.getColumna());
			int numeroFila = c.getFila() + 1;
			
			opciones[i] = p.getNombre() + " " + p.getColor() + " en " + letraColumna + numeroFila;
		}

		//preguntar al usuario que pieza quiere eliminar
		Object seleccion = JOptionPane.showInputDialog(null, "Selecciona la pieza enemiga que el Bedel va a eliminar:", 
				"Limpieza general del Bedel", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion == null) {
			return;
		}

		//localizar la casilla seleccionada
		int indice = -1;
		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i].equals(seleccion)) {
				indice = i;
				break;
			}
		}

		if (indice == -1) {
			return;
		}

		Casilla casillaObjetivo = objetivos.get(indice);
		Pieza piezaEliminada = casillaObjetivo.getPieza();

		casillaObjetivo.setPieza(null);

		haUsadoHabilidad = true;

		char letraColumna = (char) ('A' + casillaObjetivo.getColumna());
		int numeroFila = casillaObjetivo.getFila() + 1;

		JOptionPane.showMessageDialog(
				null,
				"El Bedel ha realizado una limpieza general.\n" +
				"Se ha eliminado a " + piezaEliminada.getNombre() + " " + piezaEliminada.getColor() +
				" en " + letraColumna + numeroFila ,
				"Pieza eliminada",
				JOptionPane.INFORMATION_MESSAGE
		);
	}
	

	private List<Casilla> obtenerObjetivosVisibles(Tablero tablero) {
		List<Casilla> lista = new ArrayList<>();
		
		int[][] direcciones = {
				{-1,  0}, 
				{ 1,  0}, 
				{ 0, -1}, 
				{ 0,  1}, 
				{-1, -1}, 
				{-1,  1},
				{ 1, -1},
				{ 1,  1}
		};
		
		for (int[] dir : direcciones) {
			int f = this.fila + dir[0];
			int c = this.columna + dir[1];
			
			while (f >= 0 && f < 8 && c >= 0 && c < 8) {
				Casilla casilla = tablero.getCasillas(f, c);
				Pieza pieza = casilla.getPieza();
				
				if (pieza != null) {
					// Si es enemiga, se añade como objetivo
					if (pieza.getColor() != this.color) {
						lista.add(casilla);
					}
					
					break;
				}
				
				f += dir[0];
				c += dir[1];
			}
		}
		
		return lista;
	}
}				