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
	
	
	// === MÉTODO PARA INICIALIZAR LA CONEXIÓN CON LA BASE DE DATOS ===
	
	public void initBD(String nombreBD)  {
	    
	    // === INICIALIZAR LA CONEXIÓN COMO NULA ===
	    con = null;
	    
	    try {
	        // === CARGAR EL DRIVER DE SQLITE ===
	        // Registra el driver JDBC de SQLite en el sistema
	        Class.forName("org.sqlite.JDBC");
	        
	        // === ESTABLECER LA CONEXIÓN CON LA BASE DE DATOS ===
	        // Crea la conexión usando la URL jdbc:sqlite: + nombre del archivo
	        con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD);
	        
	    } catch (ClassNotFoundException e) {
	        // === MANEJO DE ERROR SI NO SE ENCUENTRA EL DRIVER ===
	        e.printStackTrace();
	        
	    } catch (SQLException e) {
	        // === MANEJO DE ERROR SI FALLA LA CONEXIÓN ===
	        e.printStackTrace();
	    }
	}

	
	
	
	
	
	
	// === MÉTODO PARA CERRAR LA CONEXIÓN CON LA BASE DE DATOS ===
	
	public void closeBD() {
	    
	    // === VERIFICAR QUE LA CONEXIÓN EXISTA ===
	    // Solo intenta cerrar si hay una conexión activa
	    if (con != null) {
	        
	        try {
	            // === CERRAR LA CONEXIÓN ===
	            con.close();
	            
	        } catch (SQLException e) {
	            // === MANEJO DE ERRORES AL CERRAR ===
	            e.printStackTrace();
	        }
	    }
	}
	
	
	
	
	
	// === MÉTODO PARA CREAR LA TABLA HISTORIAL EN LA BASE DE DATOS ===
	
	public void crearTablas() {
	    
	    // === DEFINICIÓN DE LA ESTRUCTURA DE LA TABLA HISTORIAL ===
	    // Crea una tabla con todos los campos necesarios para los jugadores
	    String sql = "CREATE TABLE IF NOT EXISTS Historial ("
	                + "idJ STRING PRIMARY KEY,"        // ID único del jugador (clave primaria)
	                + "jugadorNom STRING,"              // Nombre del jugador
	                + "jugadorApell STRING,"            // Apellidos del jugador
	                + "usuario STRING UNIQUE,"          // Nombre de usuario (debe ser único)
	                + "contrasenia STRING,"             // Contraseña del jugador
	                + "ganadas INTEGER,"                // Número de partidas ganadas
	                + "perdidas INTEGER);";             // Número de partidas perdidas
	    
	    try {
	        // === CREAR SENTENCIA SQL ===
	        Statement st = con.createStatement();
	        
	        // === EJECUTAR LA CREACIÓN DE LA TABLA ===
	        st.executeUpdate(sql);
	        
	        // === CERRAR RECURSOS ===
	        st.close();
	        
	    } catch (SQLException e) {
	        // === MANEJO DE ERRORES DE BASE DE DATOS ===
	        e.printStackTrace();
	    }
	}

	
	
	
	
	
	// === MÉTODO PARA BUSCAR SI UN JUGADOR YA EXISTE POR SU ID ===
	
	public boolean buscarJugador(String idJ){
	    
	    // === CONSULTA SQL PARA BUSCAR JUGADOR POR ID ===
	    // Busca cualquier registro en la tabla Historial con ese ID
	    String sql = String.format("SELECT * FROM Historial WHERE idJ = '%s'", idJ);
	    
	    // === VARIABLE PARA ALMACENAR SI SE ENCONTRÓ EL JUGADOR ===
	    boolean enc = false;
	    
	    try {
	        // === CREAR SENTENCIA SQL ===
	        Statement st = con.createStatement();
	        
	        // === EJECUTAR LA CONSULTA ===
	        ResultSet rs = st.executeQuery(sql);
	        
	        // === VERIFICAR SI EXISTE ALGÚN RESULTADO ===
	        if(rs.next()) {
	            enc = true; // Si hay resultados, el jugador ya existe
	        }
	        
	        // === CERRAR RECURSOS ===
	        rs.close();
	        st.close();
	        
	    } catch (SQLException e) {
	        // === MANEJO DE ERRORES DE BASE DE DATOS ===
	        e.printStackTrace();
	    }
	    
	    // === DEVOLVER SI EL JUGADOR FUE ENCONTRADO ===
	    return enc;
	}
	
	
	
	
	
	
	// === MÉTODO PARA INSERTAR UN NUEVO JUGADOR (VERSIÓN SIMPLIFICADA) ===
	
	public void insertarNuevoJugador(String idJ, String jugadorNom, String jugadorApell, int ganadas, int perdidas) {
	    
	    // === VERIFICAR QUE EL JUGADOR NO EXISTA ===
	    // Solo procede con la inserción si el ID no está registrado
	    if(!buscarJugador(idJ)) {
	        
	        // === CONSULTA SQL PARA INSERTAR JUGADOR ===
	        // Inserta directamente los valores en la tabla Historial
	        String sql = String.format("INSERT INTO Historial VALUES('%s','%s','%s', %d, %d)", 
	                                   idJ, jugadorNom, jugadorApell, ganadas, perdidas);
	        
	        try {
	            // === CREAR SENTENCIA SQL ===
	            Statement st = con.createStatement();
	            
	            // === EJECUTAR LA INSERCIÓN ===
	            st.executeUpdate(sql);
	            
	            // === CERRAR RECURSOS ===
	            st.close();
	            
	        } catch (SQLException e) {
	            // === MANEJO DE ERRORES DE BASE DE DATOS ===
	            e.printStackTrace();
	        }
	    }
	}
	
	
	
	

	// === MÉTODO PARA ELIMINAR UN JUGADOR DE LA BASE DE DATOS ===
	
	public void borrarJugador(String idJ) {
	    
	    // === CONSULTA SQL PARA ELIMINAR JUGADOR ===
	    // Busca y elimina el registro que coincida con el ID proporcionado
	    String sql = "DELETE FROM Historial WHERE idJ = '" + idJ + "'";
	    sql = String.format("DELETE FROM Historial WHERE idJ = '%s'", idJ);
	    
	    try {
	        // === CREAR SENTENCIA SQL ===
	        Statement st = con.createStatement();
	        
	        // === EJECUTAR LA ELIMINACIÓN ===
	        st.executeUpdate(sql);
	        
	        // === CERRAR RECURSOS ===
	        st.close();
	        
	    } catch (SQLException e) {
	        // === MANEJO DE ERRORES DE BASE DE DATOS ===
	        e.printStackTrace();
	    }
	}
	
	
	
	
	

	// === MÉTODO PARA OBTENER TODOS LOS JUGADORES DE LA BASE DE DATOS ===

	public List<Historial> obtenerJugador() {
	    
	    // === CREAR LISTA PARA ALMACENAR LOS JUGADORES ===
	    List<Historial> lHistorial = new ArrayList<>();
	    
	    // === CONSULTA SQL PARA OBTENER TODOS LOS REGISTROS ===
	    String sql = "SELECT idJ, jugadorNom, jugadorApell, ganadas, perdidas FROM Historial";
	    
	    try {
	        // === PREPARAR LA SENTENCIA SQL ===
	        PreparedStatement ps = con.prepareStatement(sql);
	        
	        // === EJECUTAR LA CONSULTA ===
	        ResultSet rs = ps.executeQuery();
	        
	        // === RECORRER TODOS LOS RESULTADOS ===
	        while(rs.next()) {
	            
	            // === EXTRAER DATOS DE CADA COLUMNA ===
	            String idJ = rs.getString(1);           // Columna 1 es 'idJ'
	            String jugadorNom = rs.getString(2);    // Columna 2 es 'jugadorNom'
	            String jugadorApell = rs.getString(3);  // Columna 3 es 'jugadorApell'
	            int ganadas = rs.getInt(4);             // Columna 4 es 'ganadas'
	            int perdidas = rs.getInt(5);            // Columna 5 es 'perdidas'
	            
	            // === CREAR OBJETO HISTORIAL Y AÑADIRLO A LA LISTA ===
	            lHistorial.add(new Historial(jugadorNom, jugadorApell, idJ, ganadas, perdidas));
	        }
	        
	        // === CERRAR RECURSOS ===
	        rs.close();
	        ps.close();
	        
	    } catch (SQLException e) {
	        // === MANEJO DE ERRORES DE BASE DE DATOS ===
	        e.printStackTrace();
	    }
	    
	    // === DEVOLVER LA LISTA DE JUGADORES ===
	    return lHistorial;
	}
	
	
	
	
	
	
	// === MÉTODO PARA REGISTRAR UN NUEVO JUGADOR EN LA BASE DE DATOS ===

	public boolean registrarNuevoJugador(String idJ, String jugadorNom, String jugadorApell, String usuario, String contrasenia) {
	    
	    // === VERIFICAR SI EL ID O USUARIO YA EXISTEN ===
	    // Si el jugador o usuario ya están registrados, no se permite duplicados
	    if(buscarJugador(idJ) || buscarUsuario(usuario)) {
	        return false; // Registro rechazado por duplicado
	    }

	    // === CONSULTA SQL PARA INSERTAR NUEVO JUGADOR ===
	    // Inserta todos los datos del jugador, inicializando ganadas y perdidas en 0
	    String sql = "INSERT INTO Historial (idJ, jugadorNom, jugadorApell, usuario, contrasenia, ganadas, perdidas) "
	               + "VALUES(?, ?, ?, ?, ?, 0, 0)";
	    
	    try {
	        // === PREPARAR LA SENTENCIA SQL ===
	        PreparedStatement ps = con.prepareStatement(sql);
	        
	        // === ASIGNAR VALORES A LOS PARÁMETROS DE LA CONSULTA ===
	        ps.setString(1, idJ);           // ID único del jugador
	        ps.setString(2, jugadorNom);    // Nombre del jugador
	        ps.setString(3, jugadorApell);  // Apellidos del jugador
	        ps.setString(4, usuario);       // Nombre de usuario (único)
	        ps.setString(5, contrasenia);   // Contraseña del usuario
	        
	        // === EJECUTAR LA INSERCIÓN ===
	        int filas = ps.executeUpdate();
	        
	        // === CERRAR RECURSOS ===
	        ps.close();
	        
	        // === VERIFICAR SI SE INSERTÓ CORRECTAMENTE ===
	        return filas > 0; // True si se insertó al menos 1 fila
	        
	    } catch (SQLException e) {
	        // === MANEJO DE ERRORES DE BASE DE DATOS ===
	        e.printStackTrace();
	        return false; // Registro fallido por error SQL
	    }
	}

	
	
	
	
	// === MÉTODO PARA BUSCAR SI UN USUARIO YA EXISTE ===

	public boolean buscarUsuario(String usuario){
	    
	    // === CONSULTA SQL PARA BUSCAR USUARIO ===
	    // Busca cualquier registro en la tabla Historial con ese nombre de usuario
	    String sql = String.format("SELECT * FROM Historial WHERE usuario = '%s'", usuario);
	    
	    // === VARIABLE PARA ALMACENAR SI SE ENCONTRÓ EL USUARIO ===
	    boolean enc = false;
	    
	    try {
	        // === CREAR SENTENCIA SQL ===
	        Statement st = con.createStatement();
	        
	        // === EJECUTAR LA CONSULTA ===
	        ResultSet rs = st.executeQuery(sql);
	        
	        // === VERIFICAR SI EXISTE ALGÚN RESULTADO ===
	        if(rs.next()) {
	            enc = true; // Si hay resultados, el usuario ya existe
	        }
	        
	        // === CERRAR RECURSOS ===
	        rs.close();
	        st.close();
	        
	    } catch (SQLException e) {
	        // === MANEJO DE ERRORES DE BASE DE DATOS ===
	        e.printStackTrace();
	    }
	    
	    // === DEVOLVER SI EL USUARIO FUE ENCONTRADO ===
	    return enc;
	}
	
	
	
	
	
	// === MÉTODO PARA VERIFICAR CREDENCIALES DE INICIO DE SESIÓN ===
	
	public boolean verificarCredenciales(String usuario, String contrasenia) {
	    
	    // === CONSULTA SQL CON PARÁMETROS PREPARADOS ===
	    // Busca en la tabla Historial si existe un registro con el usuario y contraseña dados
	    String sql = "SELECT idJ FROM Historial WHERE usuario = ? AND contrasenia = ?";
	    
	    // === VARIABLE PARA ALMACENAR SI SE ENCONTRÓ EL USUARIO ===
	    boolean enc = false;
	    
	    try {
	        // === PREPARAR LA SENTENCIA SQL ===
	        PreparedStatement ps = con.prepareStatement(sql);
	        
	        // === ASIGNAR VALORES A LOS PARÁMETROS DE LA CONSULTA ===
	        ps.setString(1, usuario);      // Primer parámetro: usuario
	        ps.setString(2, contrasenia);  // Segundo parámetro: contraseña
	        
	        // === EJECUTAR LA CONSULTA ===
	        ResultSet rs = ps.executeQuery();
	        
	        // === VERIFICAR SI SE ENCONTRÓ ALGÚN RESULTADO ===
	        if(rs.next()) {
	            enc = true; // Si hay resultados, las credenciales son válidas
	        }
	        
	        // === CERRAR RECURSOS ===
	        rs.close();
	        ps.close();
	        
	    } catch (SQLException e) {
	        // === MANEJO DE ERRORES DE BASE DE DATOS ===
	        e.printStackTrace();
	    }
	    
	    // === DEVOLVER RESULTADO DE LA VERIFICACIÓN ===
	    return enc;
	}

	
	
	
	// === MÉTODO PARA CONTAR JUGADORES REGISTRADOS ===
	
	public int obtenerCantidadJugadores() {
	    // === CONSULTA SQL PARA CONTAR REGISTROS ===
	    String sql = "SELECT COUNT(*) FROM Historial";
	    int cantidad = 0;
	    
	    try {
	        // === EJECUTAR CONSULTA EN LA BASE DE DATOS ===
	        Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        
	        // === OBTENER EL RESULTADO DEL CONTEO ===
	        if (rs.next()) {
	            cantidad = rs.getInt(1);
	        }
	        
	        // === CERRAR RECURSOS ===
	        rs.close();
	        st.close();
	    } catch (SQLException e) {
	        System.err.println("Error al obtener cantidad de jugadores: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    // === DEVOLVER LA CANTIDAD DE JUGADORES ===
	    return cantidad;
	}
}
	
	
	
	
	
	
	