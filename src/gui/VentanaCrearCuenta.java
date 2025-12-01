package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import bd.ConexionBD;

public class VentanaCrearCuenta extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnIniciarSesion, btnCrearCuenta;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JTextField nomUsuario;
	private JTextField apellidosUsuario;
	private JFrame ventanaAnterior, ventanaActual;
	
	private ConexionBD bd;

	public VentanaCrearCuenta(JFrame va, ConexionBD bd) {
		this.setBd(bd);
		ventanaActual = this;
		ventanaAnterior = va;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,700);
		setTitle("Crear cuenta - DeustoChess");
		setResizable(false);
		ImageIcon im = new ImageIcon("img/LogoDeustoChess.png");
		setIconImage(im.getImage());
		
		PanelConFondo fondo = new PanelConFondo("/images/crearCuenta.jpg");
		setContentPane(fondo);
		
		fondo.setLayout(null);

		
		// === CREACIÓN DE BOTONES ===
		
		btnIniciarSesion = new JButton("");
		btnCrearCuenta = new JButton("");
		txtUsuario = new JTextField();
		txtContrasenia = new JPasswordField();
		nomUsuario = new JTextField();
		apellidosUsuario = new JTextField();
		
		
		
		// === CONFIGURACIÓN DEL CAMPO DE TEXTO USUARIO CON PLACEHOLDER ===
		// Establece el texto "Usuario" que desaparece al escribir
		String placeholderUsuario = "Usuario";
		// === ESTABLECER TEXTO INICIAL DEL PLACEHOLDER ===
		txtUsuario.setText(placeholderUsuario);

		
		txtUsuario.setForeground(Color.gray);
		txtUsuario.setHorizontalAlignment(JTextField.CENTER);
		txtUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		txtUsuario.setOpaque(false);
		txtUsuario.setBorder(null);
		
		txtUsuario.addFocusListener(new FocusAdapter() {
		    
		    // === CUANDO EL USUARIO HACE CLIC EN EL CAMPO ===
		    @Override
		    public void focusGained(FocusEvent e) {
		        // === VERIFICAR SI EL TEXTO ES EL PLACEHOLDER ===
		        if (txtUsuario.getText().equals(placeholderUsuario)) {
		            // === LIMPIAR EL CAMPO ===
		            txtUsuario.setText("");
		            // === CAMBIAR COLOR A NEGRO PARA EL TEXTO REAL ===
		            txtUsuario.setForeground(Color.BLACK);
		        }
		    }
		    
		    // === CUANDO EL USUARIO SALE DEL CAMPO ===
		    @Override
		    public void focusLost(FocusEvent e) {
		        // === VERIFICAR SI EL CAMPO QUEDÓ VACÍO ===
		        if (txtUsuario.getText().isEmpty()) {
		            // === RESTAURAR COLOR GRIS DEL PLACEHOLDER ===
		            txtUsuario.setForeground(Color.GRAY);
		            // === MOSTRAR NUEVAMENTE EL PLACEHOLDER ===
		            txtUsuario.setText(placeholderUsuario);
		        }
		    }
		});
		
		
		
		// === CONFIGURACIÓN DEL CAMPO DE CONTRASEÑA CON PLACEHOLDER ===
		// Establece el texto de ayuda "Contraseña" que desaparece al escribir
		String placeholderContra = "Contraseña";

		// === GUARDAR EL CARÁCTER DE OCULTACIÓN PREDETERMINADO (•) ===
		// Se guarda para restaurarlo cuando el usuario escriba la contraseña real
		char defaultEchoChar = txtContrasenia.getEchoChar();

		// === ESTABLECER TEXTO INICIAL DEL PLACEHOLDER ===
		txtContrasenia.setText(placeholderContra);
		txtContrasenia.setForeground(Color.GRAY);
		txtContrasenia.setEchoChar((char) 0);
		txtContrasenia.setHorizontalAlignment(JPasswordField.CENTER);
		txtContrasenia.setFont(new Font("Arial", Font.BOLD, 18));
		txtContrasenia.setOpaque(false);
		txtContrasenia.setBorder(null);
		txtContrasenia.addFocusListener(new FocusAdapter() {
		    
		    // === CUANDO EL USUARIO HACE CLIC EN EL CAMPO ===
		    @Override
		    public void focusGained(FocusEvent e) {
		        // === OBTENER EL TEXTO ACTUAL ===
		        String pass = new String(txtContrasenia.getPassword());
		        
		        // === VERIFICAR SI EL TEXTO ES EL PLACEHOLDER ===
		        if (pass.equals(placeholderContra)) {
		            // === LIMPIAR EL CAMPO ===
		            txtContrasenia.setText("");
		            // === CAMBIAR COLOR A NEGRO PARA EL TEXTO REAL ===
		            txtContrasenia.setForeground(Color.BLACK);
		            // === ACTIVAR OCULTACIÓN DE CONTRASEÑA (•••) ===
		            txtContrasenia.setEchoChar(defaultEchoChar);
		        }
		    }
		    
		    // === CUANDO EL USUARIO SALE DEL CAMPO ===
		    @Override
		    public void focusLost(FocusEvent e) {
		        // === OBTENER EL TEXTO ACTUAL ===
		        String pass = new String(txtContrasenia.getPassword());
		        
		        // === VERIFICAR SI EL CAMPO QUEDÓ VACÍO ===
		        if (pass.isEmpty()) {
		            // === RESTAURAR COLOR GRIS DEL PLACEHOLDER ===
		            txtContrasenia.setForeground(Color.GRAY);
		            // === MOSTRAR NUEVAMENTE EL PLACEHOLDER ===
		            txtContrasenia.setText(placeholderContra);
		            // === DESACTIVAR OCULTACIÓN PARA MOSTRAR PLACEHOLDER ===
		            txtContrasenia.setEchoChar((char) 0);
		        }
		    }
		});

		// === CONFIGURACIÓN DEL CAMPO DE NOMBRE CON PLACEHOLDER ===
		// Establece el texto "Nombre" que desaparece al escribir
		String placeholderNom = "Nombre";

		// === ESTABLECER TEXTO INICIAL DEL PLACEHOLDER ===
		nomUsuario.setText(placeholderNom);
		nomUsuario.setForeground(Color.gray);
		nomUsuario.setHorizontalAlignment(JTextField.CENTER);
		nomUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		nomUsuario.setOpaque(false);
		nomUsuario.setBorder(null);
		nomUsuario.addFocusListener(new FocusAdapter() {
		    
		    // === CUANDO EL USUARIO HACE CLIC EN EL CAMPO ===
		    @Override
		    public void focusGained(FocusEvent e) {
		        // === VERIFICAR SI EL TEXTO ES EL PLACEHOLDER ===
		        if (nomUsuario.getText().equals(placeholderNom)) {
		            // === LIMPIAR EL CAMPO ===
		            nomUsuario.setText("");
		            // === CAMBIAR COLOR A NEGRO PARA EL TEXTO REAL ===
		            nomUsuario.setForeground(Color.BLACK);
		        }
		    }
		    
		    // === CUANDO EL USUARIO SALE DEL CAMPO ===
		    @Override
		    public void focusLost(FocusEvent e) {
		        // === VERIFICAR SI EL CAMPO QUEDÓ VACÍO ===
		        if (nomUsuario.getText().isEmpty()) {
		            // === RESTAURAR COLOR GRIS DEL PLACEHOLDER ===
		            nomUsuario.setForeground(Color.GRAY);
		            // === MOSTRAR NUEVAMENTE EL PLACEHOLDER ===
		            nomUsuario.setText(placeholderNom);
		        }
		    }
		});

		// === CONFIGURACIÓN DEL CAMPO DE APELLIDOS CON PLACEHOLDER ===
		// Establece el texto "Apellidos" que desaparece al escribir
		String placeholderApe = "Apellidos";

		// === ESTABLECER TEXTO INICIAL DEL PLACEHOLDER ===
		apellidosUsuario.setText(placeholderApe);
		apellidosUsuario.setForeground(Color.gray);
		apellidosUsuario.setHorizontalAlignment(JTextField.CENTER);
		apellidosUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		apellidosUsuario.setOpaque(false);
		apellidosUsuario.setBorder(null);

	
		apellidosUsuario.addFocusListener(new FocusAdapter() {
		    
		    // === CUANDO EL USUARIO HACE CLIC EN EL CAMPO ===
		    @Override
		    public void focusGained(FocusEvent e) {
		        // === VERIFICAR SI EL TEXTO ES EL PLACEHOLDER ===
		        if (apellidosUsuario.getText().equals(placeholderApe)) {
		            // === LIMPIAR EL CAMPO ===
		            apellidosUsuario.setText("");
		            // === CAMBIAR COLOR A NEGRO PARA EL TEXTO REAL ===
		            apellidosUsuario.setForeground(Color.BLACK);
		        }
		    }
		    
		    // === CUANDO EL USUARIO SALE DEL CAMPO ===
		    @Override
		    public void focusLost(FocusEvent e) {
		        // === VERIFICAR SI EL CAMPO QUEDÓ VACÍO ===
		        if (apellidosUsuario.getText().isEmpty()) {
		            // === RESTAURAR COLOR GRIS DEL PLACEHOLDER ===
		            apellidosUsuario.setForeground(Color.GRAY);
		            // === MOSTRAR NUEVAMENTE EL PLACEHOLDER ===
		            apellidosUsuario.setText(placeholderApe);
		        }
		    }
		});
		
		// === CREACIÓN DE BOTONES ===
		
		btnCrearCuenta.setOpaque(false);
		btnCrearCuenta.setContentAreaFilled(false);
		btnCrearCuenta.setBorderPainted(false);
		btnCrearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnIniciarSesion.setOpaque(false);
		btnIniciarSesion.setContentAreaFilled(false);
		btnIniciarSesion.setBorderPainted(false);
		btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		// === CONFIGURACIÓN DE POSICIONES Y DIMENSIONES DE LOS CAMPOS ===
		// === POSICIÓN INICIAL EN EL EJE Y (DESDE ARRIBA) ===
		int startY = 222;
		// === ALTURA DE CADA CAMPO ===
		int height = 45;
		// === ESPACIO ENTRE CAMPOS ===
		int spacing = 10;
		// === VARIABLE PARA CALCULAR LA POSICIÓN VERTICAL ACTUAL ===
		int yPos = startY;

		// === POSICIONAR CAMPO DE NOMBRE ===
		nomUsuario.setBounds(80, yPos, 340, height);
		// === CALCULAR SIGUIENTE POSICIÓN (INCREMENTAR Y) ===
		yPos += height + spacing; // 222 + 45 + 10 = 277
		
		// === POSICIONAR CAMPO DE APELLIDOS ===
		apellidosUsuario.setBounds(80, yPos, 340, height);
		// === CALCULAR SIGUIENTE POSICIÓN ===
		yPos += height + spacing; // 277 + 45 + 10 = 332

		// === POSICIONAR CAMPO DE USUARIO ===
		txtUsuario.setBounds(80, yPos, 340, height);
		// === CALCULAR SIGUIENTE POSICIÓN ===
		yPos += height + spacing; // 332 + 45 + 10 = 387

		// === POSICIONAR CAMPO DE CONTRASEÑA ===
		txtContrasenia.setBounds(80, yPos, 340, height);
		// === CALCULAR SIGUIENTE POSICIÓN (POR SI SE AÑADEN MÁS CAMPOS) ===
		yPos += height + spacing; // 387 + 45 + 10 = 442
		
		btnIniciarSesion.setBounds(257, 450, 142, 40); 
		btnCrearCuenta.setBounds(103, 450, 142, 40);

		// === AÑADIRLOS AL FONDO ===
		fondo.add(nomUsuario);
		fondo.add(apellidosUsuario);
		fondo.add(txtUsuario);
		fondo.add(txtContrasenia);
		fondo.add(btnIniciarSesion);
		fondo.add(btnCrearCuenta);
		
		
		btnIniciarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaActual.setVisible(false); ventanaAnterior.setVisible(true);

			}
		});
		
		btnCrearCuenta.addActionListener(e -> {
		    // === OBTENER DATOS DE LOS CAMPOS DE TEXTO ===
		    String nombre = nomUsuario.getText().trim();
		    String apellido = apellidosUsuario.getText().trim();
		    String usuario = txtUsuario.getText().trim();
		    String contrasenia = new String(txtContrasenia.getPassword());
		    
		    // === GENERAR ID SECUENCIAL: J0001, J0002, J0003, etc. ===
		    // Obtiene la cantidad actual de jugadores y suma 1 para el nuevo ID
		    int cantidadJugadores = bd.obtenerCantidadJugadores();
		    String idJ = "J" + String.format("%04d", cantidadJugadores + 1);

		    // === VALIDAR QUE TODOS LOS CAMPOS ESTÉN COMPLETOS ===
		    if (!nombre.isEmpty() && !apellido.isEmpty() && !usuario.isEmpty() && !contrasenia.isEmpty()
		            && !usuario.equals(placeholderUsuario) && !contrasenia.equals(placeholderContra)) {

		        // === INTENTAR REGISTRAR AL NUEVO JUGADOR EN LA BASE DE DATOS ===
		        boolean registrado = bd.registrarNuevoJugador(idJ, nombre, apellido, usuario, contrasenia);

		        // === VERIFICAR SI EL REGISTRO FUE EXITOSO ===
		        if (registrado) {
		            JOptionPane.showMessageDialog(null, "Creación de cuenta realizada correctamente. Ya puedes iniciar sesión.");
		            ventanaActual.setVisible(false);
		            
		            ventanaAnterior.setVisible(true); 
		        } else {
		            // === MENSAJE DE ERROR SI EL USUARIO YA EXISTE ===
		            JOptionPane.showMessageDialog(null, "Error: El nombre de usuario o ID ya existe. Intenta con otro nombre de usuario.");
		        }
		    } else {
		        // === MENSAJE DE ERROR SI FALTAN CAMPOS POR RELLENAR ===
		        JOptionPane.showMessageDialog(null, "Por favor, rellena todos los campos.");
		    }
		    
		    // === LIMPIAR TODOS LOS CAMPOS DE TEXTO ===
		    txtUsuario.setText("");
		    txtContrasenia.setText("");
		    nomUsuario.setText("");
		    apellidosUsuario.setText("");
		});


		this.getRootPane().setDefaultButton(btnIniciarSesion);
		this.setLocationRelativeTo(null);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        // Ejecutar la lógica de cierre de la base de datos
		        bd.closeBD();
		        
		        // Finalizar la aplicación
		        System.exit(0);
		    }
		});
		
		setVisible(true);
	}
	
	public VentanaCrearCuenta(JFrame va) {
		this(va, null);
	}

	public ConexionBD getBd() {
		return bd;
	}

	public void setBd(ConexionBD bd) {
		this.bd = bd;
	}

}