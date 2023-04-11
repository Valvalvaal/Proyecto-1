package inventariohotel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import utils.DateUtils;

public class Hotel {
    String nombre;
    HashMap<String, Habitacion> habitaciones;
    HashMap<String, Restaurante> restaurantes;
    HashMap<String, Servicio> servicios;
    HashMap<Date, List<Tarifa>> tarifasPorFecha;

    public Hotel() {
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

    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.put(habitacion.getId(), habitacion);
    }

    public Habitacion getHabitacionById(String id) {
        return habitaciones.get(id);
    }

    public void setHabitaciones(HashMap<String, Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public HashMap<String, Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public Restaurante getRestauranteByName(String nombre) {
        return restaurantes.get(nombre);
    }

    public void setRestaurantes(HashMap<String, Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    public void addRestaurante(Restaurante restaurante) {
        restaurantes.put(restaurante.getNombre(), restaurante);
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

    public void agregarListaDeTarifasPorFecha(Date fecha, List<Tarifa> tarifas) {
        tarifasPorFecha.put(fecha, tarifas);
    }

    public void agregarTarifa(Tarifa nuevaTarifa, Date fecha) {
        List<Tarifa> tarifas = this.tarifasPorFecha.get(fecha);
        if (tarifas == null) {
            tarifas = new ArrayList<>();
        }
        // Si ya existe una tarifa para el mismo tipo de habitación, se deja la de menor
        // valor
        boolean reemplazada = false;
        for (int i = 0; i < tarifas.size() && !reemplazada; i++) {
            Tarifa tarifaExistente = tarifas.get(i);
            boolean mismoTipoDeTarifas = tarifaExistente.getTipo() == nuevaTarifa.getTipo();
            boolean mejorPrecioNuevaTarifa = tarifaExistente.getPrecio() > nuevaTarifa.getPrecio();
            if (mismoTipoDeTarifas && mejorPrecioNuevaTarifa) {
                tarifas.set(i, nuevaTarifa);
                reemplazada = true;
            }
        }
        if (!reemplazada) {
            tarifas.add(nuevaTarifa);
        }
        this.tarifasPorFecha.put(fecha, tarifas);
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
     * @throws IOException
     */
    public void cargarHabitaciones(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea = br.readLine(); // Ignorar el encabezado del archivo
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarCamas(String filePathString) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePathString))) {
            String linea = br.readLine();

            // camas.csv tiene un encabezado que se ignora
            linea = br.readLine();
            while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
            {
                // Separar los valores que estaban en una línea
                String[] partes = linea.split(",");
                String id = partes[0];
                Habitacion habitacion = this.getHabitacionById(id);
                EnumMap<TipoCama, Integer> camas = habitacion.getCamas();
                for (TipoCama t : TipoCama.values()) {
                    Integer cantidad = Integer.parseInt(partes[1 + t.ordinal()]);
                    camas.put(t, cantidad);
                }
                linea = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarMenus(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea = br.readLine(); // La primera línea del archivo se ignora porque únicamente tiene los títulos
                                          // de
                                          // las columnas
            linea = br.readLine();
            while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
            {
                // Separar los valores que estaban en una línea
                String[] partes = linea.split(",");
                String nombreRestaurante = partes[0];
                Restaurante restaurante = this.getRestauranteByName(nombreRestaurante);
                if (restaurante == null) {
                    restaurante = new Restaurante(nombreRestaurante);
                    this.addRestaurante(restaurante);
                }
                String plato = partes[1];
                Integer precio = Integer.parseInt(partes[2]);
                restaurante.addPlato(plato, precio);
                linea = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarTarifas(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea = br.readLine();
            linea = br.readLine();
            while (linea != null) {
                // Separar los valores que estaban en una línea
                String[] partes = linea.split(",");
                Date fechaInicial = DateUtils.getDate(partes[0]);
                Date fechaFinal = DateUtils.getDate(partes[1]);
                TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(partes[2]);
                Integer valorTarifaPorNoche = Integer.parseInt(partes[3]);
                Tarifa tarifa = new Tarifa(valorTarifaPorNoche, tipoHabitacion);
                this.agregarTarifaMultiplesFechas(tarifa, fechaInicial, fechaFinal);
                linea = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
