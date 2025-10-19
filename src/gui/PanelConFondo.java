package gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelConFondo extends JPanel {

    private static final long serialVersionUID = 1L;
    private Image imagenFondo;

    //Creamos una clase que nos permita colocar una imagen en el fondo de la ventana de inicio
    public PanelConFondo(String rutaImagen) {
    	//Generamos un bloque try-catch por si no detectase la imagen
        try {
            imagenFondo = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de fondo: " + rutaImagen);
            e.printStackTrace();
            imagenFondo = null;
        }
    }

    //Llamamos al metodo paintComponent para "pintar" el background con la imagen que deseamos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            // Esto dibuja la imagen escalándola al tamaño actual del panel
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
