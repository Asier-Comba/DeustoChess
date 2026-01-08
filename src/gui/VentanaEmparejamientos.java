package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class VentanaEmparejamientos extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame ventanaAnterior;

	private JTextArea taNombres;
	private JButton btnGenerar;
	private JButton btnVolver;
	private JTextArea taResultado;
	private JLabel lblInfo;

	public VentanaEmparejamientos(JFrame va) {
		this.ventanaAnterior = va;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Generar emparejamientos");
		setSize(980, 720);
		setLocationRelativeTo(null);

		PanelConFondo root = new PanelConFondo("/images/Fondo.png");
		root.setLayout(new BorderLayout());
		setContentPane(root);

		Color azul = new Color(0, 123, 255);
		Color texto = new Color(230, 235, 255);

		// ====== NORTE ======
		JPanel norte = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		norte.setOpaque(false);

		JLabel titulo = new JLabel("GENERAR EMPAREJAMIENTOS (recursividad: combinaciones edition)");
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		titulo.setForeground(Color.WHITE);

		norte.add(titulo);

		root.add(norte, BorderLayout.NORTH);

		// ====== CENTRO ======
		JPanel centro = new JPanel();
		centro.setOpaque(false);
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

		JLabel lblInstrucciones = new JLabel("Escribe nombres separados por comas o por líneas (ej: Asier, Jon, Maite)");
		lblInstrucciones.setFont(new Font("Arial", Font.BOLD, 15));
		lblInstrucciones.setForeground(Color.WHITE);
		lblInstrucciones.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		centro.add(lblInstrucciones);
		centro.add(Box.createVerticalStrut(10));

		taNombres = new JTextArea();
		taNombres.setFont(new Font("Monospaced", Font.PLAIN, 13));
		taNombres.setLineWrap(true);
		taNombres.setWrapStyleWord(true);
		taNombres.setText("Asier, Jon, Maite, Leire");

		JScrollPane scrollNombres = new JScrollPane(taNombres);
		scrollNombres.setPreferredSize(new Dimension(880, 120));
		scrollNombres.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);

		centro.add(scrollNombres);
		centro.add(Box.createVerticalStrut(10));

		JPanel filaBoton = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		filaBoton.setOpaque(false);

		btnGenerar = new JButton("GENERAR");
		estilarBoton(btnGenerar, new Font("Arial", Font.BOLD, 16), texto, azul);

		lblInfo = new JLabel(" ");
		lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		lblInfo.setForeground(Color.WHITE);

		filaBoton.add(btnGenerar);
		filaBoton.add(lblInfo);

		centro.add(filaBoton);
		centro.add(Box.createVerticalStrut(10));

		taResultado = new JTextArea();
		taResultado.setEditable(false);
		taResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
		taResultado.setLineWrap(true);
		taResultado.setWrapStyleWord(true);

		JScrollPane scroll = new JScrollPane(taResultado);
		scroll.setPreferredSize(new Dimension(880, 420));
		scroll.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);

		centro.add(scroll);

		root.add(centro, BorderLayout.CENTER);

		// ====== SUR ======
		JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		sur.setOpaque(false);

		btnVolver = new JButton("VOLVER AL MENÚ");
		estilarBoton(btnVolver, new Font("Arial", Font.BOLD, 16), texto, azul);

		sur.add(btnVolver);
		root.add(sur, BorderLayout.SOUTH);

		// ====== ACCIONES ======
		btnVolver.addActionListener(e -> {
			setVisible(false);
			dispose();
			if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
		});

		btnGenerar.addActionListener(e -> generar());

		setVisible(true);
	}

	private void estilarBoton(JButton btn, Font f, Color fg, Color bg) {
		btn.setFont(f);
		btn.setForeground(fg);
		btn.setBackground(bg);
		btn.setOpaque(true);
		btn.setBorderPainted(false);
	}

	private void generar() {
		List<String> nombres = parsearNombres(taNombres.getText());

		if (nombres.size() < 2) {
			JOptionPane.showMessageDialog(this, "Mete al menos 2 nombres (si no, no hay drama... pero tampoco emparejamientos).");
			return;
		}

		// Aviso anti-explosión combinatoria
		// (12 ya son 10.395 emparejamientos; 14 ya son 135.135; 16 es apocalipsis)
		if (nombres.size() > 12) {
			int op = JOptionPane.showConfirmDialog(this,
					"Con " + nombres.size() + " personas salen MUCHÍSIMOS emparejamientos.\n"
							+ "Puede tardar o volverse un pergamino infinito.\n\n"
							+ "¿Quieres seguir?",
					"Aviso", JOptionPane.YES_NO_OPTION);
			if (op != JOptionPane.YES_OPTION)
				return;
		}

		btnGenerar.setEnabled(false);
		taResultado.setText("");
		lblInfo.setText("Calculando... (los becarios están generando combinaciones)");

		new Thread(() -> {
			List<String> res = generarEmparejamientosComoTexto(nombres);

			SwingUtilities.invokeLater(() -> {
				StringBuilder sb = new StringBuilder();
				sb.append("Participantes: ").append(nombres.size()).append("\n");
				sb.append("Emparejamientos generados: ").append(res.size()).append("\n\n");

				int cont = 1;
				for (String r : res) {
					sb.append(cont).append(") ").append(r).append("\n");
					cont++;
				}

				taResultado.setText(sb.toString());
				taResultado.setCaretPosition(0);
				lblInfo.setText("Listo ✅  (" + res.size() + " emparejamientos)");
				btnGenerar.setEnabled(true);
			});
		}).start();
	}

	// ==========================
	// Parseo de nombres
	// ==========================
	private List<String> parsearNombres(String texto) {
		List<String> nombres = new ArrayList<>();

		if (texto == null) return nombres;

		// Separa por comas o saltos de línea
		String normalizado = texto.replace("\r", "\n").replace(",", "\n");
		String[] partes = normalizado.split("\n");

		for (String p : partes) {
			String n = p.trim();
			if (!n.isEmpty() && !nombres.contains(n)) {
				nombres.add(n);
			}
		}

		return nombres;
	}

	// =========================================================
	// RECURSIVIDAD (BACKTRACKING) – emparejamientos completos
	// =========================================================
	private List<String> generarEmparejamientosComoTexto(List<String> nombres) {
		List<String> resultados = new ArrayList<>();
		int n = nombres.size();

		if (n < 2) return resultados;

		// Si impar: elegimos quién descansa y emparejamos el resto
		if (n % 2 == 1) {
			for (int i = 0; i < nombres.size(); i++) {
				String bye = nombres.get(i);
				List<String> resto = new ArrayList<>(nombres);
				resto.remove(i);

				List<String> parciales = new ArrayList<>();
				backtrackingParejas(resto, new ArrayList<>(), parciales);

				for (String emp : parciales) {
					resultados.add("Descansa " + bye + " | " + emp);
				}
			}
		} else {
			backtrackingParejas(nombres, new ArrayList<>(), resultados);
		}

		return resultados;
	}

	/**
	 * Backtracking:
	 * - cojo el primero de libres
	 * - lo emparejo con cada uno de los demás
	 * - recursión con el resto
	 */
	private void backtrackingParejas(List<String> libres, List<String[]> parejasActuales, List<String> resultados) {
		if (libres.isEmpty()) {
			resultados.add(formatearParejas(parejasActuales));
			return;
		}

		String primero = libres.get(0);

		for (int i = 1; i < libres.size(); i++) {
			String segundo = libres.get(i);

			// nuevos libres sin primero y segundo
			List<String> nuevosLibres = new ArrayList<>(libres);
			nuevosLibres.remove(primero);
			nuevosLibres.remove(segundo);

			// elegir
			parejasActuales.add(new String[] { primero, segundo });

			// recur
			backtrackingParejas(nuevosLibres, parejasActuales, resultados);

			// deshacer
			parejasActuales.remove(parejasActuales.size() - 1);
		}
	}

	private String formatearParejas(List<String[]> parejas) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parejas.size(); i++) {
			String[] par = parejas.get(i);
			sb.append("(").append(par[0]).append(" vs ").append(par[1]).append(")");
			if (i < parejas.size() - 1) sb.append("  ");
		}
		return sb.toString();
	}
}
