package gui;

import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JFrame ventanaActual;

    private JPanel pSur, pCentro, pEste, pOeste;
    private JButton btnCerrarSesion, btnSalir, btn1VS1, btn1vsIA, btnReglas;
    private JFrame ventanaAnterior;

    public VentanaPrincipal(JFrame va) {
        ventanaActual = this;
        ventanaAnterior = va;

        // === CONFIGURACIÓN BÁSICA DE LA VENTANA ===
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DeustoChess");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // === CONTENEDOR RAÍZ ===
        PanelConFondo root = new PanelConFondo("/images/FondoActualizado.png");
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // === CREACIÓN DE PANELES ===
        pSur = new JPanel();
        pCentro = new JPanel();
        pEste = new JPanel();
        pOeste = new JPanel();

        // === CREACIÓN DE BOTONES ===
        btnCerrarSesion = new JButton("CERRAR SESIÓN");
        btnSalir = new JButton("SALIR");
        btn1VS1 = new JButton("1 VS 1");
        btn1vsIA = new JButton("1 vs IA");
        btnReglas = new JButton("REGLAS");

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
        

        // === BOTONES DEL PANEL SUR ===
        pSur.add(btnCerrarSesion);
        pSur.add(btnSalir);

        // === BOTONES DEL PANEL ESTE ===
        btn1VS1.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn1vsIA.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReglas.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        // Espaciado vertical entre botones (según práctica 3A.III)
        pEste.add(Box.createVerticalGlue());
        pEste.add(btn1VS1);
        pEste.add(Box.createRigidArea(new Dimension(0, 25)));
        pEste.add(btn1vsIA);
        pEste.add(Box.createRigidArea(new Dimension(0, 25)));
        pEste.add(btnReglas);
        pEste.add(Box.createVerticalGlue());
        
        // === ESPACIO ENTRE LOS BOTONES Y LA VENTANA ===
        pEste.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
        

     // === ACCIONES === 
        btnSalir.addActionListener(e -> System.exit(0)); 
        
        btnCerrarSesion.addActionListener(e -> { 
        
        	ventanaActual.setVisible(false); ventanaAnterior.setVisible(true);
        	});
        
        btnReglas.addActionListener(e -> {
          
            VentanaReglas ventanaReglas = new VentanaReglas();
            
            ventanaReglas.setVisible(true);
 
        });
        
        // === HACER VISIBLE ===
        setVisible(true);
    }
}

