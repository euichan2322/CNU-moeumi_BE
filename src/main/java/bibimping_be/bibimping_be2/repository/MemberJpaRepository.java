/*비빔핑 프로젝트와 무관한 파일입니다. 디버깅, 테스트 용도를 위해 만들어졌습니다.*/
package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.Member;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
