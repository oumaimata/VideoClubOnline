package daw.videoclubonline;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
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

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
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

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
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

		// get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// return movies.html as page and the found movies as object
		return new ModelAndView("movies").addObject("movies", movies).addObject("username", username);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping("/watch")
	public ModelAndView watch(@RequestParam String nombre) {
		// get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		// get movie with passed id
		Movie movie = movieRepository.findByNombre(nombre);
		// return watch.html as page and the corresponding movie as object
		return new ModelAndView("watch").addObject("movie", movie).addObject("username", username);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/admin_user")
	public ModelAndView admin_user() {
		// get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		return new ModelAndView("admin_user").addObject("username", username);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/admin_movie")
	public ModelAndView admin_movie() {
		// get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		return new ModelAndView("admin_movie").addObject("username", username);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping("/movie")
	public ModelAndView movie() {
		// get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		return new ModelAndView("movie").addObject("username", username);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping("/profile")
	public ModelAndView profile() {

		// get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// find user with username
		User user = userRepository.findByNombre(username);

		return new ModelAndView("profile").addObject("user", user);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/new_movie")
	public ModelAndView new_movie(@RequestParam String nombre, @RequestParam String url,
			@RequestParam String descripcion, @RequestParam String ano, @RequestParam String director,
			@RequestParam String actores, @RequestParam String portada, @RequestParam String valoracion) {

		// user wants to insert the movie into data base
		Movie movie = new Movie(nombre, url, descripcion, ano, director, actores, portada, valoracion);
		movieRepository.save(movie);

		return new ModelAndView("admin_movie").addObject("addedmovie", movie);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/new_user")
	public ModelAndView new_user(@RequestParam String nombre, @RequestParam String contrasena,
			@RequestParam String correo) {

		// user wants to insert the movie into data base
		String passwordHash = DigestUtils.md5DigestAsHex(contrasena.getBytes());
		User user = new User(nombre, passwordHash, correo);
		userRepository.save(user);

		return new ModelAndView("admin_user").addObject("addeduser", user);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/search_movie_to_modify")
	public ModelAndView search_movie_to_modify(@RequestParam String nombre) {
		// Searching for movie...
		List<Movie> movies = movieRepository.findByNombreContaining(nombre);

		return new ModelAndView("admin_movie").addObject("modify", "").addObject("movies", movies);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/search_user_to_modify")
	public ModelAndView search_user_to_modify(@RequestParam String nombre) {
		// Searching for user...
		List<User> users = userRepository.findByNombreContaining(nombre);

		return new ModelAndView("admin_user").addObject("modify", "").addObject("users", users);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/movie_to_modify")
	public ModelAndView movie_to_modify(@RequestParam String movie_to_modify) {
		// return data of the movie to be modified
		Movie movie = movieRepository.findByNombre(movie_to_modify);

		return new ModelAndView("admin_movie").addObject("modify", "").addObject("movie", movie);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/user_to_modify")
	public ModelAndView user_to_modify(@RequestParam String user_to_modify) {
		// return data of the user to be modified
		User user = userRepository.findByNombre(user_to_modify);

		return new ModelAndView("admin_user").addObject("modify", "").addObject("user", user);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/modify_movie")
	public ModelAndView modify_movie(@RequestParam String nombre, @RequestParam String url,
			@RequestParam String descripcion, String ano, @RequestParam String director, @RequestParam String actores,
			@RequestParam String portada, @RequestParam String valoracion) {
		// write changes of modified movie to db
		Movie movie = new Movie(nombre, url, descripcion, ano, director, actores, portada, valoracion);
		movieRepository.save(movie);

		return new ModelAndView("admin_movie").addObject("modifiedmovie", movie);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/modify_user")
	public ModelAndView modify_user(@RequestParam String nombre, @RequestParam String contrasena,
			@RequestParam String correo) {
		// write changes of modified user to db
		String passwordHash = DigestUtils.md5DigestAsHex(contrasena.getBytes());
		User user = new User(nombre, passwordHash, correo);
		userRepository.save(user);

		return new ModelAndView("admin_user").addObject("modifieduser", user);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/search_movie_to_delete")
	public ModelAndView search_movie_to_delete(@RequestParam String nombre) {
		// Searching for movie...
		List<Movie> movies = movieRepository.findByNombreContaining(nombre);

		return new ModelAndView("admin_movie").addObject("delete", "").addObject("movies", movies);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/search_user_to_delete")
	public ModelAndView search_user_to_delete(@RequestParam String nombre) {
		// Searching for user...
		List<User> users = userRepository.findByNombreContaining(nombre);

		return new ModelAndView("admin_user").addObject("delete", "").addObject("users", users);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/delete_movie")
	public ModelAndView delete_movie(@RequestParam String nombre) {

		movieRepository.deleteByNombre(nombre);

		return new ModelAndView("admin_movie").addObject("deletedmovie", nombre);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/delete_user")
	public ModelAndView delete_user(@RequestParam String nombre) {

		userRepository.deleteByNombre(nombre);

		return new ModelAndView("admin_user").addObject("deleteduser", nombre);
	}

}
