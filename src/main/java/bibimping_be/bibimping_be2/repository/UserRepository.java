package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
}
