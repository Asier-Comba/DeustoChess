package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	//Creamos la ventana principal del juego
	public VentanaPrincipal() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setExtendedState(MAXIMIZED_BOTH);
		setTitle("DeustoChess");
		
		// Obtenemos las dimensiones de la pantalla para calcular posiciones
		Dimension tamañoPantalla = Toolkit.getDefaultToolkit().getScreenSize();		//Nos ayudamos de los conocimientos de internet
		int anchoPantalla = tamañoPantalla.width;
		int altoPantalla = tamañoPantalla.height;
		
		// Creamos el panel de fondo
		JLabel panelFondo = new JLabel(new ImageIcon(getClass().getResource("/imagenes/bk.jpg")));
		//panelFondo.setLayout(null); // Eliminamos el Layout automático
		
		// Creamos el titulo y posicionamos manualmente
		JLabel titulo = new JLabel("DeustoChess");
		titulo.setFont(new Font(Font.DIALOG, Font.BOLD, 80));
		titulo.setForeground(new Color(230, 235, 255)); 	//Generamos nuestro propio color
		titulo.setHorizontalAlignment(JLabel.CENTER);
		titulo.setBounds(0, 100, anchoPantalla, 150); // Hacemos que ocupe todo el ancho para que se centre correctamente
		panelFondo.add(titulo);
		
		JLabel modo = new JLabel("Elija modo de juego:");
		modo.setFont(new Font(Font.SANS_SERIF,Font.BOLD | Font.ITALIC, 30));
		modo.setForeground(new Color(230, 235, 255));
		modo.setHorizontalAlignment(JLabel.CENTER);
		modo.setBounds(0, 100, anchoPantalla, 500);
		panelFondo.add(modo);
		
		
		// Creamos botones y los posicionamos
		JButton jugarLocal = new JButton("1 vs 1");
		jugarLocal.setFont(new Font("Arial", Font.BOLD, 24));
		jugarLocal.setForeground(new Color(230, 235, 255));
		jugarLocal.setBackground(new Color(0, 123, 255));
		jugarLocal.setOpaque(true);
		jugarLocal.setBorderPainted(false);
		
		JButton jugarIa = new JButton("1 vs IA");
		jugarIa.setFont(new Font("Arial", Font.BOLD, 24));
		jugarIa.setForeground(new Color(230, 235, 255));
		jugarIa.setBackground(new Color(0, 123, 255));
		jugarIa.setOpaque(true);
		jugarIa.setBorderPainted(false);
		
		JButton repeticiones = new JButton("Repeticiones");
		repeticiones.setFont(new Font("Arial", Font.BOLD, 24));
		repeticiones.setForeground(new Color(230, 235, 255));
		repeticiones.setBackground(new Color(0, 123, 255));
		repeticiones.setOpaque(true);
		repeticiones.setBorderPainted(false);
		
		JButton exit = new JButton("Salir");
		exit.setFont(new Font("Arial", Font.BOLD, 24));
		exit.setForeground(new Color(230, 235, 255));
		exit.setBackground(new Color(0, 123, 255));
		exit.setOpaque(true);
		exit.setBorderPainted(false);
		exit.addActionListener((e)-> {
			System.exit(0);
		});
		
		// Calculamos la posición del botón basándonos en la imagen
		int anchoBoton = 200;
		int altoBoton = 60;
		int botonX = (int) (anchoPantalla * 0.45); 
		int botonY = (int) (altoPantalla * 0.37);
		
		// Usamos setBounds para colocar el botón donde queremos
		jugarLocal.setBounds(botonX, botonY, anchoBoton, altoBoton);
		jugarIa.setBounds(botonX, botonY + 80, anchoBoton, altoBoton);
		repeticiones.setBounds(botonX, botonY + 160, anchoBoton, altoBoton);
		exit.setBounds(botonX, botonY + 240, anchoBoton, altoBoton);
		panelFondo.add(jugarLocal);
		panelFondo.add(jugarIa);
		panelFondo.add(repeticiones);
		panelFondo.add(exit);
		
		// Añadimos el panel principal al frame
		add(panelFondo);
		setVisible(true);
	}
}
