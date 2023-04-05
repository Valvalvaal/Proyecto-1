package sistemaReservas;

import java.sql.Date;

public class Reserva {
	private Date fecha;
	private String id;

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
