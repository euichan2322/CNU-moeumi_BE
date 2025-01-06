package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
