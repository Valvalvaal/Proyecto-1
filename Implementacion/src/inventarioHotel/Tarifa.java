package inventarioHotel;

import java.sql.Date;

public class Tarifa {
	private int precio;
	private TipoHabitacion tipo;

	public Tarifa(int precio, TipoHabitacion tipo) {
		this.precio = precio;
		this.tipo = tipo;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public TipoHabitacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoHabitacion tipo) {
		this.tipo = tipo;
	}
}
