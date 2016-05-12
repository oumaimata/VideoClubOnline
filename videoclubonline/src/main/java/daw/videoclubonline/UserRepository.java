package daw.videoclubonline;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

	User findByNombre(String user);

}