package gui;

import java.awt.*;
import bd.ConexionBD;
import javax.swing.*;

import bd.ConexionBD;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JFrame ventanaActual;

    private JPanel pSur, pEste, pOeste;
    private JButton btnCerrarSesion, btnSalir, btn1VS1, btn1vsIA, btnReglas, btnHistorial;
    private JFrame ventanaAnterior;
    private ConexionBD bd;

    public VentanaPrincipal(JFrame va, ConexionBD bd) {
        ventanaActual = this;
        ventanaAnterior = va;
        this.bd = bd;

        // === CONFIGURACIÓN BÁSICA DE LA VENTANA ===
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DeustoChess");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // === AÑADIMOS EL LOGOTIPO DE DEUSTOCHESS ===
        ImageIcon im = new ImageIcon("img/LogoDeustoChess.png");
		setIconImage(im.getImage());
        
        // === CONTENEDOR RAÍZ ===
        PanelConFondo root = new PanelConFondo("/images/Fondo.png");
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // === CREACIÓN DE PANELES ===
        pSur = new JPanel();
        pSur.setOpaque(false);
        pEste = new JPanel();
        pEste.setOpaque(false);
        pOeste = new JPanel();
        pOeste.setOpaque(false);

        // === CREACIÓN DE BOTONES ===
        btnCerrarSesion = new JButton("CERRAR SESIÓN");
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 24));
        btnCerrarSesion.setForeground(new Color(230, 235, 255));
        btnCerrarSesion.setBackground(new Color(0, 123, 255));
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setBorderPainted(false);

        btnSalir = new JButton("SALIR");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 24));
        btnSalir.setForeground(new Color(230, 235, 255));
        btnSalir.setBackground(new Color(0, 123, 255));
        btnSalir.setOpaque(true);
        btnSalir.setBorderPainted(false);

        btn1VS1 = new JButton("1 VS 1");
        btn1VS1.setFont(new Font("Arial", Font.BOLD, 24));
        btn1VS1.setForeground(new Color(230, 235, 255));
        btn1VS1.setBackground(new Color(0, 123, 255));
        btn1VS1.setOpaque(true);
        btn1VS1.setBorderPainted(false);

        btnReglas = new JButton("REGLAS");
        btnReglas.setFont(new Font("Arial", Font.BOLD, 24));
        btnReglas.setForeground(new Color(230, 235, 255));
        btnReglas.setBackground(new Color(0, 123, 255));
        btnReglas.setOpaque(true);
        btnReglas.setBorderPainted(false);

        btnHistorial = new JButton("HISTORIAL");
        btnHistorial.setFont(new Font("Arial", Font.BOLD, 24));
        btnHistorial.setForeground(new Color(230, 235, 255));
        btnHistorial.setBackground(new Color(0, 123, 255));
        btnHistorial.setOpaque(true);
        btnHistorial.setBorderPainted(false);
        
        // === LAYOUTS ===
        pSur.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10)); // botones centrados y separados
        pEste.setLayout(new BoxLayout(pEste, BoxLayout.Y_AXIS));   // botones en columna

        // === COLORES ===
        Color blancoDeusto = Color.WHITE;
        pSur.setBackground(blancoDeusto);
        pEste.setBackground(blancoDeusto);
        pOeste.setBackground(blancoDeusto);

        // === DISTRIBUCIÓN ===
        getContentPane().add(pSur, BorderLayout.SOUTH);
        getContentPane().add(pEste, BorderLayout.EAST);
        getContentPane().add(pOeste, BorderLayout.WEST);

        // === BOTONES DEL PANEL ESTE ===
        btn1VS1.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReglas.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        // Espaciado vertical entre botones (según práctica 3A.III)
        pSur.add(Box.createVerticalGlue());
        pSur.add(btn1VS1);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnReglas);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnHistorial);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnCerrarSesion);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnSalir);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(Box.createVerticalGlue());


        
        // === ESPACIO ENTRE LOS BOTONES Y LA VENTANA ===
        pSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        

     // === ACCIONES === 
        btnSalir.addActionListener(e -> System.exit(0)); 
        
        btnCerrarSesion.addActionListener(e -> { 
        
        	ventanaActual.setVisible(false); ventanaAnterior.setVisible(true);
        	});
        
        btnReglas.addActionListener(e -> {
          
            VentanaReglas ventanaReglas = new VentanaReglas();
            
            ventanaReglas.setVisible(true);
 
        });
    
        btnHistorial.addActionListener(e -> {
            ventanaActual.setVisible(false);
           
            new TablaHistorial(ventanaActual, bd).setVisible(true); 
        });
        
        btn1VS1.addActionListener(e -> {
            ventanaActual.setVisible(false);
          
            new PanelTablero(ventanaActual, bd).setVisible(true); 
        });
        // === HACER VISIBLE ===
        setVisible(true);
    }
   
}


