package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.BookmarkAlarm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkAlarmRepository extends JpaRepository<BookmarkAlarm, Long> {
    List<BookmarkAlarm> findTop5ByUserIdAndAlarm_BusinessGroupId(Long userId, Long businessGroupId, Pageable pageable);
    Optional<BookmarkAlarm> findByUserIdAndAlarmId(Long userId, Long alarmId);

}
