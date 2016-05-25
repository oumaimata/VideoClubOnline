package daw.videoclubonline;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, String> {

	/**
	 * Select a user by its primary key 'nombre'
	 * 
	 * @param user
	 *            the name of the user
	 * @return the found user as an object
	 */
	User findByNombre(String user);
	
	/**
	 * Find users containing the searched text in their name
	 * 
	 * @param user
	 *            the user name to search
	 * @return a list of users that match the searched String
	 */
	
	List<User> findByNombreContaining(String user);
	
	/**
	 * To insert and modify a user
	 */
	
	@Override
	@Transactional
	<S extends User> S save(S user);
	
	/**
	 * To insert a set of users
	 */
	
	@Override
	@Transactional
	<S extends User> Iterable<S> save(Iterable<S> user);
	
	/**
	 * Deletes the user from the database
	 * 
	 * @param user
	 *            the name of the user to delete
	 */
	
	@Modifying
	@Transactional
	void deleteByNombre(String user);
	
	

}