package springbootstarter.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootstarter.entities.User;

public interface UserRepository extends CrudRepository<User,Long> {

    User findUserByUsername(String username);
}
