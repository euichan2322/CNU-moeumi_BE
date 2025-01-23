package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.Alarm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public class AlarmJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Alarm save(Alarm alarm) {
        em.persist(alarm);
        return alarm;
    }

    public Alarm find(Long id) {
        return em.find(Alarm.class, id);
    }
}
