package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;



import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Query("SELECT a FROM Alarm a WHERE a.businessGroup.id = :businessGroupId ORDER BY a.alarmAt DESC")
    List<Alarm> findTop5ByBusinessGroupId(@Param("businessGroupId") Long businessGroupId, Pageable pageable);
}