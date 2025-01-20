package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.BusinessGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class BookmarkGroupJpaRepository {
    @PersistenceContext
    private EntityManager em;

    public BusinessGroup save(BusinessGroup businessGroup) {
        em.persist(businessGroup);
        return businessGroup;
    }

    public BusinessGroup find(Long id) {
        return em.find(BusinessGroup.class, id);
    }
}
