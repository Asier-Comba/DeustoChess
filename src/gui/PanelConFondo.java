package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelConFondo extends JPanel{

	private static final long serialVersionUID = 1L;
	private Image imagenFondo;
	//Vamos a crear un panel que ajuste el fondo al tamaño de la ventana
	public PanelConFondo(String rutaImagen) {
		imagenFondo = new ImageIcon(getClass().getResource("/imagenes/bk.jpg")).getImage();
	}

	protected void pintarFondo(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);

	}
}
