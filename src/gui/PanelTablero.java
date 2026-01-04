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
import java.util.ArrayList;
import java.util.List;

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
    
    // Matriz de botones que representa visualmente el tablero
    private JButton[][] botonesCasillas;
    
    private JTextArea logArea;
    private List<String> historialMovimientos = new ArrayList<>();
    private List<String[]> historialEstados = new ArrayList<>(); // Para guardar estados del tablero
    private int movimientoActual = 0;
    private boolean modoReplay = false;
    
    private JLabel lblPiezaSeleccionada;
    private JLabel lblPantallaMaquina; 
    private JButton btnUsarHabilidad;
    
    // Control de turnos
    private domain.Color turnoActual;
 
    private ConexionBD bd;
    
    // Inicializamos la ventana y sus componentes
    public PanelTablero(JFrame va, ConexionBD bd) {
        this.ventanaAnterior = va;
        this.setBd(bd);
        
        this.tableroLogico = new Tablero();
        this.botonesCasillas = new JButton[8][8];
        this.piezaSeleccionada = null;
        this.turnoActual = domain.Color.BLANCA; // Comienzan las blancas

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DeustoChess - Partida 1 vs 1");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel principal con imagen de fondo
        PanelConFondo main = new PanelConFondo("/images/FondoJuego.png");
        main.setLayout(new BorderLayout());
        setContentPane(main);

        // Paleta de colores y fuentes
        Color colorAzulDeusto = new Color(58, 117, 173); 
        Color colorFondoAzul = new Color(0, 123, 255); 
        Color colorTexto = new Color(230, 235, 255); 
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        // PANEL NORTE
        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false); // Transparente para ver el fondo
        norte.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        
        // Parte izquierda: Logo y Título
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

        // Parte derecha: Botones de navegación
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
                
        // PANELES LATERALES
        // Panel izquierdo: Habilidades especiales y pieza seleccionada
        JPanel panelIzquierdo = crearPanelIzquierdo(colorFondoAzul, colorTexto);
        main.add(panelIzquierdo, BorderLayout.WEST);
        
        // Panel derecho: Log de movimientos e historial (Por implementar funcionalidad)
        JPanel panelDerecho = crearPanelLateral(colorFondoAzul, colorTexto);
        main.add(panelDerecho, BorderLayout.EAST);

        // PANEL CENTRAL: TABLERO
        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel contenedorTablero = new JPanel(new BorderLayout(5, 5));
        contenedorTablero.setOpaque(false);
        
        // Coordenadas del 1-8 a la izquierda
        JPanel panelNumeros = new JPanel(new GridLayout(8, 1));
        panelNumeros.setOpaque(false);
        panelNumeros.setPreferredSize(new Dimension(30, 725));
        for (int i = 8; i >= 1; i--) {
            JLabel lblNum = new JLabel(String.valueOf(i), JLabel.CENTER);
            lblNum.setFont(new Font("Arial", Font.BOLD, 20));
            lblNum.setForeground(Color.BLACK);
            panelNumeros.add(lblNum);
        }
        
        // Coordenadas alfabéticas de A-H abajo
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
        
        // Colocamos las piezas en los botones creados
        actualizarTablero();
        
        contenedorTablero.add(panelNumeros, BorderLayout.WEST);
        contenedorTablero.add(tableroVisual, BorderLayout.CENTER);
        contenedorTablero.add(panelLetras, BorderLayout.SOUTH);
        
        centro.add(contenedorTablero);
        main.add(centro, BorderLayout.CENTER);
        
        // EVENTOS DE BOTONES SUPERIORES
        btnSalir.addActionListener(e -> {
            System.exit(0);
            bd.closeBD();
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
        
        // Listener para cerrar la BD correctamente
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                bd.closeBD();
                System.exit(0);
            }
        });
        
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    // Manejos de click en casilla
    private void clicCasilla(int fila, int col) {
        Casilla casillaClickeada = tableroLogico.getCasillas(fila, col);
        Pieza piezaEnCasilla = casillaClickeada.getPieza();
        
        // CASO 1: No hay pieza seleccionada previamente
        if (piezaSeleccionada == null) {
            // Verificamos si hay pieza y si es del color del turno actual
            if (piezaEnCasilla != null && piezaEnCasilla.getColor() == turnoActual) {
                piezaSeleccionada = piezaEnCasilla;
                setCasillaSeleccionada(casillaClickeada);
                
                lblPiezaSeleccionada.setText(piezaEnCasilla.getNombre() + " (" + piezaEnCasilla.getColor() + ")");
                
                // Habilitar botón de habilidad
                if (piezaEnCasilla instanceof Becario || piezaEnCasilla instanceof MaquinaExpendedora || 
                    piezaEnCasilla instanceof Secretaria || piezaEnCasilla instanceof Alumno || 
                    piezaEnCasilla instanceof Rector || piezaEnCasilla instanceof Bedel) {
                    btnUsarHabilidad.setEnabled(true);
                    btnUsarHabilidad.setText("USAR: " + piezaEnCasilla.getNombre());
                } else {
                    btnUsarHabilidad.setEnabled(false);
                }
                
                // Resaltar la casilla seleccionada
                resaltarCasilla(fila, col, true);
            } else {
                lblPiezaSeleccionada.setText("Selecciona una pieza de tu color");
            }
        } 
        // CASO 2: Ya hay una pieza seleccionada
        else {
            int filaOrigen = piezaSeleccionada.getFila();
            int colOrigen = piezaSeleccionada.getColumna();
            
            // Deseleccionamos clicando en la misma casilla
            if (fila == filaOrigen && col == colOrigen) {
                deseleccionarPieza();
                return;
            }
            
            // RESTRICCIÓN MOMENTÁNEA: Solo los Alumnos se mueven manualmente (los otros por habilidades o reglas especiales)
            if (!(piezaSeleccionada instanceof Alumno)) {
                lblPiezaSeleccionada.setText("Solo los Alumnos pueden moverse manualmente");
                JOptionPane.showMessageDialog(this, 
                    "Movimiento restringido temporalmente\n\n" +
                    "Solo los ALUMNOS pueden moverse manualmente.\n" +
                    "Las demás piezas solo se mueven con sus habilidades.");
                return;
            }
            
            // Verificar si el movimiento es válido
            if (piezaSeleccionada.movimientoValido(fila, col, tableroLogico)) {
                realizarMovimiento(filaOrigen, colOrigen, fila, col);
                
                // Habilidad especial para Alumno al llegar a la casilla de promoción
                if (piezaSeleccionada instanceof Alumno) {
                    int filaFinal = (piezaSeleccionada.getColor() == domain.Color.BLANCA) ? 7 : 0;
                    if (fila == filaFinal) piezaSeleccionada.usarHabilidad(tableroLogico);
                }
                
                cambiarTurno();
                deseleccionarPieza();
                actualizarTablero();
            } else {
                lblPiezaSeleccionada.setText("Movimiento inválido");
            }
        }
    }
    
    private void realizarMovimiento(int fO, int cO, int fD, int cD) {
        Pieza piezaCapturada = tableroLogico.getCasillas(fD, cD).getPieza();
        String nombreCapturada = null;
        
        if (piezaCapturada != null) {
            nombreCapturada = piezaCapturada.getNombre();
            System.out.println("Capturada: " + nombreCapturada);
        }
        
        // Guardar estado antes del movimiento
        guardarEstadoTablero();
        
        // Realizar el movimiento
        tableroLogico.getCasillas(fO, cO).setPieza(null);
        tableroLogico.getCasillas(fD, cD).setPieza(piezaSeleccionada);
        
        piezaSeleccionada.setFila(fD);
        piezaSeleccionada.setColumna(cD);
        
        // Registrar movimiento
        char colO = (char)('A' + cO);
        char colD = (char)('A' + cD);
        
        String movimiento = String.format("%d. %s %s: %c%d → %c%d",  // IA GENERATIVA
            historialMovimientos.size() + 1, piezaSeleccionada.getNombre(), piezaSeleccionada.getColor(), colO, fO + 1, colD, fD + 1);
        
        if (nombreCapturada != null) {
            movimiento += " ✗" + nombreCapturada;
        }
        
        historialMovimientos.add(movimiento);
        movimientoActual = historialMovimientos.size();
        
        // Actualizar log
        if (!modoReplay) {
            actualizarLogNormal();
        }
    }
    
    // Alternar el turno entre blancas y azules
    private void cambiarTurno() {
        turnoActual = (turnoActual == domain.Color.BLANCA) ? domain.Color.NEGRA : domain.Color.BLANCA;
        System.out.println("Turno de: " + turnoActual);
    }
    
    // Limpiar estado de selección
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
    
    // Panel izquierdo donde se activarán habs especiales y selección de pieza
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
        
        // Elementos visuales y espaciadores (IA GENERATIVA)
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

    // Panel lateral derecho para guardar los movimientos
    private JPanel crearPanelLateral(Color fondo, Color frente) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondo);
        panel.setOpaque(true); 
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        panel.setPreferredSize(new Dimension(350, 1)); 

        // Panel pestañas superior
        JPanel pestanas = new JPanel(new GridLayout(1, 2));
        Font fuenteBoton = new Font("Arial", Font.BOLD, 12);
        Color oscurecerBK = fondo.darker(); 
        
        JButton btnJugar = new JButton("JUGAR");
        estilarBoton(btnJugar, fuenteBoton, frente, oscurecerBK);
        pestanas.add(btnJugar);
        
        JButton btnMovimientos = new JButton("HISTORIAL");
        estilarBoton(btnMovimientos, fuenteBoton, frente, oscurecerBK);
        pestanas.add(btnMovimientos);
        
        panel.add(pestanas, BorderLayout.NORTH);

        // Área para mostrar movimientos
        logArea = new JTextArea("=== DEUSTOCHESS ===\n\nPartida Nueva\nTurno: Blancas\n\n");
        logArea.setEditable(false);
        logArea.setBackground(fondo.brighter()); 
        logArea.setForeground(Color.BLACK);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
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
        
        // === LISTENERS PARA NAVEGACIÓN ===
        btnInicio.addActionListener(e -> irAlInicio());
        btnAtras.addActionListener(e -> movimientoAnterior());
        btnAdelante.addActionListener(e -> movimientoSiguiente());
        btnFin.addActionListener(e -> irAlFinal());
        
        // Cambiar entre modo juego y modo historial
        btnJugar.addActionListener(e -> {
            modoReplay = false;
            irAlFinal(); // Volver al estado actual
            btnInicio.setEnabled(false);
            btnAtras.setEnabled(false);
            btnAdelante.setEnabled(false);
            btnFin.setEnabled(false);
            actualizarLogNormal();
        });
        
        btnMovimientos.addActionListener(e -> {
            if (historialMovimientos.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Aún no hay movimientos registrados.",
                    "Sin movimientos",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            modoReplay = true;
            btnInicio.setEnabled(true);
            btnAtras.setEnabled(true);
            btnAdelante.setEnabled(true);
            btnFin.setEnabled(true);
            actualizarLogHistorial();
        });
        
        controles.add(btnInicio);
        controles.add(btnAtras);
        controles.add(btnAdelante);
        controles.add(btnFin);
        
        // Inicialmente deshabilitados
        btnInicio.setEnabled(false);
        btnAtras.setEnabled(false);
        btnAdelante.setEnabled(false);
        btnFin.setEnabled(false);
        
        panel.add(controles, BorderLayout.SOUTH);
        return panel;
    }

    
    // Ejecutar habilidades especiales según el tipo de pieza
    private void ejecutarHabilidadEspecial() {
        if (piezaSeleccionada == null) return;
        btnUsarHabilidad.setEnabled(false);

        // Habilidades que modifican el tablero
        if (piezaSeleccionada instanceof Becario ||
            piezaSeleccionada instanceof Secretaria ||
            piezaSeleccionada instanceof Alumno ||
            piezaSeleccionada instanceof Rector ||
            piezaSeleccionada instanceof Bedel) {
            
            piezaSeleccionada.usarHabilidad(tableroLogico);
            actualizarTablero();
        } 
        // Caso de la máquina expendedora: interactúa con el display
        else if (piezaSeleccionada instanceof MaquinaExpendedora) {
            MaquinaExpendedora maq = (MaquinaExpendedora) piezaSeleccionada;
            maq.usarHabilidadConDisplay(tableroLogico, lblPantallaMaquina);
            
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    SwingUtilities.invokeLater(() -> {
                        btnUsarHabilidad.setEnabled(true);
                        actualizarTablero();
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
                    } else {
                        b.setText(p.getNombre().substring(0, 2));
                    }
                } else {
                    b.setIcon(null);
                    b.setText("");
                }
            }
        }
    }
    
    // Aplicamos estilo común a los botones
    private void estilarBoton(JButton btn, Font f, Color fg, Color bg) {
        btn.setFont(f);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }
    
    // Generamos la ruta y color de la pieza
    private String obtenerRutaImagen(Pieza p) {
        String nombreClase = p.getClass().getSimpleName();
        String color = p.getColor().toString();
        return "/images/" + nombreClase + "_" + color + ".png";
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
    
    
    private void guardarEstadoTablero() {
        String[] estado = new String[64];
        int index = 0;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza p = tableroLogico.getCasillas(i, j).getPieza();
                if (p != null) {
                    estado[index] = p.getNombre() + "|" + p.getColor() + "|" + i + "|" + j;
                } else {
                    estado[index] = "VACIO";
                }
                index++;
            }
        }
        
        historialEstados.add(estado);
    }

    private void actualizarLogNormal() {
        StringBuilder sb = new StringBuilder(); // IA GENERATIVA
        
        sb.append("=== DEUSTOCHESS ===\n\n");
        sb.append("Turno actual: ").append(turnoActual).append("\n");
        sb.append("Movimientos: ").append(historialMovimientos.size()).append("\n\n");
        sb.append("--- ÚLTIMOS MOVIMIENTOS ---\n\n");
        
        // Mostrar los últimos 15 movimientos
        int inicio = Math.max(0, historialMovimientos.size() - 15);
        for (int i = inicio; i < historialMovimientos.size(); i++) {
            sb.append(historialMovimientos.get(i)).append("\n");
        }
        
        logArea.setText(sb.toString());
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void actualizarLogHistorial() {
        if (historialMovimientos.isEmpty()) {
            logArea.setText("=== HISTORIAL ===\n\nNo hay movimientos registrados.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== HISTORIAL DE MOVIMIENTOS ===\n\n");
        sb.append("Movimiento: ").append(movimientoActual).append(" / ").append(historialMovimientos.size()).append("\n\n");
        
        for (int i = 0; i < historialMovimientos.size(); i++) {
            if (i == movimientoActual - 1) {
                sb.append(">>> ").append(historialMovimientos.get(i)).append(" <<<\n");
            } else {
                sb.append(historialMovimientos.get(i)).append("\n");
            }
        }
        
        logArea.setText(sb.toString());
        
        // Scroll al movimiento actual
        if (movimientoActual > 0) {
            try {
                int pos = logArea.getText().indexOf(">>>");
                if (pos >= 0) {
                    logArea.setCaretPosition(pos);
                }
            } catch (Exception e) {
                // Ignorar
            }
        }
    }

    private void irAlInicio() {
        if (!modoReplay || historialMovimientos.isEmpty()) return;
        movimientoActual = 0;
        actualizarLogHistorial();
    }

    private void movimientoAnterior() {
        if (!modoReplay || movimientoActual <= 0) return;
        movimientoActual--;
        actualizarLogHistorial();
    }

    private void movimientoSiguiente() {
        if (!modoReplay || movimientoActual >= historialMovimientos.size()) return;
        movimientoActual++;
        actualizarLogHistorial();
    }

    private void irAlFinal() {
        if (!modoReplay) return;
        movimientoActual = historialMovimientos.size();
        actualizarLogHistorial();
    }
}
