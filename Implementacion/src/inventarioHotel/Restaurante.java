package inventarioHotel;

import java.util.HashMap;

public class Restaurante {
	private String nombre;
	private HashMap<String, Integer> menu;

	public Restaurante(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public HashMap<String, Integer> getMenu() {
		return menu;
	}

	public void setMenu(HashMap<String, Integer> menu) {
		this.menu = menu;

	}

	public void addPlato(String nombrePlato, Integer precio) {
		menu.put(nombrePlato, precio);
	}
}
