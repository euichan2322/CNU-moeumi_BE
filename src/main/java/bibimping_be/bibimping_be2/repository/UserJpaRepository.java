package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.User;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;

@Repository
public class UserJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }
}
