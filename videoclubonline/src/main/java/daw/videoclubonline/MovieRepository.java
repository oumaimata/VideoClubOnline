package daw.videoclubonline;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, String> {

	Movie findByNombre(String movie);

	List<Movie> findByNombreContaining(String movie);
}
