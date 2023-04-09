package inventarioHotel;

public class Cama {
	private int capacidad;
	private TipoCama tipo;

	public Cama(int capacidad, TipoCama tipo) {
		this.capacidad = capacidad;
		this.tipo = tipo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public TipoCama getTipo() {
		return tipo;
	}

	public void setTipo(TipoCama tipo) {
		this.tipo = tipo;
	}

}
