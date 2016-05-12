package daw.videoclubonline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
		// return movies.html as page and the found movies as object
		return new ModelAndView("movies").addObject("movies", movies);
	}

	@RequestMapping("/watch")
	public ModelAndView watch(@RequestParam String nombre) {
		// get movie with passed id
		Movie movie = movieRepository.findByNombre(nombre);
		System.out.println("Movie: " + movie.getNombre());
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

}
