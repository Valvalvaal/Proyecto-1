package sistemaReservas;

public class Huesped {
	private String nombre;
	private String documento;
	private String correo;
	private String celular;

	public Huesped(String nombre, String documento, String correo, String celular) {
		this.nombre = nombre;
		this.documento = documento;
		this.correo = correo;
		this.celular = celular;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
}
