package inventariohotel;

public class Servicio {
	private String servicio;
	private int precio;
	private String descripcion;

	public Servicio(String servicio, int precio, String descripcion) {
		this.servicio = servicio;
		this.precio = precio;
		this.descripcion = descripcion;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
