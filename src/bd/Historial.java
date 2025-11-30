package bd;

public class Historial {
	
	private String jugadorNom;
	private String jugadorApell;
	private String idJ;
	private int ganadas;
	private int perdidas;
	public Historial() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Historial(String jugadorNom, String jugadorApell, String idJ, int ganadas, int perdidas) {
		super();
		this.jugadorNom = jugadorNom;
		this.jugadorApell = jugadorApell;
		this.idJ = idJ;
		this.ganadas = ganadas;
		this.perdidas = perdidas;
	}
	public String getJugadorNom() {
		return jugadorNom;
	}
	public void setJugadorNom(String jugadorNom) {
		
		this.jugadorNom = jugadorNom; 
	}
	public String getJugadorApell() {
		return jugadorApell;
	}
	public void setJugadorApell(String jugadorApell) {
		this.jugadorApell = jugadorApell;
	}

	
	public String getIdJ() {
		return idJ;
	}
	public void setIdJ(String idJ) {
		this.idJ = idJ;
	}
	public int getGanadas() {
		return ganadas;
	}
	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}
	public int getPerdidas() {
		return perdidas;
	}
	public void setPerdidas(int perdidas) {
		this.perdidas = perdidas;
	}
	@Override
	public String toString() {
		// ¡CORREGIDO! Añadido ", " antes de "jugadorApell="
		return "Historial [jugadorNom=" + jugadorNom + ", jugadorApell=" + jugadorApell + ", idJ=" + idJ + ", ganadas=" + ganadas + ", perdidas=" + perdidas
				+ "]";
	}
}