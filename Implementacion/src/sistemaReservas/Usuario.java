package sistemaReservas;

public class Usuario {
	private String user;
	private String password;
	private TipoUsuario tipoUsuario;

	public Usuario(String user, String password, TipoUsuario tipoUsuario) {
		this.user = user;
		this.password = password;
		this.tipoUsuario = tipoUsuario;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
}
