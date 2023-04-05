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
}
