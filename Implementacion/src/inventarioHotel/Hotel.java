package inventarioHotel;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public class Hotel {
    String nombre;
    HashMap<String, Habitacion> habitaciones;
    HashMap<String, Restaurante> restaurantes;
    HashMap<String, Servicio> servicios;
    HashMap<Date, List<Tarifa>> tarifasPorFecha;

    Hotel() {
        habitaciones = new HashMap<>();
        restaurantes = new HashMap<>();
        servicios = new HashMap<>();
        tarifasPorFecha = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(HashMap<String, Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public HashMap<String, Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public void setRestaurantes(HashMap<String, Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    public HashMap<String, Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(HashMap<String, Servicio> servicios) {
        this.servicios = servicios;
    }

    public HashMap<Date, List<Tarifa>> getTarifasPorFecha() {
        return tarifasPorFecha;
    }

    public void setTarifasPorFecha(HashMap<Date, List<Tarifa>> tarifasPorFecha) {
        this.tarifasPorFecha = tarifasPorFecha;
    }
}
