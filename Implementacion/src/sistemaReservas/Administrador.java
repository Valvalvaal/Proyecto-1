package sistemaReservas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import inventarioHotel.Feature;
import inventarioHotel.Habitacion;
import inventarioHotel.Hotel;
import inventarioHotel.Tarifa;
import inventarioHotel.TipoHabitacion;

public class Administrador extends Usuario {
    Hotel hotel; // TODO:

    public Administrador(String user, String password, Hotel hotel) {
        super(user, password);
        this.hotel = hotel;
    }

    public void agregarHabitacion(Habitacion habitacion, HashMap<String, Habitacion> habitaciones) {
        habitaciones.put(habitacion.getId(), habitacion);
    }

    public void agregarTarifa(Tarifa tarifa, Date fecha, HashMap<Date, List<Tarifa>> tarifasPorFecha) {
        List<Tarifa> tarifas = tarifasPorFecha.get(fecha);
        if (tarifas == null) {
            tarifas = new ArrayList<>();
            tarifas.add(tarifa);
            tarifasPorFecha.put(fecha, tarifas);
        } else {
            tarifas.add(tarifa);
        }
    }

    public void agregarTarifaMultiplesFechas(Tarifa tarifa, Date fechaInicio, Date fechaFin,
            HashMap<Date, List<Tarifa>> tarifasPorFecha) {
        Date fecha = fechaInicio;
        while (fecha.compareTo(fechaFin) <= 0) {
            agregarTarifa(tarifa, fecha, tarifasPorFecha);
            fecha = new Date(fecha.getTime() + 86400000); // 86400000 ms = 1 día
        }
    }

    /**
     * @param filePath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void cargarHabitaciones(String filePath) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea = br.readLine(); // La primera línea del archivo se ignora porque únicamente tiene los títulos
                                          // de
                                          // las columnas
            linea = br.readLine();
            while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
            {
                // Separar los valores que estaban en una línea
                String[] partes = linea.split(",");
                String id = partes[0];
                String ubicacion = partes[1];
                TipoHabitacion tipo = TipoHabitacion.valueOf(partes[2].toUpperCase());
                EnumMap<Feature, Boolean> features = new EnumMap<>(Feature.class);
                for (Feature f : Feature.values()) {
                    Boolean tieneFeature = Boolean.parseBoolean(partes[3 + f.ordinal()]);
                    features.put(f, tieneFeature);
                }
                Habitacion habitacion = new Habitacion(id, ubicacion, tipo, features);
                this.agregarHabitacion(habitacion, this.hotel.getHabitaciones());
                linea = br.readLine();
            }

            br.close();
        }
    }

}
