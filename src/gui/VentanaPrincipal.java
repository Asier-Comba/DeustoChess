package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private JFrame ventanaActual, ventanaAnterior;

    public VentanaPrincipal(JFrame va) {
        ventanaActual = this;
        ventanaAnterior = va;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("DeustoChess");
        setExtendedState(MAXIMIZED_BOTH);

        // === CONTENEDOR RAÍZ ===
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(30, 30, 30));
        setContentPane(root);

        // === CABECERA (TÍTULO + SUBTÍTULO) ===
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(30, 30, 30));

        JLabel titulo = new JLabel("DeustoChess", SwingConstants.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font(Font.DIALOG, Font.BOLD, 80));
        titulo.setForeground(new Color(230, 235, 255));

        JLabel subtitulo = new JLabel("Elija modo de juego:", SwingConstants.CENTER);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 30));
        subtitulo.setForeground(new Color(230, 235, 255));

        header.add(Box.createVerticalStrut(40));
        header.add(titulo);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitulo);
        header.add(Box.createVerticalStrut(20));
        root.add(header, BorderLayout.NORTH);

        // === CENTRO: COLUMNA DE BOTONES CENTRADA VERTICAL/HORIZONTALMENTE ===
        // Wrapper con GridBagLayout para forzar centrado total
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(30, 30, 30));
        root.add(centerWrapper, BorderLayout.CENTER);

        // Columna con BoxLayout en Y
        JPanel col = new JPanel();
        col.setBackground(new Color(30, 30, 30));
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));

        // Botones
        JButton btn1v1        = crearBoton("1 vs 1");
        JButton btn1vsIA      = crearBoton("1 vs IA");
        JButton btnRepet      = crearBoton("Repeticiones");
        JButton btnSalir      = crearBoton("Salir");
        JButton btnCerrarSess = crearBoton("Cerrar sesión");

        // Añadir a la columna con separaciones
        for (JButton b : new JButton[]{btn1v1, btn1vsIA, btnRepet, btnSalir, btnCerrarSess}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            col.add(Box.createVerticalStrut(15));
            col.add(b);
        }
        col.add(Box.createVerticalStrut(15));

        // Insertar la columna centrada
        centerWrapper.add(col, new GridBagConstraints());

        // === ACCIONES ===
        btnSalir.addActionListener(e -> System.exit(0));
        btnCerrarSess.addActionListener(e -> {
            ventanaActual.setVisible(false);
            ventanaAnterior.setVisible(true);
        });

        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setForeground(new Color(230, 235, 255));
        boton.setBackground(new Color(0, 123, 255));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(260, 60));  // tamaño consistente
        return boton;
    }
}
