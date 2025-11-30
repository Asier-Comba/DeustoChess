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
		this.bd = bd;
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

		btnIniciarSesion = new JButton("");
		btnCrearCuenta = new JButton("");
		txtUsuario = new JTextField();
		txtContrasenia = new JPasswordField();
		nomUsuario = new JTextField();
		apellidosUsuario = new JTextField();
		
		String placeholderUsuario = "Usuario";
		txtUsuario.setText(placeholderUsuario);
		txtUsuario.setForeground(Color.gray);
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
		
		String placeholderNom = "Nombre";
		nomUsuario.setText(placeholderNom);
		nomUsuario.setForeground(Color.gray);
		nomUsuario.setHorizontalAlignment(JTextField.CENTER); 
		nomUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		nomUsuario.setOpaque(false);
		nomUsuario.setBorder(null);
		
		nomUsuario.addFocusListener(new FocusAdapter() { 
			@Override
			public void focusGained(FocusEvent e) {
				if (nomUsuario.getText().equals(placeholderNom)) {
					nomUsuario.setText("");
					nomUsuario.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (nomUsuario.getText().isEmpty()) {
					nomUsuario.setForeground(Color.GRAY);
					nomUsuario.setText(placeholderNom);
				}
			}
		});
		
		String placeholderApe = "Apellidos";
		apellidosUsuario.setText(placeholderApe);
		apellidosUsuario.setForeground(Color.gray);
		apellidosUsuario.setHorizontalAlignment(JTextField.CENTER); 
		apellidosUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		apellidosUsuario.setOpaque(false);
		apellidosUsuario.setBorder(null);
		
		apellidosUsuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (apellidosUsuario.getText().equals(placeholderApe)) {
					apellidosUsuario.setText("");
					apellidosUsuario.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (apellidosUsuario.getText().isEmpty()) {
					apellidosUsuario.setForeground(Color.GRAY);
					apellidosUsuario.setText(placeholderApe);
				}
			}
		});
		
		
		btnCrearCuenta.setOpaque(false);
		btnCrearCuenta.setContentAreaFilled(false);
		btnCrearCuenta.setBorderPainted(false);
		btnCrearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnIniciarSesion.setOpaque(false);
		btnIniciarSesion.setContentAreaFilled(false);
		btnIniciarSesion.setBorderPainted(false);
		btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		int startY = 222;
		int height = 45;
		int spacing = 10;
		int yPos = startY;

		nomUsuario.setBounds(80, yPos, 340, height);
		yPos += height + spacing; 
		apellidosUsuario.setBounds(80, yPos, 340, height);
		yPos += height + spacing; 
		txtUsuario.setBounds(80, yPos, 340, height);
		yPos += height + spacing;
		txtContrasenia.setBounds(80, yPos, 340, height);
		yPos += height + spacing;
		
		btnIniciarSesion.setBounds(257, 450, 142, 40); 
		btnCrearCuenta.setBounds(103, 450, 142, 40);


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
			String nombre = nomUsuario.getText().trim();
			String apellido = apellidosUsuario.getText().trim();
			String usuario = txtUsuario.getText().trim();
			String contrasenia = new String(txtContrasenia.getPassword());
			
			int cantidadJugadores = bd.obtenerCantidadJugadores();
			String idJ = "J" + String.format("%04d", cantidadJugadores + 1);

			if (!nombre.isEmpty() && !apellido.isEmpty() && !usuario.isEmpty() && !contrasenia.isEmpty()
					&& !usuario.equals(placeholderUsuario) && !contrasenia.equals(placeholderContra)) {

				boolean registrado = bd.registrarNuevoJugador(idJ, nombre, apellido, usuario, contrasenia);

				if (registrado) {
					JOptionPane.showMessageDialog(null, "Creación de cuenta realizada correctamente. Ya puedes iniciar sesión.");
					ventanaActual.setVisible(false);
					
					ventanaAnterior.setVisible(true); 
				} else {
					JOptionPane.showMessageDialog(null, "Error: El nombre de usuario o ID ya existe. Intenta con otro nombre de usuario.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, rellena todos los campos.");
			}
			txtUsuario.setText("");
			txtContrasenia.setText("");
			nomUsuario.setText("");
			apellidosUsuario.setText("");
		});

		this.getRootPane().setDefaultButton(btnIniciarSesion);
		this.setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	public VentanaCrearCuenta(JFrame va) {
		this(va, null);
	}

}