package inventarioHotel;

public class Habitacion {
	private String id;
	private String ubicacion;
	private TipoHabitacion tipo;
	private Feature features;

	public Habitacion(String id, String ubicacion, TipoHabitacion tipo, Feature features) {
		this.id = id;
		this.ubicacion = ubicacion;
		this.tipo = tipo;
		this.features = features;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public TipoHabitacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoHabitacion tipo) {
		this.tipo = tipo;
	}

	public Feature getFeatures() {
		return features;
	}

	public void setFeatures(Feature features) {
		this.features = features;
	}

}
