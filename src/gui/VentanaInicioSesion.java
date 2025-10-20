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
		// === METEMOS EL LOGO NUEVAMENTE ===
		ImageIcon im = new ImageIcon("img/LogoDeustoChess.png");
		setIconImage(im.getImage());
		// === ESTABLECER EL PANEL DE FONDO ===
		PanelConFondo fondo = new PanelConFondo("/images/InicioSesion.png");
		setContentPane(fondo);
		
		// === USAR NULL LAYOUT PARA POSICIONAMIENTO ABSOLUTO ===
		fondo.setLayout(null);

		// === CREACIÓN DE COMPONENTES ===
		btnIniciarSesion = new JButton("");
		btnCrearCuenta = new JButton("Crear Cuenta");
		txtUsuario = new JTextField(20);
		txtContrasenia = new JPasswordField(20);

		// === CONFIGURACIÓN DEL PLACEHOLDER PARA USUARIO ===
		String placeholderUsuario = "Usuario";
		txtUsuario.setText(placeholderUsuario);
		txtUsuario.setForeground(Color.GRAY);
		// === CENTRAMOS EL TEXTO ===
		txtUsuario.setHorizontalAlignment(JTextField.CENTER); 
		txtUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		// === FONDO TRANSPARENTE ===
		txtUsuario.setOpaque(false);
		// === SIN BORDES ===
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

		// === CONFIGURACIÓN DEL PLACEHOLDER PARA LA CONTRASEÑA ===
		String placeholderContra = "Contraseña";
		// === GUARDAR EL CARÁCTER '•' (IA GENERATIVA) ===
		char defaultEchoChar = txtContrasenia.getEchoChar(); 
		txtContrasenia.setText(placeholderContra);
		txtContrasenia.setForeground(Color.GRAY);
		txtContrasenia.setEchoChar((char) 0);
		txtContrasenia.setHorizontalAlignment(JPasswordField.CENTER); 
		txtContrasenia.setFont(new Font("Arial", Font.BOLD, 18));
		// === FONDO TRANSPARENTE PARA USAR LA IMAGEN ===
		txtContrasenia.setOpaque(false); 
		txtContrasenia.setBorder(null);
		
		txtContrasenia.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String pass = new String(txtContrasenia.getPassword());
				if (pass.equals(placeholderContra)) {
					txtContrasenia.setText("");
					txtContrasenia.setForeground(Color.BLACK);
					// === OCULTAR TEXTO AL ESCRIBIR (IA GENERATIVA) ===
					txtContrasenia.setEchoChar(defaultEchoChar); 
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				String pass = new String(txtContrasenia.getPassword());
				if (pass.isEmpty()) {
					txtContrasenia.setForeground(Color.GRAY);
					txtContrasenia.setText(placeholderContra);
					// === MOSTRAR PLACEHOLDER (IA GENERATIVA) ===
					txtContrasenia.setEchoChar((char) 0);
				}
			}
		});

		// === CONFIGURACIÓN DEL BOTÓN DE  LOGIN (INVISIBLE) ===
		btnIniciarSesion.setOpaque(false);
		btnIniciarSesion.setContentAreaFilled(false);
		btnIniciarSesion.setBorderPainted(false);
		btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// === POSICIONAR COMPONENTES CON SETBOUNDS ===
		txtUsuario.setBounds(80, 332, 340, 45);
		txtContrasenia.setBounds(80, 387, 340, 45);
		btnIniciarSesion.setBounds(165, 450, 160, 43);
		btnCrearCuenta.setBounds(350, 20, 90, 30); 

		// === AÑADIR COMPONENTES AL PANEL FONDO ===
		fondo.add(txtUsuario);
		fondo.add(txtContrasenia);
		fondo.add(btnIniciarSesion);
		fondo.add(btnCrearCuenta);

		// === AÑADIR LISTENERS ===
		// === ACCIÓN: INICIAR SESIÓN ===
		btnIniciarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = txtUsuario.getText();
				String contrasenia = new String(txtContrasenia.getPassword());

				// === QUITAR PLACEHOLDERS DE LA LÓGICA DE LOGIN ===
				if (nombre.equals(placeholderUsuario)) nombre = "";
				if (contrasenia.equals(placeholderContra)) contrasenia = "";

				if (nombre.equals("deusto") && contrasenia.equals("deusto")) {
					JOptionPane.showMessageDialog(null, "Inicio de sesión correcto");
					ventanaActual.setVisible(false);
					 new VentanaPrincipal(ventanaActual);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos");
				}

				// === RESETEAR CAMPOS A PLACEHOLDER ===
				txtUsuario.setForeground(Color.GRAY);
				txtUsuario.setText(placeholderUsuario);
				txtContrasenia.setForeground(Color.GRAY);
				txtContrasenia.setText(placeholderContra);
				txtContrasenia.setEchoChar((char) 0);
			}
		});
		
		// === ACCIÓN: CREAR CUENTA ===
		btnCrearCuenta.addActionListener(e -> {
			ventanaActual.setVisible(false);
			 new VentanaCrearCuenta(ventanaActual); 
		});

		this.getRootPane().setDefaultButton(btnIniciarSesion);
		this.setLocationRelativeTo(null);
		
		setVisible(true);
	}
}