package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaIniciarSesion extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnIniciarSesion, btnCrearCuenta;
	private JPanel pNorte, pSur, pCentro;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JLabel lblUsuario, lblContrasenia;
	private JFrame ventanaActual;

	public VentanaIniciarSesion() {
		ventanaActual = this;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(400, 200, 450, 350);
		setTitle("Inicio de sesión - DeustoChess");

		pNorte = new JPanel();
		pSur = new JPanel();
		pCentro = new JPanel();

		btnIniciarSesion = new JButton("Iniciar Sesión");
		btnCrearCuenta = new JButton("Crear Cuenta");

		txtUsuario = new JTextField(10);
		txtContrasenia = new JPasswordField(10);

		lblUsuario = new JLabel("Introduce el usuario:");
		lblContrasenia = new JLabel("Introduce la contraseña:");

		pCentro.setLayout(new GridLayout(2, 2, 10, 10));
		pCentro.add(lblUsuario);
		pCentro.add(txtUsuario);
		pCentro.add(lblContrasenia);
		pCentro.add(txtContrasenia);

		JPanel pCentroCentrado = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));
		pCentroCentrado.add(pCentro);

		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pCentroCentrado, BorderLayout.CENTER);

		pSur.add(btnIniciarSesion);
		pSur.add(btnCrearCuenta);

		// Acción: iniciar sesión
		btnIniciarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = txtUsuario.getText();
				String contrasenia = new String(txtContrasenia.getPassword());

				if (nombre.equals("deusto") && contrasenia.equals("deusto")) {
					JOptionPane.showMessageDialog(null, "Inicio de sesión correcto");
					ventanaActual.setVisible(false);
					new VentanaPrincipal(ventanaActual);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos");
				}

				txtUsuario.setText("");
				txtContrasenia.setText("");
			}
		});

		// Acción: crear cuenta (solo placeholder)
		btnCrearCuenta.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, "Funcionalidad no implementada aún.");
		});

		this.getRootPane().setDefaultButton(btnIniciarSesion);
		this.setLocationRelativeTo(null);
		
		setVisible(true);
	}

}
