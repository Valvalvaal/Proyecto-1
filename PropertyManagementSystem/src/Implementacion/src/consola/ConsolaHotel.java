package consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
import sistemareservas.Administrador;
import sistemareservas.Huesped;
import sistemareservas.TipoUsuario;
import sistemareservas.Usuario;
import utils.DateUtils;

public class ConsolaHotel {
    public static final String USERS_FILE_PATH = "PropertyManagementSystem" + File.separator + "data" + File.separator
            + "usuarios.csv";
    /**
     * Esta es el hotel que se usará para hacer todas las
     * operaciones de la aplicación. Esta calculadora también contiene toda la
     * información sobre los atletas después de que se cargue desde un archivo.
     */
    private Hotel hotel;
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
        int opcionSeleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
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
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
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

            br.close();
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
        System.out.println("0. Cargar hotel");
        System.out.println("1. Agregar habitación");
        System.out.println("2. Cargar csv de habitaciones");
        System.out.println("3. Cargar tarifas");
        System.out.println("4. Modificar tarifas de servicios");
        System.out.println("5. Cargar menus de los restaurantes");
        System.out.println("6. Cerrar sesión");
        int opcionSeleccionada = Integer.parseInt(input("Por favor seleccione una opción"));

        ejecutarFuncionAdmin(opcionSeleccionada);
    }

    public void ejecutarFuncionAdmin(int opcion) {
        this.hotel = new Hotel();
        Administrador admin = new Administrador(usuario.getUser(), usuario.getPassword(), this.hotel);
        if (opcion == 0)
            try {
                cargarHotel(admin);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        // if (opcion == 2)
        // ejecutarCargarHabitaciones();
        // if (opcion == 3)
        // ejecutarCargarTarifas();
        // if (opcion == 4)
        // ejecutarModificarTarifas();
        // if (opcion == 5)
        // ejecutarCargarMenus();
        if (opcion == 6)
            usuario = null;
    }

    private void cargarHotel(Administrador admin) throws IOException {
        admin.cargarHabitaciones(
                "PropertyManagementSystem" + File.separator + "data" + File.separator + "habitaciones.csv");
        admin.cargarCamas(
                "PropertyManagementSystem" + File.separator + "data" + File.separator + "camas.csv");
        admin.cargarMenus(
                "PropertyManagementSystem" + File.separator + "data" + File.separator + "menus.csv");
    }

    public void mostrarMenuRecepcionista() {
        System.out.println("1. Crear reserva");
        System.out.println("2. Cancelar reserva");
        System.out.println("3. Cerrar sesión");
        int opcionSeleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
        ejecutarFuncionRecepcionista(opcionSeleccionada);
    }

    private void ejecutarFuncionRecepcionista(int opcion) {
        // if (opcion == 1)
        // crearReserva();
        // if (opcion == 2)
        // cancelarReserva();
        if (opcion == 3)
            usuario = null;
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
        System.out.println("9. Cancelar reserva");
        System.out.println("10. Cerrar sesión");

        int opcionSeleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
        ejecutarFuncionEmpleado(opcionSeleccionada);
    }

    public void ejecutarFuncionEmpleado(int opcion) {
        if (opcion == 1) {
            System.out.println("Habitaciones del hotel: ");
            for (Habitacion habitacion : hotel.getHabitaciones().values())
                System.out.println(habitacion.toString());
            consultarHabitacion();
        }

        if (opcion == 2)
            consultarMenuRestaurante();
        // if (opcion == 3)
        // ejecutarListarServicios();
        // if (opcion == 4)
        // ejecutarConsultarReserva();
        // if (opcion == 5)
        // ejecutarCrearReserva();
        // if (opcion == 6)
        // ejecutarRegistrarConsumo();
        // if (opcion == 7)
        // ejecutarRegistrarEntrada();
        // if (opcion == 8)
        // ejecutarRegistrarSalida();
        // if (opcion == 9)
        // ejecutarGenerarFactura();
        // if (opcion == 10)
        // ejecutarCancelarReserva();
        if (opcion == 11)
            usuario = null;
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
                if (huespedes.size() == 0) {
                    System.out.println("La habitación está disponible en esta fecha");
                } else {
                    System.out.println("La habitación está ocupada por los siguientes huéspedes:");
                    for (Huesped huesped : huespedes) {
                        System.out.println(huesped.toString());
                    }
                }
            } catch (ParseException e) {
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
