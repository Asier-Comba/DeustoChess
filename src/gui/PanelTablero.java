package gui;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import bd.ConexionBD;
import domain.Alumno;
import domain.Becario;
import domain.Casilla;
import domain.MaquinaExpendedora;
import domain.Pieza;
import domain.Rector;
import domain.Secretaria;
import domain.Tablero;

public class PanelTablero extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFrame ventanaAnterior;
    
    private Tablero tableroLogico;
    private Casilla casillaSeleccionada; 
    
    private JButton[][] botonesCasillas;
    
    private JLabel lblPiezaSeleccionada;
    private JLabel lblPantallaMaquina; 
    private JButton btnUsarHabilidad;
    private ConexionBD bd;
    
    public PanelTablero(JFrame va, ConexionBD bd) {
        this.ventanaAnterior = va;
        this.bd = bd;
        this.tableroLogico = new Tablero();
        this.botonesCasillas = new JButton[8][8];

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DeustoChess - Partida 1 vs 1");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        PanelConFondo main = new PanelConFondo("/images/FondoJuego.png");
        main.setLayout(new BorderLayout());
        setContentPane(main);

        Color colorAzulDeusto = new Color(58, 117, 173); 
        Color colorFondoAzul = new Color(0, 123, 255); 
        Color colorTexto = new Color(230, 235, 255); 
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        // NORTE
        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        
        JPanel logoTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        logoTitulo.setOpaque(false);
        
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/LogoDeustoChessBK.png"));
            Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoTitulo.add(new JLabel(new ImageIcon(logoImage)));
        } catch (Exception e) {
             System.err.println("No se pudo cargar el logo");
        }
        
        JLabel lblTitulo = new JLabel("DeustoChess");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE); 
        logoTitulo.add(lblTitulo);
        norte.add(logoTitulo, BorderLayout.WEST);

        JPanel botonesVentana = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); 
        botonesVentana.setOpaque(false);
        
        JButton btnVolverMenu = new JButton("VOLVER AL MENÚ"); 
        estilarBoton(btnVolverMenu, buttonFont, colorTexto, colorFondoAzul);
        
        JButton btnCerrarSesion = new JButton("CERRAR SESIÓN");
        estilarBoton(btnCerrarSesion, buttonFont, colorTexto, colorFondoAzul);
        
        JButton btnSalir = new JButton("SALIR DEL JUEGO");
        estilarBoton(btnSalir, buttonFont, colorTexto, colorFondoAzul);

        botonesVentana.add(btnVolverMenu);
        botonesVentana.add(btnCerrarSesion);
        botonesVentana.add(btnSalir);
        norte.add(botonesVentana, BorderLayout.EAST);
        
        main.add(norte, BorderLayout.NORTH);
                
        JPanel panelIzquierdo = crearPanelIzquierdo(colorFondoAzul, colorTexto);
        main.add(panelIzquierdo, BorderLayout.WEST);
        
        JPanel panelDerecho = crearPanelLateral(colorFondoAzul, colorTexto);
        main.add(panelDerecho, BorderLayout.EAST);

        // TABLERO CON COORDENADAS
        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel contenedor para tablero y coordenadas
        JPanel contenedorTablero = new JPanel(new BorderLayout(5, 5));
        contenedorTablero.setOpaque(false);
        
        // Números a la IZQUIERDA (8 a 1, de arriba abajo)
        JPanel panelNumeros = new JPanel(new GridLayout(8, 1));
        panelNumeros.setOpaque(false);
        panelNumeros.setPreferredSize(new Dimension(30, 725));
        for (int i = 8; i >= 1; i--) {
            JLabel lblNum = new JLabel(String.valueOf(i), JLabel.CENTER);
            lblNum.setFont(new Font("Arial", Font.BOLD, 20));
            lblNum.setForeground(Color.BLACK);
            panelNumeros.add(lblNum);
        }
        
        // Letras ABAJO (A a H)
        JPanel panelLetras = new JPanel(new GridLayout(1, 8));
        panelLetras.setOpaque(false);
        panelLetras.setPreferredSize(new Dimension(725, 30));
        String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (String letra : letras) {
            JLabel lblLetra = new JLabel(letra, JLabel.CENTER);
            lblLetra.setFont(new Font("Arial", Font.BOLD, 20));
            lblLetra.setForeground(Color.BLACK);
            panelLetras.add(lblLetra);
        }
        
        // Tablero
        JPanel tableroVisual = new JPanel(new GridLayout(8, 8));
        tableroVisual.setPreferredSize(new Dimension(725, 725)); 
        
        for (int i = 7; i >= 0; i--) { 
            for (int j = 0; j <= 7; j++) { 
                Color colorCasilla = ((i + j) % 2 == 0) ? Color.WHITE : colorAzulDeusto;

                JButton casilla = new JButton(); 
                casilla.setBackground(colorCasilla);
                casilla.setOpaque(true);
                
                casilla.setBorder(null);
                casilla.setFocusPainted(false);
                
                final int fila = i;
                final int col = j;
                
                casilla.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        seleccionarCasilla(fila, col);
                    }
                });
                
                botonesCasillas[i][j] = casilla;
                tableroVisual.add(casilla);
            }
        }
        
        actualizarTablero();
        
        // Añadir componentes al contenedor
        contenedorTablero.add(panelNumeros, BorderLayout.WEST);
        contenedorTablero.add(tableroVisual, BorderLayout.CENTER);
        contenedorTablero.add(panelLetras, BorderLayout.SOUTH);
        
        centro.add(contenedorTablero);
        main.add(centro, BorderLayout.CENTER);
        
        // ACCIONES
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        btnVolverMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                if (ventanaAnterior != null) ventanaAnterior.dispose(); 
                new VentanaInicioSesion(bd); 
            }
        });

        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    private JPanel crearPanelIzquierdo(Color fondo, Color texto) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(250, 0));
        p.setBackground(fondo);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        p.setOpaque(true);
        
        JLabel lblTitulo = new JLabel("HABILIDADES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(texto);
        lblTitulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        lblPiezaSeleccionada = new JLabel("Seleccione pieza...");
        lblPiezaSeleccionada.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPiezaSeleccionada.setForeground(Color.WHITE);
        lblPiezaSeleccionada.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        lblPantallaMaquina = new JLabel("[ --- ]");
        lblPantallaMaquina.setFont(new Font("Monospaced", Font.BOLD, 20));
        lblPantallaMaquina.setForeground(Color.YELLOW);
        lblPantallaMaquina.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblPantallaMaquina.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        btnUsarHabilidad = new JButton("ACTIVAR PODER");
        btnUsarHabilidad.setFont(new Font("Arial", Font.BOLD, 14));
        btnUsarHabilidad.setBackground(Color.ORANGE);
        btnUsarHabilidad.setForeground(Color.BLACK);
        btnUsarHabilidad.setAlignmentX(JButton.CENTER_ALIGNMENT);
        btnUsarHabilidad.setEnabled(false);
        
        btnUsarHabilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarHabilidadEspecial();
            }
        });

        p.add(lblTitulo);
        p.add(new JSeparator());
        p.add(Box.createVerticalStrut(20));
        p.add(lblPiezaSeleccionada);
        p.add(Box.createVerticalStrut(20));
        p.add(btnUsarHabilidad);
        p.add(Box.createVerticalStrut(30));
        p.add(new JLabel("Display:"));
        p.add(lblPantallaMaquina);
        
        return p;
    }

    private JPanel crearPanelLateral(Color fondo, Color frente) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondo);
        panel.setOpaque(true); 
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        panel.setPreferredSize(new Dimension(350, 1)); 

        JPanel pestanas = new JPanel(new GridLayout(1, 3));
        Font fuenteBoton = new Font("Arial", Font.BOLD, 12);
        Color oscurecerBK = fondo.darker(); 
        
        JButton btnJugar = new JButton("JUGAR");
        estilarBoton(btnJugar, fuenteBoton, frente, oscurecerBK);
        pestanas.add(btnJugar);

        JButton btnMovimientos = new JButton("MOVS");
        estilarBoton(btnMovimientos, fuenteBoton, frente, oscurecerBK);
        pestanas.add(btnMovimientos);

        JButton btnInformacion = new JButton("INFO");
        estilarBoton(btnInformacion, fuenteBoton, frente, oscurecerBK);
        pestanas.add(btnInformacion);

        panel.add(pestanas, BorderLayout.NORTH);

        JTextArea logArea = new JTextArea("Log de Partida:\n- Inicio de juego.\n- Turno Blancas.");
        logArea.setEditable(false);
        logArea.setBackground(fondo.brighter()); 
        logArea.setForeground(Color.BLACK);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(logArea);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        controles.setBackground(oscurecerBK);
        
        JButton btnInicio = new JButton("|<");
        JButton btnAtras = new JButton("<");
        JButton btnAdelante = new JButton(">");
        JButton btnFin = new JButton(">|");
        
        Color controlBg = Color.LIGHT_GRAY;
        estilarBoton(btnInicio, new Font("Arial", Font.BOLD, 12), Color.BLACK, controlBg);
        estilarBoton(btnAtras, new Font("Arial", Font.BOLD, 12), Color.BLACK, controlBg);
        estilarBoton(btnAdelante, new Font("Arial", Font.BOLD, 12), Color.BLACK, controlBg);
        estilarBoton(btnFin, new Font("Arial", Font.BOLD, 12), Color.BLACK, controlBg);

        controles.add(btnInicio);
        controles.add(btnAtras);
        controles.add(btnAdelante);
        controles.add(btnFin);
        
        panel.add(controles, BorderLayout.SOUTH);

        return panel;
    }
    
    private void seleccionarCasilla(int f, int c) {
        this.casillaSeleccionada = tableroLogico.getCasillas(f, c);
        Pieza p = casillaSeleccionada.getPieza();
        
        if (p != null) {
            lblPiezaSeleccionada.setText(p.getNombre() + " (" + p.getColor() + ")");
            if (p instanceof Becario || p instanceof MaquinaExpendedora || p instanceof Secretaria || p instanceof Alumno || p instanceof Rector) {
                btnUsarHabilidad.setEnabled(true);
                btnUsarHabilidad.setText("USAR: " + p.getNombre());
            } else {
                btnUsarHabilidad.setEnabled(false);
                btnUsarHabilidad.setText("Sin habilidad");
            }
        } else {
            lblPiezaSeleccionada.setText("Casilla Vacía");
            btnUsarHabilidad.setEnabled(false);
        }
    }
    
    private void ejecutarHabilidadEspecial() {
        if (casillaSeleccionada == null) return;
        Pieza p = casillaSeleccionada.getPieza();
        btnUsarHabilidad.setEnabled(false);

        if (p instanceof Becario) {
            p.usarHabilidad(tableroLogico);
            actualizarTablero();
        } else if (p instanceof MaquinaExpendedora) {
            MaquinaExpendedora maq = (MaquinaExpendedora) p;
            maq.usarHabilidadConDisplay(tableroLogico, lblPantallaMaquina);
            
            // Habilitar el botón después de 3 segundos
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                btnUsarHabilidad.setEnabled(true);
                                actualizarTablero();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (p instanceof Secretaria) {
            p.usarHabilidad(tableroLogico);
            actualizarTablero();
        } else if (p instanceof Alumno) {									
            p.usarHabilidad(tableroLogico);
            actualizarTablero();
        } else if (p instanceof Rector) {
            ((Rector) p).usarHabilidad(tableroLogico);
            actualizarTablero();
        }
    }

    private void actualizarTablero() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j <= 7; j++) {
                Casilla casillaLogica = tableroLogico.getCasillas(i, j);
                Pieza pieza = casillaLogica.getPieza();
                JButton boton = botonesCasillas[i][j];
                
                if (pieza != null) {
                    String rutaImagen = obtenerRutaImagen(pieza);
                    URL imgURL = getClass().getResource(rutaImagen);
                    if (imgURL != null) {
                        ImageIcon icon = new ImageIcon(imgURL);
                        Image imgEscalada = icon.getImage().getScaledInstance(85, 85, Image.SCALE_SMOOTH);
                        boton.setIcon(new ImageIcon(imgEscalada));
                    } else {
                        boton.setText(pieza.getNombre().substring(0, 2));
                    }
                } else {
                    boton.setIcon(null);
                    boton.setText("");
                }
            }
        }
    }
    
    private void estilarBoton(JButton btn, Font f, Color fg, Color bg) {
        btn.setFont(f);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }
    
    private String obtenerRutaImagen(Pieza p) {
        String nombreClase = p.getClass().getSimpleName();
        String color = p.getColor().toString();
        return "/images/" + nombreClase + "_" + color + ".png";
    }

    public PanelTablero() {
        this(null, null);
    }
}