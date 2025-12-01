
package main;

import bd.ConexionBD;

public class Ajedrez {
	public static void main(String[] args) {
		
		
		ConexionBD bd = new ConexionBD();
		bd.initBD("deustochess.db"); 
		bd.crearTablas(); 
		
	
		
		new gui.VentanaInicioSesion(bd); 
	}
}