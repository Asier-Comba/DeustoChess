package gui;

import java.awt.*;
import bd.ConexionBD;
import bd.Historial;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JFrame ventanaActual;

    private JPanel pSur, pEste, pOeste;
    private JButton btnCerrarSesion, btnSalir, btn1VS1, btnReglas, btnHistorial, btnEmparejamientos;
    private JFrame ventanaAnterior;
    private ConexionBD bd;
    

    private Historial j1; 
    private Historial j2; 


    public VentanaPrincipal(JFrame va, ConexionBD bd, Historial j1, Historial j2) {
        ventanaActual = this;
        ventanaAnterior = va;
        this.setBd(bd);
        this.j1 = j1;
        this.j2 = j2;


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DeustoChess - Menú Principal (" + j1.getJugadorNom() + " vs " + j2.getJugadorNom() + ")");
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        ImageIcon im = new ImageIcon("img/LogoDeustoChess.png");
		setIconImage(im.getImage());
        

        PanelConFondo root = new PanelConFondo("/images/Fondo.png");
        root.setLayout(new BorderLayout());
        setContentPane(root);


        pSur = new JPanel();
        pSur.setOpaque(false);
        pEste = new JPanel();
        pEste.setOpaque(false);
        pOeste = new JPanel();
        pOeste.setOpaque(false);


        btnCerrarSesion = new JButton("CERRAR SESIÓN");
        estilarBoton(btnCerrarSesion);

        btnSalir = new JButton("SALIR");
        estilarBoton(btnSalir);

        btn1VS1 = new JButton("1 VS 1");
        estilarBoton(btn1VS1);

        btnReglas = new JButton("REGLAS");
        estilarBoton(btnReglas);

        btnHistorial = new JButton("HISTORIAL");
        estilarBoton(btnHistorial);

        btnEmparejamientos = new JButton("GENERAR EMPAREJAMIENTOS");
        estilarBoton(btnEmparejamientos);
        

        pSur.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10)); 
        pEste.setLayout(new BoxLayout(pEste, BoxLayout.Y_AXIS));  


        Color blancoDeusto = Color.WHITE;
        pSur.setBackground(blancoDeusto);
        pEste.setBackground(blancoDeusto);
        pOeste.setBackground(blancoDeusto);


        getContentPane().add(pSur, BorderLayout.SOUTH);
        getContentPane().add(pEste, BorderLayout.EAST);
        getContentPane().add(pOeste, BorderLayout.WEST);


        btn1VS1.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReglas.setAlignmentX(Component.CENTER_ALIGNMENT);


        pSur.add(Box.createVerticalGlue());
        pSur.add(btn1VS1);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnReglas);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnHistorial);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnEmparejamientos);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnCerrarSesion);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(btnSalir);
        pSur.add(Box.createRigidArea(new Dimension(0, 25)));
        pSur.add(Box.createVerticalGlue());

        pSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        

        btnSalir.addActionListener((e) -> {
        	System.exit(0);
        	bd.closeBD();
        }); 
        
        btnCerrarSesion.addActionListener(e -> { 
        	ventanaActual.setVisible(false);

            new VentanaInicioSesion(bd); 
            ventanaActual.dispose();
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

            new PanelTablero(ventanaActual, bd, j1, j2).setVisible(true); 
        });

        btnEmparejamientos.addActionListener(e -> {
            ventanaActual.setVisible(false);
            new VentanaEmparejamientos(ventanaActual).setVisible(true);
        });
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                bd.closeBD();
                System.exit(0);
            }
        });

        setVisible(true);
    }


    private void estilarBoton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 24));
        btn.setForeground(new Color(230, 235, 255));
        btn.setBackground(new Color(0, 123, 255));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }

	public ConexionBD getBd() {
		return bd;
	}

	public void setBd(ConexionBD bd) {
		this.bd = bd;
	}
}