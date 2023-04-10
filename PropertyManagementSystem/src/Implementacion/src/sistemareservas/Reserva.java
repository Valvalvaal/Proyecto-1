package sistemareservas;

import java.util.Date;
import java.util.ArrayList;

public class Reserva {
	private Date fecha;
	private String id;
	private ArrayList<Huesped> huespedes;

	public ArrayList<Huesped> getHuespedes() {
		return huespedes;
	}

	public void setHuespedes(ArrayList<Huesped> huespedes) {
		this.huespedes = huespedes;
	}

	public Reserva(Date fecha, String id) {
		this.fecha = fecha;
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
