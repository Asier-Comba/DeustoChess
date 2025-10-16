package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;

	///Creamos la ventana principal
	public VentanaPrincipal() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setExtendedState(MAXIMIZED_BOTH);
		setTitle("DeustoChess");
		
		//Creamos el background de nuestra pantalla de inicio
		//Creamos el label donde irá nuestra imagen
		JLabel panelFondo = new JLabel(new ImageIcon(getClass().getResource("/imagenes/bk.jpg")));
		panelFondo.setLayout(new BorderLayout());
		
		//Creamos el titulo
		JLabel titulo = new JLabel("DeustoChess");
		titulo.setFont(new Font(Font.MONOSPACED, Font.BOLD | Font.ITALIC, 80));
		titulo.setForeground(new Color(0, 91, 187)); 	//Generamos un color similar al azul de Deusto
		
		panelFondo.add(titulo, BorderLayout.CENTER);
		add(panelFondo);
		
		setVisible(true);
	}
}
