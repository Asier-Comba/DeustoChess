package gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import bd.Historial;

public class ModeloTablaHistorial extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	private List<Historial> historial;
	private List<String> titulos = Arrays.asList("NOM_JUGADOR", "APELLIDO_JUGADOR", "ID JUGADOR", "GANADAS", "PERDIDAS");
	
	public ModeloTablaHistorial(List<Historial> historial) {
		this.historial = historial;
	}
	
	
	
		@Override
		public int getRowCount() {
			if (historial == null) {
				return 0;
			}
			return historial.size();
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
			Historial p = historial.get(row);
			switch (column) {
			    case 0:
			        return p.getJugadorNom(); // NOM_JUGADOR
			    case 1:
			        return p.getJugadorApell(); // APELLIDO_JUGADOR
			    case 2:
			        return p.getIdJ(); // ID JUGADOR
			    case 3:
			        return p.getGanadas(); // GANADAS
			    case 4:
			        return p.getPerdidas(); // PERDIDAS
			    default:
			        return null;
			}
		
		}
	
	}
