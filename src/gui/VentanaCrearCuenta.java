package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class VentanaCrearCuenta extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnIniciarSesion, btnCrearCuenta;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JTextField nomUsuario;
	private JTextField apellidosUsuario;
	private JFrame ventanaAnterior, ventanaActual;

	public VentanaCrearCuenta(JFrame va) {
		ventanaActual = this;
		ventanaAnterior = va;
		// === CREAMOS LA VENTANA ===
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,700);
		setTitle("Crear cuenta - DeustoChess");
		setResizable(false);
		// === VOLVEMOS A PONER EL LOGO ===
		ImageIcon im = new ImageIcon("img/LogoDeustoChess.png");
		setIconImage(im.getImage());
		
		// === ESTABLECEMOS FONDO ===
		PanelConFondo fondo = new PanelConFondo("/images/crearCuenta.jpg");
		setContentPane(fondo);
		
		// === USAMOS LAYOUT NULL PARA POSICIONAMIENTO CON SETBOUNDS ===
		fondo.setLayout(null);

		//=== CREAMOS LOS BOTONES E INICIALIZAMOS TODOS LOS COMPONENTES===
		btnIniciarSesion = new JButton("");
		btnCrearCuenta = new JButton("");
		txtUsuario = new JTextField();
		txtContrasenia = new JPasswordField();
		nomUsuario = new JTextField();
		apellidosUsuario = new JTextField();
		
		// === CONFIGURAMOS EL PLACEHOLDER PARA USUARIO ===
		String placeholderUsuario = "Usuario";
		txtUsuario.setText(placeholderUsuario); //Esto lo hacemos para que cuando campo = vacio aparezca lo que espera la aplicación que pongamos ahi
		txtUsuario.setForeground(Color.gray);
		// === CENTRAMOS EL TEXTO ===
		txtUsuario.setHorizontalAlignment(JTextField.CENTER); 
		txtUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		// === FONDO TRANSPARENTE ===
		txtUsuario.setOpaque(false);
		// === SIN BORDES ===
		txtUsuario.setBorder(null);
		
		txtUsuario.addFocusListener(new FocusAdapter() { //IA GENERATIVA
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
		
		// === CONFIGURACIÓN DEL PLACEHOLDER PARA EL NOMBRE ===
		String placeholderNom = "Nombre";
		nomUsuario.setText(placeholderNom); //Esto lo hacemos para que cuando campo = vacio aparezca que espera la aplicación que pongamos ahi
		nomUsuario.setForeground(Color.gray);
		// === CENTRAMOS EL TEXTO ===
		nomUsuario.setHorizontalAlignment(JTextField.CENTER); 
		nomUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		// === FONDO TRANSPARENTE ===
		nomUsuario.setOpaque(false);
		// === SIN BORDES ===
		nomUsuario.setBorder(null);
		
		nomUsuario.addFocusListener(new FocusAdapter() { //IA GENERATIVA
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
		
		// === CONFIGURACIÓN DEL PLACEHOLDER PARA EL APELLIDO ===
		String placeholderApe = "Apellidos";
		apellidosUsuario.setText(placeholderApe); //Esto lo hacemos para que cuando campo = vacio aparezca que espera la aplicación que pongamos ahi
		apellidosUsuario.setForeground(Color.gray);
		// === CENTRAMOS EL TEXTO ===
		apellidosUsuario.setHorizontalAlignment(JTextField.CENTER); 
		apellidosUsuario.setFont(new Font("Arial", Font.BOLD, 18));
		// === FONDO TRANSPARENTE ===
		apellidosUsuario.setOpaque(false);
		// === SIN BORDES ===
		apellidosUsuario.setBorder(null);
		
		apellidosUsuario.addFocusListener(new FocusAdapter() { //IA GENERATIVA
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
		
		
		/// === CONFIGURACIÓN DEL BOTÓN DE CREAR CUENTA (INVISIBLE) ===
		btnCrearCuenta.setOpaque(false);
		btnCrearCuenta.setContentAreaFilled(false);
		btnCrearCuenta.setBorderPainted(false);
		btnCrearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		/// === CONFIGURACIÓN DEL BOTÓN DE INICIAR SESION (INVISIBLE) ===
		btnIniciarSesion.setOpaque(false);
		btnIniciarSesion.setContentAreaFilled(false);
		btnIniciarSesion.setBorderPainted(false);
		btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// === POSICIONAR COMPONENTES CON SETBOUNDS ===
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


		// ===AÑADIMOS LOS COMPONENTES===
		fondo.add(nomUsuario);
		fondo.add(apellidosUsuario);
		fondo.add(txtUsuario);
		fondo.add(txtContrasenia);
		fondo.add(btnIniciarSesion);
		fondo.add(btnCrearCuenta);
		
		
		// === ACCIÓN INICIAR SESIÓN ===
		btnIniciarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaActual.setVisible(false); ventanaAnterior.setVisible(true);

			}
		});
		
		// === ACCIÓN: CREAR CUENTA (SOLO PLACEHOLDER) ===
		btnCrearCuenta.addActionListener(e -> {
			String nombre = txtUsuario.getText();
			String contrasenia = new String(txtContrasenia.getPassword());
			if (!nombre.equals("") && !contrasenia.equals("")) {

			JOptionPane.showMessageDialog(null, "Creación de cuenta realizada correctamente");
			
			} else {
				JOptionPane.showMessageDialog(null, "No se ha podido realizar la creación de la cuenta");
			}
			txtUsuario.setText("");
			txtContrasenia.setText("");
			});

		this.getRootPane().setDefaultButton(btnIniciarSesion);
		this.setLocationRelativeTo(null);
		
		setVisible(true);
	}

}
