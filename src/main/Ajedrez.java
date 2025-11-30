package main;

import bd.ConexionBD;

public class Ajedrez {
	public static void main(String[] args) {
		
		
		ConexionBD bd = new ConexionBD();
		bd.initBD("deustochess.db"); 
		bd.crearTablas(); 
		
		// Lógica de inserción de prueba (opcional, puedes borrarla si no la necesitas)
//		bd.insertarNuevoJugador("Julen", "Martínez", "49395948Y", 234, 54);
//		bd.insertarNuevoJugador("Markel", "Díaz", "29384869J", 532, 134);
//		bd.insertarNuevoJugador("Mikel", "González", "49525824J", 865, 534);
//		bd.insertarNuevoJugador("Javier", "Pérez", "49672456Z", 84, 4);
//		System.out.println(bd.obtenerJugador());
		bd.closeBD();

		
		new gui.VentanaInicioSesion(bd); 
	}
}