	package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaCrearCuenta extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnVolver, btnCrearCuenta;
	private JPanel pNorte, pSur, pCentro;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JLabel lblUsuario, lblContrasenia;
	private JFrame ventanaAnterior, ventanaActual;

	public VentanaCrearCuenta(JFrame va) {
		ventanaActual = this;
		ventanaAnterior = va;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(400, 200, 450, 350);
		setTitle("Crear cuenta - DeustoChess");

		pNorte = new JPanel();
		pSur = new JPanel();
		pCentro = new JPanel();

		btnVolver = new JButton("Volver");
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

		pSur.add(btnCrearCuenta);
		pSur.add(btnVolver);
		
		lblUsuario.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		txtUsuario.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		lblContrasenia.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		txtContrasenia.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		
		
		// Acción: iniciar sesión
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaActual.setVisible(false); ventanaAnterior.setVisible(true);

			}
		});

		// Acción: crear cuenta (solo placeholder)
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

		this.getRootPane().setDefaultButton(btnVolver);
		this.setLocationRelativeTo(null);
		
		setVisible(true);
	}

}
