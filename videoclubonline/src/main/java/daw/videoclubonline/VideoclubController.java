package daw.videoclubonline;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class VideoclubController {

	@Autowired
	private MovieRepository movieRepository; // used for movie-database access
	@Autowired
	private UserRepository userRepository; // used for user-database access

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("/")
	public ModelAndView home() {
		// get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// find user with username
		User user = userRepository.findByNombre(username);
		// return index.html as page and the user object
		return new ModelAndView("index").addObject("user", user);
	}

	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String nombre) {
		List<Movie> movies = movieRepository.findByNombreContaining(nombre);

		// GET MISSING INFORMATION OF MOVIES
		ClientREST restClient = new ClientREST();

		for (Movie m : movies) {
			try {
				restClient.completeMovieInformation(m, restClient.getMovieREST(m.getNombre()));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Store changes in db
		movieRepository.save(movies);

		// return movies.html as page and the found movies as object
		return new ModelAndView("movies").addObject("movies", movies);
	}

	@RequestMapping("/watch")
	public ModelAndView watch(@RequestParam String nombre) {
		// get movie with passed id
		Movie movie = movieRepository.findByNombre(nombre);
		// return watch.html as page and the corresponding movie as object
		return new ModelAndView("watch").addObject("movie", movie);
	}

	@RequestMapping("/admin_user")
	public ModelAndView admun_user() {
		// TODO
		return new ModelAndView("admin_user");
	}

	@RequestMapping("/admin_movie")
	public ModelAndView admun_movie() {
		// TODO
		return new ModelAndView("admin_movie");
	}

	@RequestMapping("/movie")
	public ModelAndView movie() {
		// TODO
		return new ModelAndView("movie");
	}

	@RequestMapping("/new_movie")
	public ModelAndView new_movie(@RequestParam String nombre, @RequestParam String url,
			@RequestParam String descripcion, @RequestParam String ano, @RequestParam String director,
			@RequestParam String actores, @RequestParam String portada, @RequestParam String valoracion) {

		// user wants to insert the movie into data base
		Movie movie = new Movie(nombre, url, descripcion, ano, director, actores, portada, valoracion);
		movieRepository.save(movie);

		return new ModelAndView("admin_movie").addObject("addedmovie", movie);
	}

	@RequestMapping("/search_movie_to_modify")
	public ModelAndView search_movie_to_modify(@RequestParam String nombre) {
		// Searching for movie...
		List<Movie> movies = movieRepository.findByNombreContaining(nombre);

		return new ModelAndView("admin_movie").addObject("modify", "").addObject("movies", movies);
	}

	@RequestMapping("/movie_to_modify")
	public ModelAndView movie_to_modify(@RequestParam String movie_to_modify) {
		// return data of the movie to be modified
		Movie movie = movieRepository.findByNombre(movie_to_modify);

		return new ModelAndView("admin_movie").addObject("modify", "").addObject("movie", movie);
	}

	@RequestMapping("/modify_movie")
	public ModelAndView modify_movie(@RequestParam String nombre, @RequestParam String url,
			@RequestParam String descripcion, String ano, @RequestParam String director, @RequestParam String actores,
			@RequestParam String portada, @RequestParam String valoracion) {
		// write changes of modified movie to db
		Movie movie = new Movie(nombre, url, descripcion, ano, director, actores, portada, valoracion);
		movieRepository.save(movie);

		return new ModelAndView("admin_movie").addObject("modifiedmovie", movie);
	}

	@RequestMapping("/search_movie_to_delete")
	public ModelAndView search_movie_to_delete(@RequestParam String nombre) {
		// Searching for movie...
		List<Movie> movies = movieRepository.findByNombreContaining(nombre);

		return new ModelAndView("admin_movie").addObject("delete", "").addObject("movies", movies);
	}

	@RequestMapping("/delete_movie")
	public ModelAndView delete_movie(@RequestParam String nombre) {

		movieRepository.deleteByNombre(nombre);

		return new ModelAndView("admin_movie").addObject("deletedmovie", nombre);
	}

}
