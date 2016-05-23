package daw.videoclubonline;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Rest-Client for Movie-requests. API "The Open Movie Database":
 * http://www.omdbapi.com/
 * 
 * @author felix
 *
 */
public class ClientREST {

	public ClientREST() {
	}

	/**
	 * Finds information about the desired movie-name. The information is
	 * returned as a MovieREST-Object, which contains all the fields provided by
	 * the omdb API.
	 * 
	 * @param nombre
	 *            the movie name to look for (has to be at least 2 characters
	 *            long)
	 * @return the MovieREST object containing the found information
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public MovieREST getMovieREST(String nombre) throws JsonParseException, JsonMappingException, IOException {
		URL apiURL = new URL("http://www.omdbapi.com/?y=&plot=short&r=json&t=" + nombre.replaceAll(" ", "+"));
		HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
		connection.connect();
		// Configure Jackson parser
		ObjectMapper mapper = new ObjectMapper();
		// Parse response
		MovieREST foundMovie = mapper.readValue(connection.getInputStream(), MovieREST.class);
		return foundMovie;
	}

	/**
	 * Replaces all the empty fields of the <i>movie</i>-object by the
	 * information of the <i>foundMovie</i>-object.
	 * 
	 * @param movie
	 *            the movie to modify
	 * @param foundMovie
	 *            the movie found by the REST-API
	 * @return the changed Movie-object containing the new information
	 */
	public Movie completeMovieInformation(Movie movie, MovieREST foundMovie) {
		if (movie.getDescripcion().equals(""))
			// descripcion set to ""
			movie.setDescripcion(foundMovie.getPlot());
		if (movie.getAno().equals(""))
			// ano set to ""
			movie.setAno(foundMovie.getYear().substring(0, 4));
		if (movie.getDirector().equals(""))
			// director set to ""
			movie.setDirector(foundMovie.getDirector());
		if (movie.getActores().equals(""))
			// actores set to ""
			movie.setActores(foundMovie.getActors());
		if (movie.getPortada().equals(""))
			// portada set to ""
			movie.setPortada(foundMovie.getPoster());
		if (movie.getValoracion().equals(""))
			// valoracion set to ""
			movie.setValoracion(foundMovie.getImdbRating());

		return movie;
	}

}
