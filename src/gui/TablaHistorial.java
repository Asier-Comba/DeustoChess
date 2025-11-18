package gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import domain.Partida;


public class TablaHistorial extends JFrame{
    private static final long serialVersionUID = 1L;

    private JTable tablaPersonas;

    public TablaHistorial(List<Partida> partidas) {
        super("Tabla de personas");
        setSize(800, 600);

        ModeloTabla modelo = new ModeloTabla(partidas);
        tablaPersonas = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tablaPersonas);
        add(scrollPane);

        // TODO Tarea 1 - Swing: Añade aquí el código para la visualización
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

