package inventarioHotel;

import java.util.ArrayList;

public class Restaurante {
	private String nombreRestaurante;
	private ArrayList<String> menu;

	public Restaurante(String nombreRestaurante, ArrayList<String> menu) {
		this.nombreRestaurante = nombreRestaurante;
		this.menu = menu;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public ArrayList<String> getMenu() {
		return menu;
	}

	public void setMenu(ArrayList<String> menu) {
		this.menu = menu;

	}
}
