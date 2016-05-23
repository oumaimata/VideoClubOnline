package daw.videoclubonline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Movie-Class to map the responses of the REST-API to a java object.
 * 
 * @author felix
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieREST {

	@JsonProperty("Title")
	private String title;
	@JsonProperty("Year")
	private String year;
	@JsonProperty("Director")
	private String director;
	@JsonProperty("Actors")
	private String actors;
	@JsonProperty("Plot")
	private String plot;
	@JsonProperty("Poster")
	private String poster;
	private String imdbRating;
	@JsonProperty("Response")
	private String response;

	public MovieREST() {
		super();
	}

	public MovieREST(String title, String year, String director, String actors, String plot, String poster,
			String imdbRating, String response) {
		super();
		this.title = title;
		this.year = year;
		this.director = director;
		this.actors = actors;
		this.plot = plot;
		this.poster = poster;
		this.imdbRating = imdbRating;
		this.response = response;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
