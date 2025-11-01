package gui;

import javax.swing.JFrame;

public class PanelTablero extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public PanelTablero() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DeustoChess");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
}
