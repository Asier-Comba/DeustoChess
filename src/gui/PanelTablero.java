package gui;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import bd.ConexionBD;
import domain.Alumno;
import domain.Becario;
import domain.Bedel;
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
    private Pieza piezaSeleccionada;  
    
    private JButton[][] botonesCasillas;
    
    private JLabel lblPiezaSeleccionada;
    private JLabel lblPantallaMaquina; 
    private JButton btnUsarHabilidad;
    
    // Control de turnos
    private domain.Color turnoActual;

    // Efectos de turno (Máquina)
    private int movimientosExtra;
    private boolean perderTurno;

    // Log real
    private JTextArea logArea;
 
    private ConexionBD bd;
    
    public PanelTablero(JFrame va, ConexionBD bd) {
        this.ventanaAnterior = va;
        this.setBd(bd);
        
        this.tableroLogico = new Tablero();
        this.botonesCasillas = new JButton[8][8];
        this.piezaSeleccionada = null;
        this.turnoActual = domain.Color.BLANCA;

        // Máquina
        this.movimientosExtra = 0;
        this.perderTurno = false;

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

        // PANEL NORTE
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

        // TABLERO
        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel contenedorTablero = new JPanel(new BorderLayout(5, 5));
        contenedorTablero.setOpaque(false);
        
        JPanel panelNumeros = new JPanel(new GridLayout(8, 1));
        panelNumeros.setOpaque(false);
        panelNumeros.setPreferredSize(new Dimension(30, 725));
        for (int i = 8; i >= 1; i--) {
            JLabel lblNum = new JLabel(String.valueOf(i), JLabel.CENTER);
            lblNum.setFont(new Font("Arial", Font.BOLD, 20));
            lblNum.setForeground(Color.BLACK);
            panelNumeros.add(lblNum);
        }
        
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
                
                casilla.addActionListener(e -> clicCasilla(fila, col));
                
                botonesCasillas[i][j] = casilla;
                tableroVisual.add(casilla);
            }
        }
        
        actualizarTablero();
        
        contenedorTablero.add(panelNumeros, BorderLayout.WEST);
        contenedorTablero.add(tableroVisual, BorderLayout.CENTER);
        contenedorTablero.add(panelLetras, BorderLayout.SOUTH);
        
        centro.add(contenedorTablero);
        main.add(centro, BorderLayout.CENTER);
        
        // BOTONES SUPERIORES
        btnSalir.addActionListener(e -> {
            if (bd != null) bd.closeBD();
            System.exit(0);
        });
        
        btnVolverMenu.addActionListener(e -> {
            if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
            setVisible(false);
            dispose();
        });
        
        btnCerrarSesion.addActionListener(e -> {
            setVisible(false);
            dispose();
            if (ventanaAnterior != null) ventanaAnterior.dispose(); 
            new VentanaInicioSesion(bd); 
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (bd != null) bd.closeBD();
                System.exit(0);
            }
        });
        
        escribirLog("Inicio de juego.");
        escribirLog("Turno: " + turnoActual);

        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    // CLICK EN CASILLA
    private void clicCasilla(int fila, int col) {
        Casilla casillaClickeada = tableroLogico.getCasillas(fila, col);
        Pieza piezaEnCasilla = casillaClickeada.getPieza();
        
        // CASO 1: no hay pieza seleccionada
        if (piezaSeleccionada == null) {
            if (piezaEnCasilla != null && piezaEnCasilla.getColor() == turnoActual) {
                piezaSeleccionada = piezaEnCasilla;
                setCasillaSeleccionada(casillaClickeada);
                
                lblPiezaSeleccionada.setText(piezaEnCasilla.getNombre() + " (" + piezaEnCasilla.getColor() + ")");
                
                if (piezaEnCasilla instanceof Becario || piezaEnCasilla instanceof MaquinaExpendedora || 
                    piezaEnCasilla instanceof Secretaria || piezaEnCasilla instanceof Alumno || 
                    piezaEnCasilla instanceof Rector || piezaEnCasilla instanceof Bedel) {
                    btnUsarHabilidad.setEnabled(true);
                    btnUsarHabilidad.setText("USAR: " + piezaEnCasilla.getNombre());
                } else {
                    btnUsarHabilidad.setEnabled(false);
                }
                
                resaltarCasilla(fila, col, true);
            } else {
                lblPiezaSeleccionada.setText("Selecciona una pieza de tu color");
            }
            return;
        }
        
        // CASO 2: ya hay pieza seleccionada
        int filaOrigen = piezaSeleccionada.getFila();
        int colOrigen = piezaSeleccionada.getColumna();
        
        // deselección
        if (fila == filaOrigen && col == colOrigen) {
            deseleccionarPieza();
            return;
        }
        
        // si clicas una pieza aliada, cambias selección
        if (piezaEnCasilla != null && piezaEnCasilla.getColor() == turnoActual) {
            resaltarCasilla(filaOrigen, colOrigen, false);
            piezaSeleccionada = piezaEnCasilla;
            setCasillaSeleccionada(casillaClickeada);
            lblPiezaSeleccionada.setText(piezaEnCasilla.getNombre() + " (" + piezaEnCasilla.getColor() + ")");
            resaltarCasilla(fila, col, true);
            return;
        }
        
        // movimiento normal: cualquier pieza que tenga movimientoValido implementado
        if (piezaSeleccionada.movimientoValido(fila, col, tableroLogico)) {
            realizarMovimiento(filaOrigen, colOrigen, fila, col);

            escribirLog("Movimiento: " + piezaSeleccionada.getNombre() + " -> " + coordHumana(fila, col));

            // Promoción de Alumno
            if (piezaSeleccionada instanceof Alumno) {
                int filaFinal = (piezaSeleccionada.getColor() == domain.Color.BLANCA) ? 7 : 0;
                if (fila == filaFinal) piezaSeleccionada.usarHabilidad(tableroLogico);
            }

            terminarAccionYTurno();
            deseleccionarPieza();
            actualizarTablero();
        } else {
            lblPiezaSeleccionada.setText("Movimiento inválido");
        }
    }
    
    private void realizarMovimiento(int fO, int cO, int fD, int cD) {
        Pieza piezaCapturada = tableroLogico.getCasillas(fD, cD).getPieza();
        
        if (piezaCapturada != null) {
        	escribirLog("Capturada: " + piezaCapturada.getNombre());
        }
        
        tableroLogico.getCasillas(fO, cO).setPieza(null);
        tableroLogico.getCasillas(fD, cD).setPieza(piezaSeleccionada);
        
        piezaSeleccionada.setFila(fD);
        piezaSeleccionada.setColumna(cD);
    }

    // Turnos con efecto Máquina
    private void terminarAccionYTurno() {
        if (perderTurno) {
            perderTurno = false;
            cambiarTurno();
            return;
        }

        if (movimientosExtra > 0) {
            movimientosExtra--;
            escribirLog("Movimiento extra activo. Sigue el turno: " + turnoActual);
            return;
        }

        cambiarTurno();
    }
    
    private void cambiarTurno() {
        turnoActual = (turnoActual == domain.Color.BLANCA) ? domain.Color.NEGRA : domain.Color.BLANCA;
        escribirLog("Turno: " + turnoActual);
        System.out.println("Turno de: " + turnoActual);
    }
    
    private void deseleccionarPieza() {
        if (piezaSeleccionada != null) {
            resaltarCasilla(piezaSeleccionada.getFila(), piezaSeleccionada.getColumna(), false);
        }
        piezaSeleccionada = null;
        setCasillaSeleccionada(null);
        lblPiezaSeleccionada.setText("Seleccione pieza...");
        btnUsarHabilidad.setEnabled(false);
    }
    
    private void resaltarCasilla(int fila, int col, boolean resaltar) {
        JButton boton = botonesCasillas[fila][col];
        if (resaltar) {
            boton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else {
            boton.setBorder(null);
        }
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
        
        btnUsarHabilidad.addActionListener(e -> ejecutarHabilidadEspecial());
        
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

        // LOG (ahora sí, campo)
        logArea = new JTextArea("");
        logArea.setEditable(false);
        logArea.setBackground(fondo.brighter()); 
        logArea.setForeground(Color.BLACK);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(logArea);
        
        panel.add(scroll, BorderLayout.CENTER);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        controles.setBackground(oscurecerBK);
        panel.add(controles, BorderLayout.SOUTH);

        return panel;
    }
    
    private void ejecutarHabilidadEspecial() {
        if (piezaSeleccionada == null) return;
        btnUsarHabilidad.setEnabled(false);

        if (piezaSeleccionada instanceof Becario ||
            piezaSeleccionada instanceof Secretaria ||
            piezaSeleccionada instanceof Alumno ||
            piezaSeleccionada instanceof Rector ||
            piezaSeleccionada instanceof Bedel) {
            
            piezaSeleccionada.usarHabilidad(tableroLogico);
            escribirLog("Habilidad usada: " + piezaSeleccionada.getNombre());
            actualizarTablero();

            // Para que “usar habilidad” cuente como acción del turno (como en clase)
            terminarAccionYTurno();
            deseleccionarPieza();
        } 
        else if (piezaSeleccionada instanceof MaquinaExpendedora) {
            MaquinaExpendedora maq = (MaquinaExpendedora) piezaSeleccionada;
            maq.usarHabilidadConDisplay(tableroLogico, lblPantallaMaquina);
            
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    SwingUtilities.invokeLater(() -> {

                        // APLICAR EFECTO DE MÁQUINA AL TURNO
                        if (maq.isPremio()) {
                            movimientosExtra += 1; // “doble movimiento” = 1 extra
                            escribirLog("Máquina: PREMIO -> tienes un movimiento extra.");
                        } else {
                            perderTurno = true;
                            escribirLog("Máquina: FALLO -> pierdes el turno.");
                        }

                        // reset premio para que no se reaplique
                        maq.setPremio(false);

                        btnUsarHabilidad.setEnabled(true);
                        actualizarTablero();

                        // La máquina como acción del turno
                        terminarAccionYTurno();
                        deseleccionarPieza();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void actualizarTablero() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casilla c = tableroLogico.getCasillas(i, j);
                Pieza p = c.getPieza();
                JButton b = botonesCasillas[i][j];
                
                if (p != null) {
                    String ruta = obtenerRutaImagen(p);
                    URL imgURL = getClass().getResource(ruta);
                    if (imgURL != null) {
                        ImageIcon icon = new ImageIcon(imgURL);
                        Image imgEscalada = icon.getImage().getScaledInstance(85, 85, Image.SCALE_SMOOTH);
                        b.setIcon(new ImageIcon(imgEscalada));
                        b.setText("");
                    } else {
                        b.setText(p.getNombre().substring(0, 2));
                        b.setIcon(null);
                    }
                } else {
                    b.setIcon(null);
                    b.setText("");
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

    private void escribirLog(String msg) {
        if (logArea == null) return;
        logArea.append("- " + msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private String coordHumana(int fila, int col) {
        char letra = (char)('A' + col);
        int num = fila + 1;
        return "" + letra + num;
    }

    public PanelTablero() {
        this(null, null);
    }

    public Casilla getCasillaSeleccionada() {
        return casillaSeleccionada;
    }

    public void setCasillaSeleccionada(Casilla casillaSeleccionada) {
        this.casillaSeleccionada = casillaSeleccionada;
    }

    public ConexionBD getBd() {
        return bd;
    }

    public void setBd(ConexionBD bd) {
        this.bd = bd;
    }
}
