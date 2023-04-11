package inventariohotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;

import sistemareservas.Huesped;
import sistemareservas.Reserva;

public class Habitacion {
	private String id;
	private String ubicacion;
	private TipoHabitacion tipo;
	private EnumMap<Feature, Boolean> features;
	private HashMap<Date, Reserva> reservas;
	private EnumMap<TipoCama, Integer> camas;

	public EnumMap<TipoCama, Integer> getCamas() {
		return camas;
	}

	public void setCamas(EnumMap<TipoCama, Integer> camas) {
		this.camas = camas;
	}

	public Habitacion(String id, String ubicacion, TipoHabitacion tipo, EnumMap<Feature, Boolean> features) {
		this.id = id;
		this.ubicacion = ubicacion;
		this.tipo = tipo;
		this.features = features;
		this.camas = new EnumMap<>(TipoCama.class);
		this.reservas = new HashMap<>();
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

	public EnumMap<Feature, Boolean> getFeatures() {
		return features;
	}

	public void setFeatures(EnumMap<Feature, Boolean> features) {
		this.features = features;
	}

	public ArrayList<Huesped> getHuespedes(Date fecha) {
		Reserva reserva = reservas.get(fecha);
		if (reserva != null) {
			return reserva.getHuespedes();
		}
		return new ArrayList<>();
	}

	public int getCapacidadAdultos() {
		return camas.get(TipoCama.SENCILLA) + camas.get(TipoCama.DOBLE) * 2 + camas.get(TipoCama.QUEEN) * 2
				+ camas.get(TipoCama.KING) * 2;
	}

	public int getCapacidadNinos() {
		return camas.get(TipoCama.KID);
	}

	@Override
	public String toString() {
		return "Habitación #: " + id + "\nUbicación: " + ubicacion + "\nTipo: " + tipo + "\nFeatures: " + features;
	}

}
