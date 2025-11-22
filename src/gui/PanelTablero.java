package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants; 

public class PanelTablero extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFrame ventanaAnterior;

    public PanelTablero(JFrame va) {
        this.ventanaAnterior = va;

        // === CONFIGURAMOS LA VENTANA ===
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DeustoChess - Partida 1 vs 1");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        // === EMPLEAMOS PanelConFondo PARA ESCALAR DE FORMA CORRECTA LA IMAGEN ===
        PanelConFondo main = new PanelConFondo("/images/FondoJuego.png");
        main.setLayout(new BorderLayout());
        setContentPane(main);

        // === COLORES ===
        Color colorAzulDeusto = new Color(58, 117, 173); 
        Color colorFondoAzul = new Color(0, 123, 255); 
        Color colorTexto = new Color(230, 235, 255); 
        
        // --- ESTILO BÁSICO DE BOTONES ---
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        // === PANEL NORTE DONDE IRÁN LOGO Y BOTONES ===
        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        
        // === LOGO Y TITULO (WEST) ===
        JPanel logoTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        logoTitulo.setOpaque(false);
        
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/LogoDeustoChessBK.png"));
            Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(logoImage));
            logoTitulo.add(lblLogo);
        } catch (Exception e) {
             System.err.println("No se pudo cargar el logo");
        }
        
        JLabel lblTitulo = new JLabel("DeustoChess");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE); 
        logoTitulo.add(lblTitulo);
        
        norte.add(logoTitulo, BorderLayout.WEST);

        // === BOTONES PARA EL CONTROL DE LAS VENTANAS (EAST) ===
        JPanel botonesVentana = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); 
        botonesVentana.setOpaque(false);
        
        JButton btnVolverMenu = new JButton("VOLVER AL MENÚ"); 
        btnVolverMenu.setFont(buttonFont);
        btnVolverMenu.setForeground(colorTexto);
        btnVolverMenu.setBackground(colorFondoAzul);
        btnVolverMenu.setOpaque(true);
        btnVolverMenu.setBorderPainted(false);
        
        JButton btnCerrarSesion = new JButton("CERRAR SESIÓN");
        btnCerrarSesion.setFont(buttonFont);
        btnCerrarSesion.setForeground(colorTexto);
        btnCerrarSesion.setBackground(colorFondoAzul);
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setBorderPainted(false);
        
        JButton btnSalir = new JButton("SALIR DEL JUEGO");
        btnSalir.setFont(buttonFont);
        btnSalir.setForeground(colorTexto);
        btnSalir.setBackground(colorFondoAzul);
        btnSalir.setOpaque(true);
        btnSalir.setBorderPainted(false);
     

        botonesVentana.add(btnVolverMenu);
        botonesVentana.add(btnCerrarSesion);
        botonesVentana.add(btnSalir);

        norte.add(botonesVentana, BorderLayout.EAST);
        
        main.add(norte, BorderLayout.NORTH);
        
        // === PANEL CENTRAL DE JUEGO ===
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        
        JPanel tablero = new JPanel(new GridLayout(8, 8));
        tablero.setPreferredSize(new Dimension(850, 850)); 
        
        for (int i = 7; i >= 0; i--) { 
            for (int j = 0; j <= 7; j++) { 
                
                Color colorCasilla = ((i + j) % 2 == 0) ? Color.WHITE : colorAzulDeusto;

                JButton casilla = new JButton(); 
                casilla.setBackground(colorCasilla);
                casilla.setOpaque(true);
                
                tablero.add(casilla);
            }
        }
        
        JPanel panelLateral = crearPanelLateral(colorFondoAzul, colorTexto);
        
        JPanel tableroCentrado = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tableroCentrado.setOpaque(false);
        tableroCentrado.add(tablero);
        
        centro.add(panelLateral, BorderLayout.EAST);
        centro.add(tableroCentrado, BorderLayout.CENTER);

        main.add(centro, BorderLayout.CENTER);
        
        // === ACCIONES DE LOS BOTONES DE VENTANA === 
        
        btnSalir.addActionListener(e -> System.exit(0));
        
        btnVolverMenu.addActionListener(e -> {
            if (ventanaAnterior != null) {
                ventanaAnterior.setVisible(true);
            }
            setVisible(false);
            dispose(); 
        });
        
        btnCerrarSesion.addActionListener(e -> {
            setVisible(false);
            dispose();
            
            if (ventanaAnterior != null) {
                ventanaAnterior.dispose(); 
            }
           
            new VentanaInicioSesion(); 
        });

        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    //  === METODO PARA CREAR EL PANEL LATERAL  === 
    private JPanel crearPanelLateral(Color fondo, Color frente) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondo);
        panel.setOpaque(true); 
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        
        panel.setPreferredSize(new Dimension(400, 1)); 

        JPanel pestanas = new JPanel(new GridLayout(1, 3));
        
        Font fuenteBoton = new Font("Arial", Font.BOLD, 12);
        Color oscurecerBK = fondo.darker(); //IA oscurecemos fondo para darle mejor visualización
        
        JButton btnJugar = new JButton("JUGAR");
        btnJugar.setFont(fuenteBoton);
        btnJugar.setForeground(frente);
        btnJugar.setBackground(oscurecerBK);
        btnJugar.setOpaque(true);
        pestanas.add(btnJugar);

        JButton btnMovimientos = new JButton("MOVIMIENTOS");
        btnMovimientos.setFont(fuenteBoton);
        btnMovimientos.setForeground(frente);
        btnMovimientos.setBackground(oscurecerBK);
        btnMovimientos.setOpaque(true);
        pestanas.add(btnMovimientos);

        JButton btnInformacion = new JButton("INFORMACIÓN");
        btnInformacion.setFont(fuenteBoton);
        btnInformacion.setForeground(frente);
        btnInformacion.setBackground(oscurecerBK);
        btnInformacion.setOpaque(true);
        pestanas.add(btnInformacion);

        panel.add(pestanas, BorderLayout.NORTH);

        JTextArea logArea = new JTextArea("Log de Movimientos y Eventos (Expediente, Habilidades, etc.)");
        logArea.setEditable(false);
        logArea.setBackground(fondo.brighter()); //IA HACEMOS EL FONDO MAS CLARO
        logArea.setForeground(Color.BLACK);
        JScrollPane scroll = new JScrollPane(logArea);

        panel.add(scroll, BorderLayout.CENTER);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        controles.setBackground(oscurecerBK);
        
        Font fuentePlain = new Font("Arial", Font.PLAIN, 12);
        Font fuenteBold = new Font("Arial", Font.BOLD, 12);
        
        JButton btnTablas = new JButton("TABLAS");
        btnTablas.setFont(fuentePlain);
        btnTablas.setForeground(frente);
        btnTablas.setBackground(fondo);
        btnTablas.setOpaque(true);
        
        JButton btnAnular = new JButton("ANULAR");
        btnAnular.setFont(fuentePlain);
        btnAnular.setForeground(frente);
        btnAnular.setBackground(fondo);
        btnAnular.setOpaque(true);
        
        controles.add(new JLabel("Controles de Jugada:"));
        
        Color controlFg = Color.BLACK;
        Color controlBg = Color.LIGHT_GRAY;

        JButton btnInicio = new JButton("|<");
        btnInicio.setFont(fuenteBold);
        btnInicio.setForeground(controlFg);
        btnInicio.setBackground(controlBg);
        btnInicio.setOpaque(true);
        controles.add(btnInicio);

        JButton btnAtras = new JButton("<");
        btnAtras.setFont(fuenteBold);
        btnAtras.setForeground(controlFg);
        btnAtras.setBackground(controlBg);
        btnAtras.setOpaque(true);
        controles.add(btnAtras);

        JButton btnAdelante = new JButton(">");
        btnAdelante.setFont(fuenteBold);
        btnAdelante.setForeground(controlFg);
        btnAdelante.setBackground(controlBg);
        btnAdelante.setOpaque(true);
        controles.add(btnAdelante);

        JButton btnFin = new JButton(">|");
        btnFin.setFont(fuenteBold);
        btnFin.setForeground(controlFg);
        btnFin.setBackground(controlBg);
        btnFin.setOpaque(true);
        controles.add(btnFin);
        
        controles.add(new JSeparator(SwingConstants.VERTICAL));

        controles.add(btnTablas);
        controles.add(btnAnular);
        
        panel.add(controles, BorderLayout.SOUTH);

        return panel;
        
    }

    public PanelTablero() {
    	
        this(null);
    }
    
}