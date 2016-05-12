package daw.videoclubonline;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity(name = "usuario")
public class User {

	@Id
	private String nombre;
	private String contrasena;
	private String correo;

	public User() {

	}

	public User(String nombre, String contrasena, String correo) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Returns the roles of the user depending on his username.
	 * 
	 * @return list of roles
	 */
	public List<GrantedAuthority> getRoles() {
		GrantedAuthority[] roles;

		if (nombre.equals("root"))
			// return ROLE_USER and ROLE_ADMIN for user root
			roles = new GrantedAuthority[] { new SimpleGrantedAuthority("ROLE_USER"),
					new SimpleGrantedAuthority("ROLE_ADMIN") };
		else
			// return ROLE_USER for all other users
			roles = new GrantedAuthority[] { new SimpleGrantedAuthority("ROLE_USER") };

		return Arrays.asList(roles);
	}

}
