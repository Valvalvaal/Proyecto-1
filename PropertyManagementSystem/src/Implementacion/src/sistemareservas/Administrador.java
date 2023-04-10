package sistemareservas;

public class Administrador extends Usuario {

    public Administrador(String user, String password) {
        super(user, password, TipoUsuario.ADMIN);
    }

}
