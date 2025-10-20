package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border; // Necesitarás esta importación

public class VentanaInicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnIniciarSesion, btnCrearCuenta;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JFrame ventanaActual;

	public VentanaInicioSesion() {
		ventanaActual = this;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,700);
		setTitle("Inicio de sesión - DeustoChess");
		setResizable(false);

		// Establecer el panel de fondo
		PanelConFondo fondo = new PanelConFondo("/images/InicioSesion.png");
		setContentPane(fondo);
		
		// Usar NULL layout para posicionamiento absoluto
		fondo.setLayout(null);

		// Crear componentes
		btnIniciarSesion = new JButton("");
		btnCrearCuenta = new JButton("Crear Cuenta");
		txtUsuario = new JTextField(20);
		txtContrasenia = new JPasswordField(20);

		// --- Configuración del Placeholder para Usuario ---
		String placeholderUsuario = "Usuario";
		txtUsuario.setText(placeholderUsuario);
		txtUsuario.setForeground(Color.GRAY);
		txtUsuario.setHorizontalAlignment(JTextField.CENTER); // Centrar texto
		txtUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		txtUsuario.setOpaque(false); // Fondo transparente
		txtUsuario.setBorder(null); // Sin borde

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

		// --- Configuración del Placeholder para Contraseña ---
		String placeholderContra = "Contraseña";
		char defaultEchoChar = txtContrasenia.getEchoChar(); // Guardar el carácter '•' (IA generativa)
		txtContrasenia.setText(placeholderContra);
		txtContrasenia.setForeground(Color.GRAY);
		txtContrasenia.setEchoChar((char) 0);
		txtContrasenia.setHorizontalAlignment(JPasswordField.CENTER); 
		txtContrasenia.setFont(new Font("Arial", Font.BOLD, 18));
		txtContrasenia.setOpaque(false); // Fondo transparente para usar la imagen
		txtContrasenia.setBorder(null);
		
		txtContrasenia.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String pass = new String(txtContrasenia.getPassword());
				if (pass.equals(placeholderContra)) {
					txtContrasenia.setText("");
					txtContrasenia.setForeground(Color.BLACK);
					txtContrasenia.setEchoChar(defaultEchoChar); // Ocultar texto al escribir (IA generativa)
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				String pass = new String(txtContrasenia.getPassword());
				if (pass.isEmpty()) {
					txtContrasenia.setForeground(Color.GRAY);
					txtContrasenia.setText(placeholderContra);
					txtContrasenia.setEchoChar((char) 0); // Mostrar placeholder (IA generativa)
				}
			}
		});

		// --- Configuración del botón de Login (invisible) ---
		btnIniciarSesion.setOpaque(false);
		btnIniciarSesion.setContentAreaFilled(false);
		btnIniciarSesion.setBorderPainted(false);
		btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// Posicionar componentes con setBounds
		txtUsuario.setBounds(80, 332, 340, 45);
		txtContrasenia.setBounds(80, 387, 340, 45);
		btnIniciarSesion.setBounds(165, 450, 160, 43);
		btnCrearCuenta.setBounds(350, 20, 90, 30); 

		// Añadir componentes al panel de fondo
		fondo.add(txtUsuario);
		fondo.add(txtContrasenia);
		fondo.add(btnIniciarSesion);
		fondo.add(btnCrearCuenta);

		// Añadir listeners
		// Acción: iniciar sesión
		btnIniciarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = txtUsuario.getText();
				String contrasenia = new String(txtContrasenia.getPassword());

				// Quitar placeholders de la lógica de login
				if (nombre.equals(placeholderUsuario)) nombre = "";
				if (contrasenia.equals(placeholderContra)) contrasenia = "";

				if (nombre.equals("deusto") && contrasenia.equals("deusto")) {
					JOptionPane.showMessageDialog(null, "Inicio de sesión correcto");
					ventanaActual.setVisible(false);
					 new VentanaPrincipal(ventanaActual);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos");
				}

				// Resetear campos a placeholder
				txtUsuario.setForeground(Color.GRAY);
				txtUsuario.setText(placeholderUsuario);
				txtContrasenia.setForeground(Color.GRAY);
				txtContrasenia.setText(placeholderContra);
				txtContrasenia.setEchoChar((char) 0);
			}
		});
		
		// Acción: crear cuenta
		btnCrearCuenta.addActionListener(e -> {
			ventanaActual.setVisible(false);
			 new VentanaCrearCuenta(ventanaActual); 
		});

		this.getRootPane().setDefaultButton(btnIniciarSesion);
		this.setLocationRelativeTo(null);
		
		setVisible(true);
	}
}