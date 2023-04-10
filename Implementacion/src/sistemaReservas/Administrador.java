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
import inventarioHotel.TipoCama;
import inventarioHotel.TipoHabitacion;
import inventarioHotel.Restaurante;

public class Administrador extends Usuario {
    Hotel hotel; // TODO:

    public Administrador(String user, String password, Hotel hotel) {
        super(user, password, TipoUsuario.ADMIN);
        this.hotel = hotel;
    }

    public void agregarHabitacion(Habitacion habitacion) {
        hotel.agregarHabitacion(habitacion);
    }

    public void agregarTarifa(Tarifa tarifa, Date fecha) {
        List<Tarifa> tarifas = hotel.getTarifasPorFecha().get(fecha);
        if (tarifas == null) {
            tarifas = new ArrayList<>();
        }
        tarifas.add(tarifa);
        hotel.agregarListaDeTarifasPorFecha(fecha, tarifas);
    }

    public void agregarTarifaMultiplesFechas(Tarifa tarifa, Date fechaInicio, Date fechaFin) {
        Date fecha = fechaInicio;
        while (fecha.compareTo(fechaFin) <= 0) {
            agregarTarifa(tarifa, fecha);
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
                this.agregarHabitacion(habitacion);
                linea = br.readLine();
            }

            br.close();
        }
    }

    public void cargarCamas(String filePathString) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePathString))) {
            String linea = br.readLine();

            // camas.csv tiene un encabezado que se ignora
            linea = br.readLine();
            while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
            {
                // Separar los valores que estaban en una línea
                String[] partes = linea.split(",");
                String id = partes[0];
                Habitacion habitacion = this.hotel.getHabitacionById(id);
                EnumMap<TipoCama, Integer> camas = habitacion.getCamas();
                for (TipoCama t : TipoCama.values()) {
                    Integer cantidad = Integer.parseInt(partes[1 + t.ordinal()]);
                    camas.put(t, cantidad);
                }
                linea = br.readLine();
            }

            br.close();
        }
    }

    public void cargarMenus(String filePath) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea = br.readLine(); // La primera línea del archivo se ignora porque únicamente tiene los títulos
                                          // de
                                          // las columnas
            linea = br.readLine();
            while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
            {
                // Separar los valores que estaban en una línea
                String[] partes = linea.split(",");
                String nombre = partes[0];
                Restaurante restaurante = hotel.getRestauranteByName(nombre);
                if (restaurante == null) {
                    restaurante = new Restaurante(nombre);
                    hotel.addRestaurante(restaurante);
                }
                String plato = partes[1];
                Integer precio = Integer.parseInt(partes[2]);
                restaurante.addPlato(plato, precio);
                linea = br.readLine();
            }

            br.close();
        }
    }

}
