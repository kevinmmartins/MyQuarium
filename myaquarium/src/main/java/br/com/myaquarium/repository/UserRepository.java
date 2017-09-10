package br.com.myaquarium.repository;

import br.com.myaquarium.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByEmail(String email);

	public User findByUser(String user);

}
