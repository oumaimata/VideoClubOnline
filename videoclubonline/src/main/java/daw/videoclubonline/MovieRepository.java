package daw.videoclubonline;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MovieRepository extends CrudRepository<Movie, String> {

	/**
	 * Select a movie by its primary key 'nombre'
	 * 
	 * @param movie
	 *            the name of the movie
	 * @return the found movie as an object
	 */
	Movie findByNombre(String movie);

	/**
	 * Find movies containing the searched text in their name
	 * 
	 * @param movie
	 *            the movie name to search
	 * @return a list of movies that match the searched String
	 */
	List<Movie> findByNombreContaining(String movie);
	
	List<Movie> findByNombreOrderByValoracionDesc();

	/**
	 * To insert and modify a movie
	 */
	@Override
	@Transactional
	<S extends Movie> S save(S movie);

	/**
	 * To insert a set of movies
	 */
	@Override
	@Transactional
	<S extends Movie> Iterable<S> save(Iterable<S> movies);

	/**
	 * Deletes the movie from the database
	 * 
	 * @param movie
	 *            the name of the movie to delete
	 */
	@Modifying
	@Transactional
	void deleteByNombre(String movie);

}
