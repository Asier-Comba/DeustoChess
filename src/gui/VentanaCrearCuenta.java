	package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

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

		//ImageIcon im = new ImageIcon("/images/LogoDeustoChess.jpg");
		//setIconImage(im.getImage());
		JPanel pCentroCentrado = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));
		pCentroCentrado.add(pCentro);

		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pCentroCentrado, BorderLayout.CENTER);

		pSur.add(btnCrearCuenta);
		pSur.add(btnVolver);
		
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 15));
		txtUsuario.setFont(new Font("Arial", Font.BOLD, 15));
		lblContrasenia.setFont(new Font("Arial", Font.BOLD, 15));
		txtContrasenia.setFont(new Font("Arial", Font.BOLD, 15));
		
		
		// Acción: iniciar sesión
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaActual.setVisible(false); ventanaAnterior.setVisible(true);

			}
		});
		btnCrearCuenta.setBackground(new Color(40, 40, 190));
		btnVolver.setBackground(new Color(40, 40, 160));
		btnCrearCuenta.setForeground(Color.white);
		btnVolver.setForeground(Color.white);
		btnVolver.setFont(new Font("Arial", Font.BOLD, 15));
		btnCrearCuenta.setFont(new Font("Arial", Font.BOLD, 15));
		btnCrearCuenta.setBorder(new EtchedBorder(20));
		btnVolver.setBorder(new EtchedBorder(20));
		
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
