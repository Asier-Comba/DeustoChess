package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bd.ConexionBD;



public class TablaHistorial extends JFrame{
    private static final long serialVersionUID = 1L;

    private ModeloTablaHistorial modeloHistorial;
	private JTable tablaHistorial;
	private JScrollPane scrollHistorial;
	
	private JPanel pSur, pCentro;
	
	private JFrame ventanaAnterior;
	 
	private ConexionBD bd;
	
	public TablaHistorial(JFrame va, ConexionBD bd) {
		this.ventanaAnterior = va;
		
		setBounds(300, 200, 600, 500);
		this.bd = bd;
		
		pCentro = new JPanel();
		pSur = new JPanel();
		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pCentro, BorderLayout.CENTER);

		// === CREACIÓN DE BOTONES ===
		
		JButton btnVolverMenu = new JButton("VOLVER");
		JButton btnSalir = new JButton("SALIR");
		JButton btnCerrarSesion = new JButton("CERRAR SESIÓN");
		
		pSur.add(btnVolverMenu);
		pSur.add(btnSalir);
		pSur.add(btnCerrarSesion);
		
	
		// === CREACIÓN DEL MODELO CON LA BD ===
		
		modeloHistorial = new ModeloTablaHistorial(bd.obtenerJugador());
		tablaHistorial = new JTable(modeloHistorial);
		scrollHistorial = new JScrollPane(tablaHistorial);
		pCentro.add(scrollHistorial);
		
		
		// === AÑADIR LOS ACTIONLISTENERS DE LOS BOTONES ===
		
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
	            
	            new VentanaInicioSesion(bd); 
	        });

	        setLocationRelativeTo(null); 
	        addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	                // Ejecutar la lógica de cierre de la base de datos
	                bd.closeBD();
	                
	                // Finalizar la aplicación
	                System.exit(0);
	            }
	        });
	
		setVisible(true);
	}
}

