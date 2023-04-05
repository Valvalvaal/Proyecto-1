package inventarioHotel;

import java.sql.Date;

public class Tarifa {
	private Date fecha;
	private int precio;
	private TipoHabitacion tipo;

	public Tarifa(Date fecha, int precio, TipoHabitacion tipo) {
		this.fecha = fecha;
		this.precio = precio;
		this.tipo = tipo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
