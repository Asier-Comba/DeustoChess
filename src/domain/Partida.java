package domain;

import bd.Jugador;

public class Partida {
private Jugador jugador;
private String id;

private int ganadas;
private int perdidas;
public Partida(Jugador jugador, String id, int ganadas, int perdidas) {
	super();
	this.jugador = jugador;
	this.id = id;
	this.ganadas = ganadas;
	this.perdidas = perdidas;
}
public Jugador getJugador() {
	return jugador;
}
public void setJugador(Jugador jugador) {
	this.jugador = jugador;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
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
	return "Partida [jugador=" + jugador + ", id=" + id + ", ganadas=" + ganadas + ", perdidas=" + perdidas + "]";
}

}
