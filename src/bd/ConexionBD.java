package bd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConexionBD {

	private Connection con; 
	
	public void initBD(String nombreBD)  {
		con = null;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeBD() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void crearTablas() {

		String sql = "CREATE TABLE IF NOT EXISTS Historial ("
					+ "idJ STRING,"
					+ "jugadorNom STRING,"
					+ "jugadorApell STRING,"
					+ "ganadas INTEGER,"
					+ "perdidas INTEGER);";
		
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*Devuelve true si el alumno cuyo dni recibe por parámetro está en la tabla Alumno de la bbdd y false en caso contrario*/
	public boolean buscarJugador(String idJ){
		String sql = "SELECT * FROM Historial WHERE idJ = '" + idJ + "'";
		sql = String.format("SELECT * FROM Alumno WHERE idJ = '%s'", idJ);
		
		boolean enc = false;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				enc = true;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enc;
	}
	
	/*Insertar un nuevo alumno*/
	public void insertarNuevoJugador(String idJ, String jugadorNom, String jugadorApell, int ganadas, int perdidas) {
		
		if(!buscarJugador(idJ)) {
	 		String sql = String.format("INSERT INTO Historial VALUES('%s','%s','%s', %d, %d)", idJ, jugadorNom, jugadorApell, ganadas, perdidas);
	 		try {
				Statement st = con.createStatement();
				st.executeUpdate(sql);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*Borrar un determinado alumno*/
	public void borrarJugador(String idJ) {
		String sql = "DELETE FROM Historial WHERE idJ = '" +idJ + "'";
		sql = String.format("DELETE FROM Historial WHERE idJ = '%s'", idJ);
		
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public List<Historial> obtenerJugador() {
		List<Historial> lHistorial = new ArrayList<>();
		String sql = "SELECT * FROM Historial";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String jugadorNom = rs.getString(1);
				String jugadorApell = rs.getString(2);
				String idJ = rs.getString(3);
				int ganadas = rs.getInt(4);
				int perdidas = rs.getInt(5);
				Historial h = new Historial(jugadorNom, jugadorApell, idJ, ganadas, perdidas);
				lHistorial.add(h);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lHistorial;
	}

}
	
	
	
	
	
	
	