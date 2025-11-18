package gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import domain.Partida;


public class ModeloTabla extends DefaultTableModel{
private List<Partida> partidas;
private List<String> titulos = Arrays.asList("JUGADOR", "ID JUGADOR", "GANADAS", "PERDIDAS");
	@Override
	public int getRowCount() {
		if (titulos == null) {
			return 0;
		}
		return partidas.size();
	}

	@Override
	public int getColumnCount() {
		return titulos.size();
	}

	@Override
	public String getColumnName(int column) {
		return titulos.get(column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Partida p = partidas.get(row);
        switch (column) {
        case 0:
            return p.getJugador();
        case 1:
            return p.getId();
        case 2:
            return p.getGanadas();
        case 3:
            return p.getPerdidas();
        default:
            return null;
	}
	}
}
