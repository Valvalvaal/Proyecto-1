package consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import inventariohotel.Habitacion;
import inventariohotel.Hotel;
import inventariohotel.Restaurante;
import inventariohotel.Servicio;
import inventariohotel.Tarifa;
import inventariohotel.TipoHabitacion;
import sistemareservas.Huesped;
import sistemareservas.TipoUsuario;
import sistemareservas.Usuario;
import utils.DateUtils;

public class ConsolaHotel {
    public static final String BASE_DATA_PATH = "PropertyManagementSystem" + File.separator + "data" + File.separator;
    public static final String SELECCIONE_UNA_OPCION = "Por favor seleccione una opción";
    /**
     * Este es el hotel que se usará para hacer todas las
     * operaciones de la aplicación.
     */
    private Hotel hotel = new Hotel();
    private Usuario usuario;
    private HashMap<String, Usuario> usuarios = new HashMap<>();

    /**
     * Ejecuta la aplicación: le muestra el menú al usuario y la pide que ingrese
     * una opción, y ejecuta la opción seleccionada por el usuario. Este proceso se
     * repite hasta que el usuario seleccione la opción de abandonar la aplicación.
     */
    public void ejecutarAplicacion() throws IOException {
        cargarUsuarios();
        System.out.println("Programa de hotelería\n");
        boolean continuar = true;
        while (continuar) {
            if (usuario == null) {
                continuar = mostrarYEjecutarFlujoInicial();
            } else {
                mostrarMenu();
            }

        }
    }

    public boolean mostrarYEjecutarFlujoInicial() {
        System.out.println("Selecciona una opción:");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Cerrar programa");
        int opcionSeleccionada = Integer.parseInt(input(SELECCIONE_UNA_OPCION));
        if (opcionSeleccionada == 1) {
            iniciarSesion();
        }
        return opcionSeleccionada == 1;
    }

    public void iniciarSesion() {
        String username = input("Ingrese su username");
        String password = input("Ingrese su contraseña");

        Usuario usuarioBuscado = usuarios.get(username);
        if (usuarioBuscado.getPassword().equals(password)) {
            usuario = usuarioBuscado;
        }

    }

