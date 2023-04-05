package sistemaReservas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import inventarioHotel.Feature;
import inventarioHotel.Habitacion;
import inventarioHotel.Hotel;
import inventarioHotel.TipoHabitacion;

public class Administrador extends Usuario {
    Hotel hotel; //TODO: 

    public Administrador(String user, String password, Hotel hotel) {
        super(user, password);
        this.hotel = hotel;
    }

    public void agregarHabitacion(Habitacion habitacion, HashMap<String, Habitacion> habitaciones) {
        habitaciones.put(habitacion.getId(), habitacion);
    }

    public void agregarHabitaciones(ArrayList<Habitacion> habitaciones) {
        for (Habitacion h: habitaciones) {
            this.agregarHabitacion(h, this.hotel.getHabitaciones());
        }

    }

    public void cargarHabitaciones(String filePath) {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
		String linea = br.readLine(); // La primera línea del archivo se ignora porque únicamente tiene los títulos de
										// las columnas
		linea = br.readLine();
		while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
		{
			// Separar los valores que estaban en una línea
			String[] partes = linea.split(",");
			String id = partes[0];
			String ubicacion = partes[1];
            TipoHabitacion tipo = TipoHabitacion.valueOf(partes[2].toUpperCase());
            HashMap<Feature, Boolean> features = new HashMap<Feature, Boolean>();
            for (Feature f: Feature.values()) {
                Boolean tieneFeature = Boolean.parseBoolean(partes[3 + f.ordinal()]);
                features.put(f, tieneFeature);
            }
            Habitacion habitacion = new Habitacion(id, ubicacion, tipo, features);
            this.agregarHabitacion(habitacion, this.hotel.getHabitaciones());
            linea = br.readLine();

			// Si el país no existe, lo agrega a la lista de paises
			Pais elPais = paises.get(nombrePais);
			if (elPais == null)
			{
				elPais = new Pais(nombrePais);
				paises.put(nombrePais, elPais);
			}

			// Si no se había encontrado antes a ese atleta, se agrega como un nuevo atleta.
			// Acá suponemos que no hay atletas con el mismo nombre
			Atleta elAtleta = atletas.get(nombreAtleta);
			if (elAtleta == null)
			{
				elAtleta = new Atleta(nombreAtleta, genero, elPais);
				elPais.agregarAtleta(elAtleta);
				atletas.put(nombreAtleta, elAtleta);
			}

			// Si no se había encontrado antes este evento, se agrega como uno nuevo.
			// Los eventos se identifican por su nombre y el año.
			Evento elEvento = buscarEvento(eventos, nombreEvento, anio);
			if (elEvento == null)
			{
				elEvento = new Evento(nombreEvento, anio);
				eventos.add(elEvento);
			}

			// Registra la participación del atleta en el evento, incluyendo el resultado
			// alcanzado (medalla de oro, plata, bronce o ninguna - na).
			Participacion laParticipacion = new Participacion(elAtleta, edad, elEvento, laMedalla);
			elAtleta.agregarParticipacion(laParticipacion);
			elEvento.agregarParticipacion(laParticipacion);

			linea = br.readLine(); // Leer la siguiente línea
		}

		br.close();

		System.out.println(eventos);
		CalculadoraEstadisticas calculadora = new CalculadoraEstadisticas(atletas, paises, eventos);
		return calculadora;
	}

    }

}
