package daw.videoclubonline;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pelicula")
public class Movie {

	@Id
	private String nombre;
	private String url;
	private String descripcion;
	private String ano;
	private String director;
	private String actores;
	private String portada;
	private String valoracion;

	public Movie() {

	}

	public Movie(String nombre, String url, String descripcion, String ano, String director, String actores,
			String portada, String valoracion) {
		super();
		this.nombre = nombre;
		this.url = url;
		this.descripcion = descripcion;
		this.ano = ano;
		this.director = director;
		this.actores = actores;
		this.portada = portada;
		this.valoracion = valoracion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActores() {
		return actores;
	}

	public void setActores(String actores) {
		this.actores = actores;
	}

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	public String getValoracion() {
		return valoracion;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	@Override
	public String toString() {
		return "Movie [nombre=" + nombre + ", url=" + url + ", descripcion=" + descripcion + ", ano=" + ano
				+ ", director=" + director + ", actores=" + actores + ", portada=" + portada + ", valoracion="
				+ valoracion + "]";
	}
	
	

}