    public void cargarUsuarios() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_DATA_PATH + "usuarios.csv"))) {
            String linea = br.readLine();

            linea = br.readLine();
            while (linea != null) {
                // Separar los valores que estaban en una línea
                String[] partes = linea.split(",");
                String username = partes[0];
                String password = partes[1];
                TipoUsuario tipoUsuario = TipoUsuario.valueOf(partes[2].toUpperCase());
                usuarios.put(username, new Usuario(username, password, tipoUsuario));
                linea = br.readLine();
            }
        }
    }

    public void mostrarMenu() {
        if (usuario.getTipoUsuario().equals(TipoUsuario.ADMIN)) {

            mostrarMenuAdmin();
        } else if (usuario.getTipoUsuario().equals(TipoUsuario.EMPLEADO)) {
            mostrarMenuEmpleado();
        } else {
            mostrarMenuRecepcionista();
        }
    }

    public void mostrarMenuAdmin() {
        System.out.println("\nBienvenido al menú de administrador\n");
        System.out.println("0. Cargar hotel");
        System.out.println("1. Agregar habitación");
        System.out.println("2. Cargar csv de habitaciones");
        System.out.println("3. Cargar tarifas");
        System.out.println("4. Agregar/Modificar tarifas de habitaciones");
        System.out.println("5. Cargar menus de los restaurantes");
        System.out.println("6. Modificar tarifas de servicios");
        System.out.println("7. Cerrar sesión");
        int opcionSeleccionada = Integer.parseInt(input(SELECCIONE_UNA_OPCION));

        ejecutarFuncionAdmin(opcionSeleccionada);
    }

    public void ejecutarFuncionAdmin(int opcion) {
        if (opcion == 0)
            try {
                cargarHotel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else if (opcion == 1) {
            // agregarHabitacion();
        } else if (opcion == 2) {
            String path = input("Ingrese el path del archivo csv de habitaciones que desea cargar: ");
            try {
                cargarHabitaciones(path);
            } catch (IOException e) {
                System.out.println("Ingresó un nombre de archivo inválido, inténtelo de nuevo");
                e.printStackTrace();
            }
        } else if (opcion == 3)
            try {
                cargarTarifas();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        else if (opcion == 4) {
            TipoHabitacion tipo = TipoHabitacion.valueOf(
                    input("Ingrese el tipo de habitación para el cual desea modificar las tarifas: ").toUpperCase());
            agregarTarifa(tipo);
        } else if (opcion == 5) {
            String path = input(
                    "Ingrese el path del archivo csv de menus que desea cargar." +
                            "Recuerde que sobreescribirá los datos de los menus de restaurantes actuales con los datos de su archivo.");
            try {
                cargarMenus(path);
            } catch (IOException e) {
                System.out.println("Ingresó un nombre de archivo inválido, inténtelo de nuevo");
                e.printStackTrace();
            }
        } else if (opcion == 6) {
            // modificarTarifasServicios();
        } else if (opcion == 7)
            usuario = null;
    }

    private void agregarTarifa(TipoHabitacion tipoHabitacion) {
        System.out.println("Ingrese las fechas en formato (dd/mm/yyyy) ej: 01/01/2020");
        try {
            Date fechaInicio = DateUtils.getDate(input("Fecha de inicio de la tarifa: "));
            Date fechaFin = DateUtils.getDate(input("Fecha de fin de la tarifa: "));
            Integer precio = Integer.parseInt(input("Ingrese el precio: "));
            Tarifa tarifa = new Tarifa(precio, tipoHabitacion);
            hotel.agregarTarifaMultiplesFechas(tarifa, fechaInicio, fechaFin);
        } catch (ParseException e) {
            System.out.println("Ingresó una fecha inválida, inténtelo de nuevo");
            // agregarTarifa(tipoHabitacion); (Para repetir)
            e.printStackTrace();
        }
    }

    private void cargarTarifas() throws IOException {
        try {
            hotel.cargarTarifas(BASE_DATA_PATH + "tarifas.csv");
        } catch (ParseException e) {
            System.out.println(
                    "Su archivo tiene información inválida, posiblemente una fecha. Arreglelo e intentelo de nuevo");
            e.printStackTrace();
        }
    }

    private void cargarMenus(String filePath) throws IOException {
        hotel.cargarMenus(filePath);
    }

    private void cargarHabitaciones(String filePath) throws IOException {
        hotel.cargarHabitaciones(filePath);
    }

    private void cargarHotel() throws IOException {
        hotel.cargarHabitaciones(BASE_DATA_PATH + "habitaciones.csv");
        hotel.cargarCamas(BASE_DATA_PATH + "camas.csv");
        hotel.cargarMenus(BASE_DATA_PATH + "menus.csv");
        hotel.cargarServicios(BASE_DATA_PATH + "servicios.csv");
    }

    public void mostrarMenuRecepcionista() {
        System.out.println("\nBienvenido al menú de recepcionista\n");
        System.out.println("1. Crear reserva");
        System.out.println("2. Cancelar reserva");
        System.out.println("3. Cerrar sesión");
        int opcionSeleccionada = Integer.parseInt(input(SELECCIONE_UNA_OPCION));
        ejecutarFuncionRecepcionista(opcionSeleccionada);
    }

    private void ejecutarFuncionRecepcionista(int opcion) {
        if (opcion == 1)
            crearReserva();
        if (opcion == 2)
            cancelarReserva();
        if (opcion == 3)
            usuario = null;
    }

    private void crearReserva() {

    }

    private void cancelarReserva() {

    }

    public void mostrarMenuEmpleado() {
        System.out.println("\nBienvenido al menú de empleado\n");
        System.out.println("1. Consultar información de una habitación");
        System.out.println("2. Consultar información de un restaurante");
        System.out.println("3. Listar servicios");
        System.out.println("4. Consultar reserva");
        System.out.println("5. Registrar consumo");
        System.out.println("6. Registrar entrada de huésped");
        System.out.println("7. Registrar salida de huésped");
        System.out.println("8. Generar factura");
        System.out.println("9. Cerrar sesión");

        int opcionSeleccionada = Integer.parseInt(input(SELECCIONE_UNA_OPCION));
        ejecutarFuncionEmpleado(opcionSeleccionada);
    }

    public void ejecutarFuncionEmpleado(int opcion) {
        if (opcion == 1) {
            consultarHabitacion();
        }
        if (opcion == 2)
            consultarMenuRestaurante();
        if (opcion == 3) {
            listarServicios();
        }
        if (opcion == 4) {

        }
        // ejecutarConsultarReserva();
        // if (opcion == 5)
        // ejecutarRegistrarConsumo();
        // if (opcion == 6)
        // ejecutarRegistrarEntrada();
        // if (opcion == 7)
        // ejecutarRegistrarSalida();
        // if (opcion == 8)
        // ejecutarGenerarFactura();
        if (opcion == 9)
            usuario = null;
    }

    private void listarServicios() {
        System.out.println("Servicios disponibles en el hotel:");
        for (String nombreServicio : hotel.getServicios().keySet()) {
            Servicio servicio = hotel.getServicio(nombreServicio);
            System.out.println(servicio.toString());
        }
    }

    private void consultarMenuRestaurante() {
        String nombreRestaurante = input("Ingrese el nombre del restaurante a consultar");
        Restaurante restaurante = hotel.getRestauranteByName(nombreRestaurante);
        if (restaurante == null) {
            System.out.println("No se encontró el restaurante");
            return;
        }
        HashMap<String, Integer> menus = restaurante.getMenu();
        System.out.println("Oferta del restaurante " + nombreRestaurante);
        for (String producto : menus.keySet()) {
            System.out.println("- " + producto + ": " + menus.get(producto));
        }
    }

    private void consultarHabitacion() {
        String habitacionId = input("Ingrese el ID de la habitación a consultar");
        Habitacion habitacion = hotel.getHabitacionById(habitacionId);

        if (habitacion == null) {
            System.out.println("No se encontró la habitación");
        } else {
            String fechaConsulta = input(
                    "Si desea consultar la disponibilidad de la habitación para una fecha en particular, ingrese la fecha en formato dd/mm/aaaa. Si no, presione enter");
            System.out.println(habitacion.toString());
            try {
                Date fecha = DateUtils.getDate(fechaConsulta);
                ArrayList<Huesped> huespedes = habitacion.getHuespedes(fecha);
                if (huespedes.isEmpty()) {
                    System.out.println("La habitación está disponible en esta fecha");
                } else {
                    System.out.println("La habitación está ocupada por los siguientes huéspedes:");
                    for (Huesped huesped : huespedes) {
                        System.out.println(huesped.toString());
                    }
                }
            } catch (ParseException e) {
                if (!fechaConsulta.isEmpty())
                    System.out.println("La fecha ingresada no es válida.");
            }
        }
    }

    /**
     * Este método sirve para imprimir un mensaje en la consola pidiéndole
     * información al usuario y luego leer lo que escriba el usuario.
     * 
     * @param mensaje El mensaje que se le mostrará al usuario
     * @return La cadena de caracteres que el usuario escriba como respuesta.
     */
    public String input(String mensaje) {
        try {
            System.out.print(mensaje + ": ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error leyendo de la consola");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Este es el método principal de la aplicación, con el que inicia la ejecución
     * de la aplicación
     * 
     * @param args Parámetros introducidos en la línea de comandos al invocar la
     *             aplicación
     */
    public static void main(String[] args) throws IOException {
        ConsolaHotel consola = new ConsolaHotel();
        consola.ejecutarAplicacion();
    }
}
