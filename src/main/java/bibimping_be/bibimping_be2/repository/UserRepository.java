package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccountId(String accountId);
    Optional<User> findByPassword(String accountId);
    Optional<User> findByAccountIdAndPassword(String accountId, String password);
}

