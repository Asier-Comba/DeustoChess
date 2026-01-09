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
import bd.Historial;
import domain.Alumno;
import domain.Becario;
import domain.Bedel;
import domain.Casilla;
import domain.Expulsion;
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
	private Expulsion expulsion; 
	private JLabel lblEstadoJaque; 
	private JButton[][] botonesCasillas;

	private List<String> historialMovimientos = new ArrayList<>();
	private List<String[]> historialEstados = new ArrayList<>();
	private int movimientoActual = 0;
	private boolean modoReplay = false;

	private JLabel lblPiezaSeleccionada;
	private JLabel lblPantallaMaquina;
	private JButton btnUsarHabilidad;

	private domain.Color turnoActual;

	private int movimientosExtra;
	private boolean perderTurno;

	private JTextArea logArea;

	private ConexionBD bd;
	
	// === JUGADORES DE LA PARTIDA ===
	private Historial jugadorBlanco;
	private Historial jugadorNegro;

	// === CONSTRUCTOR MODIFICADO: RECIBE JUGADORES ===
	public PanelTablero(JFrame va, ConexionBD bd, Historial jBlanco, Historial jNegro) {
		this.ventanaAnterior = va;
		this.setBd(bd);
		this.jugadorBlanco = jBlanco;
		this.jugadorNegro = jNegro;

		this.tableroLogico = new Tablero();
		this.botonesCasillas = new JButton[8][8];
		this.piezaSeleccionada = null;
		this.turnoActual = domain.Color.BLANCA;
		this.expulsion = new Expulsion(tableroLogico);
		this.movimientosExtra = 0;
		this.perderTurno = false;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Título personalizado con nombres de los jugadores
		setTitle("DeustoChess - " + jugadorBlanco.getJugadorNom() + " (Blancas) VS " + jugadorNegro.getJugadorNom() + " (Negras)");
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
		String[] letras = { "A", "B", "C", "D", "E", "F", "G", "H" };
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
			if (bd != null)
				bd.closeBD();
			System.exit(0);
		});

		btnVolverMenu.addActionListener(e -> {
			if (ventanaAnterior != null)
				ventanaAnterior.setVisible(true);
			setVisible(false);
			dispose();
		});

		btnCerrarSesion.addActionListener(e -> {
			setVisible(false);
			dispose();
			if (ventanaAnterior != null)
				ventanaAnterior.dispose();
			
			new VentanaInicioSesion(bd);
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				if (bd != null)
					bd.closeBD();
				System.exit(0);
			}
		});

		guardarEstadoTablero();

		actualizarLogNormal();
		actualizarEstadoJaque();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// CLICK EN CASILLA
	private void clicCasilla(int fila, int col) {
	    if (modoReplay) {
	        JOptionPane.showMessageDialog(this, "Estás en modo HISTORIAL. Vuelve a JUGAR para mover piezas.");
	        return;
	    }
	    
	    if (expulsion.isPartidaFinalizada()) {
	        JOptionPane.showMessageDialog(this, "La partida ha finalizado.\n" + expulsion.obtenerMensajeEstado());
	        return;
	    }

	    Casilla casillaClickeada = tableroLogico.getCasillas(fila, col);
	    Pieza piezaEnCasilla = casillaClickeada.getPieza();

	    if (tableroLogico.isReunionUrgencia() && piezaEnCasilla != null) {
	        if (!(piezaEnCasilla instanceof Alumno) && piezaEnCasilla.getColor() == turnoActual) {
	            JOptionPane.showMessageDialog(this, 
	                "¡Reunión de urgencia activa! Solo puedes mover Alumnos este turno.");
	            return;
	        }
	    }


	    if (piezaSeleccionada == null) {
	        if (piezaEnCasilla != null && piezaEnCasilla.getColor() == turnoActual) {
	            piezaSeleccionada = piezaEnCasilla;
	            setCasillaSeleccionada(casillaClickeada);

	            lblPiezaSeleccionada.setText(piezaEnCasilla.getNombre() + " (" + piezaEnCasilla.getColor() + ")");

	            if (piezaEnCasilla instanceof Becario || piezaEnCasilla instanceof MaquinaExpendedora
	                    || piezaEnCasilla instanceof Secretaria || piezaEnCasilla instanceof Alumno
	                    || piezaEnCasilla instanceof Rector || piezaEnCasilla instanceof Bedel) {
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


	    int filaOrigen = piezaSeleccionada.getFila();
	    int colOrigen = piezaSeleccionada.getColumna();

	    if (fila == filaOrigen && col == colOrigen) {
	        deseleccionarPieza();
	        return;
	    }

	    if (piezaEnCasilla != null && piezaEnCasilla.getColor() == turnoActual) {
	        resaltarCasilla(filaOrigen, colOrigen, false);
	        piezaSeleccionada = piezaEnCasilla;
	        setCasillaSeleccionada(casillaClickeada);
	        lblPiezaSeleccionada.setText(piezaEnCasilla.getNombre() + " (" + piezaEnCasilla.getColor() + ")");
	        resaltarCasilla(fila, col, true);
	        return;
	    }

	    if (!piezaSeleccionada.movimientoValido(fila, col, tableroLogico)) {
	        lblPiezaSeleccionada.setText("❌ Movimiento inválido");
	        JOptionPane.showMessageDialog(this, 
	            "Movimiento inválido.\n\nEsta pieza no puede moverse a esa casilla según sus reglas de movimiento.",
	            "Movimiento inválido",
	            JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    if (!expulsion.esMovimientoLegal(piezaSeleccionada, fila, col)) {
	        lblPiezaSeleccionada.setText("❌ Movimiento ilegal (deja al Rector en Expediente)");
	        JOptionPane.showMessageDialog(this, 
	            "No puedes hacer ese movimiento.\n\nDejarías a tu Rector en Expediente (Jaque).",
	            "Rector en peligro",
	            JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    realizarMovimiento(filaOrigen, colOrigen, fila, col);

	    if (piezaSeleccionada instanceof Alumno) {
	        int filaFinal = (piezaSeleccionada.getColor() == domain.Color.BLANCA) ? 7 : 0;
	        if (fila == filaFinal)
	            piezaSeleccionada.usarHabilidad(tableroLogico);
	    }

	    terminarAccionYTurno();
	    deseleccionarPieza();
	    actualizarTablero();
	}
	
	private void actualizarEstadoJaque() {
	    String mensaje = expulsion.obtenerMensajeEstado();
	    
	    if (lblEstadoJaque != null) {
	        lblEstadoJaque.setText(mensaje);
	        
	        if (mensaje.contains("Expediente")) {
	            lblEstadoJaque.setForeground(Color.RED);
	        } else if (mensaje.contains("Expulsión") || mensaje.contains("Victoria")) {
	            lblEstadoJaque.setForeground(Color.ORANGE);
	        } else if (mensaje.contains("Empate")) {
	            lblEstadoJaque.setForeground(Color.YELLOW);
	        } else {
	            lblEstadoJaque.setForeground(Color.WHITE);
	        }
	    }
	}

	private void realizarMovimiento(int fO, int cO, int fD, int cD) {
		Pieza piezaCapturada = tableroLogico.getCasillas(fD, cD).getPieza();
		String nombreCapturada = null;

		if (piezaCapturada != null) {
			nombreCapturada = piezaCapturada.getNombre();
		}

		guardarEstadoTablero();

		tableroLogico.getCasillas(fO, cO).setPieza(null);
		tableroLogico.getCasillas(fD, cD).setPieza(piezaSeleccionada);

		piezaSeleccionada.setFila(fD);
		piezaSeleccionada.setColumna(cD);

		char colO = (char) ('A' + cO);
		char colD = (char) ('A' + cD);

		String movimiento = String.format("%d. %s %s: %c%d → %c%d", historialMovimientos.size() + 1,
				piezaSeleccionada.getNombre(), piezaSeleccionada.getColor(), colO, fO + 1, colD, fD + 1);

		if (nombreCapturada != null) {
			movimiento += " ✗" + nombreCapturada;
		}

		historialMovimientos.add(movimiento);
		movimientoActual = historialMovimientos.size();

		actualizarLogNormal();
	}

	private void terminarAccionYTurno() {
		if (perderTurno) {
			perderTurno = false;
			cambiarTurno();
			return;
		}

		if (movimientosExtra > 0) {
			movimientosExtra--;
			actualizarLogNormal();
			return;
		}

		cambiarTurno();
	}

	private void cambiarTurno() {
	    if (tableroLogico.isReunionUrgencia()) {
	        tableroLogico.setReunionUrgencia(false);
	        JOptionPane.showMessageDialog(this, "La reunión de urgencia ha finalizado.");
	    }
	    
	    turnoActual = (turnoActual == domain.Color.BLANCA) ? domain.Color.NEGRA : domain.Color.BLANCA;
	    expulsion.cambiarTurno();
	    actualizarLogNormal();
	    actualizarEstadoJaque();
	    
	    // PRIMERO: verificar si la partida ha finalizado (jaque mate o empate)
	    if (expulsion.isPartidaFinalizada()) {
	        domain.Color colorGanador = expulsion.getGanador();
	        
	        if (colorGanador != null) {
	        	// HAY UN GANADOR
	        	if (colorGanador == domain.Color.BLANCA) {
	        		// GANAN LAS BLANCAS
	        		bd.sumarVictoria(jugadorBlanco.getIdJ());
	        		bd.sumarDerrota(jugadorNegro.getIdJ());
	        		
	        		JOptionPane.showMessageDialog(this, 
		                "¡EXPULSIÓN DEL RECTOR!\n\n" +
		                "GANADOR: " + jugadorBlanco.getJugadorNom() + " (Blancas)\n" +
		                "PERDEDOR: " + jugadorNegro.getJugadorNom() + " (Negras)\n\n" +
		                "Las estadísticas han sido actualizadas en la base de datos.",
		                "¡Fin de la partida!",
		                JOptionPane.INFORMATION_MESSAGE);
	        		
	        	} else {
	        		// GANAN LAS NEGRAS
	        		bd.sumarVictoria(jugadorNegro.getIdJ());
	        		bd.sumarDerrota(jugadorBlanco.getIdJ());
	        		
	        		JOptionPane.showMessageDialog(this, 
		                "¡EXPULSIÓN DEL RECTOR!\n\n" +
		                "GANADOR: " + jugadorNegro.getJugadorNom() + " (Negras)\n" +
		                "PERDEDOR: " + jugadorBlanco.getJugadorNom() + " (Blancas)\n\n" +
		                "Las estadísticas han sido actualizadas en la base de datos.",
		                "¡Fin de la partida!",
		                JOptionPane.INFORMATION_MESSAGE);
	        	}
	        } else {
	        	// EMPATE
	            JOptionPane.showMessageDialog(this, 
	                "EMPATE\n\nLa partida termina en tablas por ahogado.\nNo se actualizan estadísticas.",
	                "Empate",
	                JOptionPane.INFORMATION_MESSAGE);
	        }
	        return; // IMPORTANTE: Salir aquí para no mostrar el mensaje de jaque
	    }
	    
	    // SEGUNDO: Si la partida NO ha finalizado, verificar si hay jaque (sin mensaje duplicado)
	    if (expulsion.estaEnJaque(turnoActual)) {
	        JOptionPane.showMessageDialog(this, 
	            "¡EXPEDIENTE!\n\nEl Rector " + turnoActual + " está bajo amenaza.\nDebes protegerlo en tu próximo movimiento.",
	            "¡Expediente al Rector!",
	            JOptionPane.WARNING_MESSAGE);
	    }
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

		lblEstadoJaque = new JLabel("Turno: Blancas");
		lblEstadoJaque.setFont(new Font("Arial", Font.BOLD, 16));
		lblEstadoJaque.setForeground(Color.WHITE);
		lblEstadoJaque.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		lblEstadoJaque.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

		
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
		p.add(lblEstadoJaque); 
	    p.add(Box.createVerticalStrut(10));
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

		btnInicio.addActionListener(e -> irAlInicio());
		btnAtras.addActionListener(e -> movimientoAnterior());
		btnAdelante.addActionListener(e -> movimientoSiguiente());
		btnFin.addActionListener(e -> irAlFinal());

		btnJugar.addActionListener(e -> {
			modoReplay = false;
			btnInicio.setEnabled(false);
			btnAtras.setEnabled(false);
			btnAdelante.setEnabled(false);
			btnFin.setEnabled(false);
			actualizarLogNormal();
		});

		btnMovimientos.addActionListener(e -> {
			if (historialMovimientos.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Aún no hay movimientos registrados.", "Sin movimientos",
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

		btnInicio.setEnabled(false);
		btnAtras.setEnabled(false);
		btnAdelante.setEnabled(false);
		btnFin.setEnabled(false);

		panel.add(controles, BorderLayout.SOUTH);

		return panel;
	}

	private void ejecutarHabilidadEspecial() {
		if (piezaSeleccionada == null)
			return;

		btnUsarHabilidad.setEnabled(false);

		if (piezaSeleccionada instanceof Becario || piezaSeleccionada instanceof Secretaria
				|| piezaSeleccionada instanceof Alumno || piezaSeleccionada instanceof Rector
				|| piezaSeleccionada instanceof Bedel) {

			piezaSeleccionada.usarHabilidad(tableroLogico);
			actualizarTablero();

			terminarAccionYTurno();
			deseleccionarPieza();
		} else if (piezaSeleccionada instanceof MaquinaExpendedora) {
			MaquinaExpendedora maq = (MaquinaExpendedora) piezaSeleccionada;
			maq.usarHabilidadConDisplay(tableroLogico, lblPantallaMaquina);

			new Thread(() -> {
				try {
					Thread.sleep(3000);
					SwingUtilities.invokeLater(() -> {

						if (maq.isPremio()) {
							movimientosExtra += 1;
						} else {
							perderTurno = true;
						}

						maq.setPremio(false);

						btnUsarHabilidad.setEnabled(true);
						actualizarTablero();

						terminarAccionYTurno();
						deseleccionarPieza();
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		} else {
			btnUsarHabilidad.setEnabled(true);
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

	// Historial
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
	    if (logArea == null)
	        return;

	    StringBuilder sb = new StringBuilder();

	    sb.append("=== DEUSTOCHESS ===\n");
	    sb.append("Blancas: ").append(jugadorBlanco.getJugadorNom()).append("\n");
	    sb.append("Negras: ").append(jugadorNegro.getJugadorNom()).append("\n\n");
	    
	    if (expulsion.isPartidaFinalizada()) {
	        sb.append("PARTIDA FINALIZADA\n");
	        if (expulsion.getGanador() != null) {
	            sb.append("Victoria: ").append(expulsion.getGanador()).append("\n");
	        } else {
	            sb.append("EMPATE\n");
	        }
	    } else if (expulsion.estaEnJaque(turnoActual)) {
	        sb.append("¡EXPEDIENTE AL RECTOR ").append(turnoActual).append("!\n");
	    }
	    
	    sb.append("Turno actual: ").append(turnoActual).append("\n");
	    sb.append("Movimientos: ").append(historialMovimientos.size()).append("\n");
	    
	    if (movimientosExtra > 0)
	        sb.append("Efecto: MOVIMIENTO EXTRA\n");
	    if (perderTurno)
	        sb.append("Efecto: PIERDE TURNO\n");
	        
	    sb.append("\n--- ÚLTIMOS MOVIMIENTOS ---\n\n");

	    int inicio = Math.max(0, historialMovimientos.size() - 15);
	    for (int i = inicio; i < historialMovimientos.size(); i++) {
	        sb.append(historialMovimientos.get(i)).append("\n");
	    }

	    logArea.setText(sb.toString());
	    logArea.setCaretPosition(logArea.getDocument().getLength());
	}


	private void actualizarLogHistorial() {
		if (logArea == null)
			return;

		if (historialMovimientos.isEmpty()) {
			logArea.setText("=== HISTORIAL ===\n\nNo hay movimientos registrados.");
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("=== HISTORIAL DE MOVIMIENTOS ===\n\n");
		sb.append("Movimiento: ").append(movimientoActual).append(" / ").append(historialMovimientos.size())
				.append("\n\n");

		for (int i = 0; i < historialMovimientos.size(); i++) {
			if (i == movimientoActual - 1) {
				sb.append(">>> ").append(historialMovimientos.get(i)).append(" <<<\n");
			} else {
				sb.append(historialMovimientos.get(i)).append("\n");
			}
		}

		logArea.setText(sb.toString());

		if (movimientoActual > 0) {
			try {
				int pos = logArea.getText().indexOf(">>>");
				if (pos >= 0) {
					logArea.setCaretPosition(pos);
				}
			} catch (Exception e) {
				// ignorar
			}
		}
	}

	private void irAlInicio() {
		if (!modoReplay || historialMovimientos.isEmpty())
			return;
		movimientoActual = 0;
		actualizarLogHistorial();
	}

	private void movimientoAnterior() {
		if (!modoReplay || movimientoActual <= 0)
			return;
		movimientoActual--;
		actualizarLogHistorial();
	}

	private void movimientoSiguiente() {
		if (!modoReplay || movimientoActual >= historialMovimientos.size())
			return;
		movimientoActual++;
		actualizarLogHistorial();
	}

	private void irAlFinal() {
		if (!modoReplay)
			return;
		movimientoActual = historialMovimientos.size();
		actualizarLogHistorial();
	}

}