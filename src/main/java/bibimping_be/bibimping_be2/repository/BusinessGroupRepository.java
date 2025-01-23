package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.BusinessGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessGroupRepository extends JpaRepository<BusinessGroup, Long> {

    // 이름으로 BusinessGroup을 찾기
    Optional<BusinessGroup> findByName(String name);
}
