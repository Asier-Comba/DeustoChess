package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import bd.ConexionBD;
import bd.Historial;

public class VentanaInicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnIniciarSesion, btnCrearCuenta;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JFrame ventanaActual;
	private ConexionBD bd;
	

	private Historial jugador1 = null; 
	
	public VentanaInicioSesion(ConexionBD bd) {
		this.setBd(bd);
		ventanaActual = this;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,700);
		setTitle("Inicio de sesión - DeustoChess");
		setResizable(false);
		
		ImageIcon im = new ImageIcon("img/LogoDeustoChess.png");
		setIconImage(im.getImage());
		
		PanelConFondo fondo = new PanelConFondo("/images/InicioSesion.png");
		setContentPane(fondo);
		fondo.setLayout(null);


		btnIniciarSesion = new JButton(""); 
		btnCrearCuenta = new JButton("Crear Cuenta");
		txtUsuario = new JTextField(20);
		txtContrasenia = new JPasswordField(20);

		// === CONFIGURACIÓN USUARIO ===
		String placeholderUsuario = "Usuario";
		txtUsuario.setText(placeholderUsuario);
		txtUsuario.setForeground(Color.GRAY);
		txtUsuario.setHorizontalAlignment(JTextField.CENTER); 
		txtUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		txtUsuario.setOpaque(false);
		txtUsuario.setBorder(null);
		
		txtUsuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtUsuario.getText().equals(placeholderUsuario)) {
					txtUsuario.setText("");
					txtUsuario.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtUsuario.getText().isEmpty()) {
					txtUsuario.setForeground(Color.GRAY);
					txtUsuario.setText(placeholderUsuario);
				}
			}
		});


		String placeholderContra = "Contraseña";
		char defaultEchoChar = txtContrasenia.getEchoChar(); 
		txtContrasenia.setText(placeholderContra);
		txtContrasenia.setForeground(Color.GRAY);
		txtContrasenia.setEchoChar((char) 0);
		txtContrasenia.setHorizontalAlignment(JPasswordField.CENTER); 
		txtContrasenia.setFont(new Font("Arial", Font.BOLD, 18));
		txtContrasenia.setOpaque(false); 
		txtContrasenia.setBorder(null);
		
		txtContrasenia.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String pass = new String(txtContrasenia.getPassword());
				if (pass.equals(placeholderContra)) {
					txtContrasenia.setText("");
					txtContrasenia.setForeground(Color.BLACK);
					txtContrasenia.setEchoChar(defaultEchoChar); 
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				String pass = new String(txtContrasenia.getPassword());
				if (pass.isEmpty()) {
					txtContrasenia.setForeground(Color.GRAY);
					txtContrasenia.setText(placeholderContra);
					txtContrasenia.setEchoChar((char) 0);
				}
			}
		});

		btnIniciarSesion.setOpaque(false);
		btnIniciarSesion.setContentAreaFilled(false);
		btnIniciarSesion.setBorderPainted(false);
		btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		

		txtUsuario.setBounds(80, 332, 340, 45);
		txtContrasenia.setBounds(80, 387, 340, 45);
		btnIniciarSesion.setBounds(165, 450, 160, 43);
		btnCrearCuenta.setBounds(350, 20, 130, 30); 
		btnCrearCuenta.setForeground(Color.white);
		btnCrearCuenta.setBackground(new Color(58, 117, 173));
		
		fondo.add(txtUsuario);
		fondo.add(txtContrasenia);
		fondo.add(btnIniciarSesion);
		fondo.add(btnCrearCuenta);


		btnIniciarSesion.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        
		        String usuario = txtUsuario.getText();
		        String contrasenia = new String(txtContrasenia.getPassword());
		        
		        if (usuario.equals(placeholderUsuario)) usuario = "";
		        if (contrasenia.equals(placeholderContra)) contrasenia = "";
		        

		        if (bd.verificarCredenciales(usuario, contrasenia)) {
		            

		        	Historial jugadorLogueado = bd.obtenerDatosJugador(usuario);
		        	
		        	if (jugador1 == null) {
		        		// --- PASO 1: JUGADOR BLANCAS ---
		        		jugador1 = jugadorLogueado;
		        		
		        		JOptionPane.showMessageDialog(null, 
		        				"✅ ¡Bienvenido " + jugador1.getJugadorNom() + "!\n" +
		        				"Jugarás con BLANCAS.\n\n" +
		        				"Ahora debe iniciar sesión el JUGADOR 2 (NEGRAS).");
		        		

		        		txtUsuario.setText("");
		        		txtContrasenia.setText("");
		        		txtUsuario.requestFocus();
		        		setTitle("Inicio de sesión - JUGADOR 2 (NEGRAS)");
		        		
		        	} else {

		        		if (jugador1.getIdJ().equals(jugadorLogueado.getIdJ())) {
		        			JOptionPane.showMessageDialog(null, "❌ Error: El Jugador 2 debe ser una persona distinta.");
		        			return;
		        		}
		        		
		        		Historial jugador2 = jugadorLogueado;
		        		
		        		JOptionPane.showMessageDialog(null, 
		        				"¡Bienvenido " + jugador2.getJugadorNom() + "!\n" +
		        				"Jugarás con NEGRAS.\n\n" +
		        				"¡QUE COMIENCE LA PARTIDA!");
		        		
		        		ventanaActual.setVisible(false);
		        		

		        		new VentanaPrincipal(ventanaActual, bd, jugador1, jugador2);
		        	}
		            
		        } else {

		            if (usuario.equals("deusto") && contrasenia.equals("deusto")) {
		                JOptionPane.showMessageDialog(null, "Inicio de sesión de PRUEBA (Admin).");
		                ventanaActual.setVisible(false);

		                Historial dummy1 = new Historial("AdminBlancas", "Test", "00", 0, 0);
		                Historial dummy2 = new Historial("AdminNegras", "Test", "01", 0, 0);
		                new VentanaPrincipal(ventanaActual, bd, dummy1, dummy2);
		            } else {
		                JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos");
		            }
		        }
		        

		        if (ventanaActual.isVisible()) {
		        	if (txtUsuario.getText().isEmpty()) {
				        txtUsuario.setForeground(Color.GRAY);
				        txtUsuario.setText(placeholderUsuario);
		        	}
		        	if (String.valueOf(txtContrasenia.getPassword()).isEmpty()) {
				        txtContrasenia.setForeground(Color.GRAY);
				        txtContrasenia.setText(placeholderContra);
				        txtContrasenia.setEchoChar((char) 0);
		        	}
		        }
		    }
		});
		
		btnCrearCuenta.addActionListener(e -> {
			ventanaActual.setVisible(false);
			new VentanaCrearCuenta(ventanaActual, bd); 
		});

		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        bd.closeBD();
		        System.exit(0);
		    }
		});
		
		this.getRootPane().setDefaultButton(btnIniciarSesion);
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	public ConexionBD getBd() {
		return bd;
	}

	public void setBd(ConexionBD bd) {
		this.bd = bd;
	}
}