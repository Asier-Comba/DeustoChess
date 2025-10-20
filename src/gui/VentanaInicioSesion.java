package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border; // Necesitarás esta importación

public class VentanaInicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnIniciarSesion, btnCrearCuenta;
	// private JPanel pNorte, pSur, pCentro; // Ya no los necesitamos
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	// private JLabel lblUsuario, lblContrasenia; // Ya no los necesitamos
	private JFrame ventanaActual;

	public VentanaInicioSesion() {
		ventanaActual = this;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,700);
		setTitle("Inicio de sesión - DeustoChess");
		setResizable(false); // Recomendado para layouts nulos

		// 1. Establecer el panel de fondo
		// Asegúrate de que la clase PanelConFondo exista en tu paquete
		PanelConFondo fondo = new PanelConFondo("/images/InicioSesion.png");
		setContentPane(fondo);
		
		// 2. Usar NULL layout para posicionamiento absoluto
		fondo.setLayout(null);

		// 3. Crear componentes
		btnIniciarSesion = new JButton(""); // Sin texto, la imagen ya lo tiene
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
		char defaultEchoChar = txtContrasenia.getEchoChar(); // Guardar el carácter '•'
		txtContrasenia.setText(placeholderContra);
		txtContrasenia.setForeground(Color.GRAY);
		txtContrasenia.setEchoChar((char) 0); // Mostrar texto del placeholder
		txtContrasenia.setHorizontalAlignment(JPasswordField.CENTER); // Centrar texto
		txtContrasenia.setFont(new Font("Arial", Font.BOLD, 18));
		txtContrasenia.setOpaque(false); // Fondo transparente
		txtContrasenia.setBorder(null); // Sin borde
		
		txtContrasenia.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String pass = new String(txtContrasenia.getPassword());
				if (pass.equals(placeholderContra)) {
					txtContrasenia.setText("");
					txtContrasenia.setForeground(Color.BLACK);
					txtContrasenia.setEchoChar(defaultEchoChar); // Ocultar texto al escribir
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				String pass = new String(txtContrasenia.getPassword());
				if (pass.isEmpty()) {
					txtContrasenia.setForeground(Color.GRAY);
					txtContrasenia.setText(placeholderContra);
					txtContrasenia.setEchoChar((char) 0); // Mostrar placeholder
				}
			}
		});

		// --- Configuración del botón de Login (invisible) ---
		btnIniciarSesion.setOpaque(false);
		btnIniciarSesion.setContentAreaFilled(false);
		btnIniciarSesion.setBorderPainted(false);
		btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor al pasar por encima

		// 4. Posicionar componentes con setBounds(x, y, width, height)
		txtUsuario.setBounds(80, 332, 340, 45);
		txtContrasenia.setBounds(80, 387, 340, 45);
		btnIniciarSesion.setBounds(165, 450, 160, 43);
		btnCrearCuenta.setBounds(350, 20, 90, 30); // Botón "Crear Cuenta" arriba a la derecha

		// 5. Añadir componentes al panel de fondo
		fondo.add(txtUsuario);
		fondo.add(txtContrasenia);
		fondo.add(btnIniciarSesion);
		fondo.add(btnCrearCuenta);

		// 6. Añadir listeners (tu código original)
		
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
					 new VentanaPrincipal(ventanaActual); // Asumiendo que esta clase existe
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
			 new VentanaCrearCuenta(ventanaActual); // Asumiendo que esta clase existe
		});

		this.getRootPane().setDefaultButton(btnIniciarSesion);
		this.setLocationRelativeTo(null);
		
		setVisible(true);
	}
}