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
					+ "idJ STRING PRIMARY KEY," // Usamos idJ como clave primaria
					+ "jugadorNom STRING,"
					+ "jugadorApell STRING,"
					+ "usuario STRING UNIQUE," // El usuario debe ser único
					+ "contrasenia STRING,"
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
	/*Devuelve true si el alumno cuyo dni recibe por parámetro está en la tabla Historial de la bbdd y false en caso contrario*/
	public boolean buscarJugador(String idJ){
	    // Había una línea con la tabla "Alumno" que no existe, la reemplazamos por "Historial"
	    String sql = String.format("SELECT * FROM Historial WHERE idJ = '%s'", idJ); 
	    
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
				
				String idJ = rs.getString(1); // Columna 1 es 'idJ'
				String jugadorNom = rs.getString(2); // Columna 2 es 'jugadorNom'
				String jugadorApell = rs.getString(3); // Columna 3 es 'jugadorApell'
				int ganadas = rs.getInt(4); // Columna 4 es 'ganadas'
				int perdidas = rs.getInt(5); // Columna 5 es 'perdidas'
				
				
				lHistorial.add(new Historial(jugadorNom, jugadorApell, idJ, ganadas, perdidas));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lHistorial;
	}
	
	public boolean registrarNuevoJugador(String idJ, String jugadorNom, String jugadorApell, String usuario, String contrasenia) {
		
		if(buscarJugador(idJ) || buscarUsuario(usuario)) {
			return false; 
		}

	
		String sql = "INSERT INTO Historial (idJ, jugadorNom, jugadorApell, usuario, contrasenia, ganadas, perdidas) "
		           + "VALUES(?, ?, ?, ?, ?, 0, 0)"; 
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, idJ);
			ps.setString(2, jugadorNom);
			ps.setString(3, jugadorApell);
			ps.setString(4, usuario);
			ps.setString(5, contrasenia);
			
			int filas = ps.executeUpdate();
			ps.close();
			return filas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean buscarUsuario(String usuario){
		String sql = String.format("SELECT * FROM Historial WHERE usuario = '%s'", usuario);
		
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
			e.printStackTrace();
		}
		return enc;
	}
	public boolean verificarCredenciales(String usuario, String contrasenia) {
		
		String sql = "SELECT idJ FROM Historial WHERE usuario = ? AND contrasenia = ?";
		
		boolean enc = false;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, usuario);
			ps.setString(2, contrasenia);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				enc = true; 
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enc;
	}
}
	
	
	
	
	
	
	