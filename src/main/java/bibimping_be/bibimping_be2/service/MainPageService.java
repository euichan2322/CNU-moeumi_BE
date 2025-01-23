package bibimping_be.bibimping_be2.service;

import bibimping_be.bibimping_be2.dto.AlarmDto;
import bibimping_be.bibimping_be2.dto.Res.MainPageResponseDto;
import bibimping_be.bibimping_be2.entity.Alarm;
import bibimping_be.bibimping_be2.entity.BookmarkAlarm;
import bibimping_be.bibimping_be2.entity.BusinessGroup;
import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.AlarmRepository;
import bibimping_be.bibimping_be2.repository.BookmarkAlarmRepository;
import bibimping_be.bibimping_be2.repository.BusinessGroupRepository;
import bibimping_be.bibimping_be2.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainPageService {

    private final AlarmRepository alarmRepository;
    private final BusinessGroupRepository businessGroupRepository;
    private final BookmarkAlarmRepository bookmarkAlarmRepository;
    private final UserRepository userRepository;

    @Autowired
    public MainPageService(AlarmRepository alarmRepository,
                           BusinessGroupRepository businessGroupRepository,
                           BookmarkAlarmRepository bookmarkAlarmRepository,
                           UserRepository userRepository) {
        this.alarmRepository = alarmRepository;
        this.businessGroupRepository = businessGroupRepository;
        this.bookmarkAlarmRepository = bookmarkAlarmRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<MainPageResponseDto> noneCookieGetAlarms() {
        List<BusinessGroup> businessGroups = businessGroupRepository.findAll();
        List<MainPageResponseDto> response = new ArrayList<>();

        for (BusinessGroup businessGroup : businessGroups) {
            List<Alarm> alarms = alarmRepository.findTop5ByBusinessGroupId(businessGroup.getId(), PageRequest.of(0, 5));
            List<AlarmDto> alarmDtos = new ArrayList<>();

            for (Alarm alarm : alarms) {
                alarmDtos.add(new AlarmDto(
                        alarm.getId(),
                        alarm.getTitle(),
                        alarm.getLink(),
                        alarm.getAlarmAt(),
                        alarm.getLiked()
                ));
            }

            response.add(new MainPageResponseDto(
                    businessGroup.getId(),
                    businessGroup.getName(),
                    alarmDtos
            ));
        }
        return response;
    }

    @Transactional(readOnly = true)
    public List<MainPageResponseDto> getUserBookmarkAlarms(Long userId) {
        List<BusinessGroup> businessGroups = businessGroupRepository.findAll();
        List<MainPageResponseDto> response = new ArrayList<>();

        for (BusinessGroup businessGroup : businessGroups) {
            // BookmarkAlarm 테이블에서 사용자의 알림과 좋아요 상태 가져오기
            List<BookmarkAlarm> bookmarkAlarms = bookmarkAlarmRepository.findTop5ByUserIdAndAlarm_BusinessGroupId(userId, businessGroup.getId(), PageRequest.of(0, 5));
            List<AlarmDto> alarmDtos = new ArrayList<>();

            // 가져온 BookmarkAlarm과 일치하는 알림들을 AlarmDto로 변환
            for (BookmarkAlarm bookmarkAlarm : bookmarkAlarms) {
                Alarm alarm = bookmarkAlarm.getAlarm(); // 알림 객체 가져오기
                alarmDtos.add(new AlarmDto(
                        alarm.getId(),
                        alarm.getTitle(),
                        alarm.getLink(),
                        alarm.getAlarmAt(),
                        bookmarkAlarm.getLiked() // 사용자가 설정한 liked 값 반환
                ));
            }

            response.add(new MainPageResponseDto(
                    businessGroup.getId(),
                    businessGroup.getName(),
                    alarmDtos
            ));
        }
        return response;
    }


    @Transactional
    public boolean updateBookmarkAlarmLike(Long userId, Long alarmId, boolean liked) {
        // User와 Alarm을 찾아서 설정
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new RuntimeException("알림을 찾을 수 없습니다."));

        // BookmarkAlarm을 찾거나 생성
        Optional<BookmarkAlarm> bookmarkAlarmOpt = bookmarkAlarmRepository.findByUserIdAndAlarmId(userId, alarmId);
        BookmarkAlarm bookmarkAlarm;

        if (bookmarkAlarmOpt.isEmpty()) {
            // 알림이 없다면 새로 생성
            bookmarkAlarm = new BookmarkAlarm();
            bookmarkAlarm.setUser(user);
            bookmarkAlarm.setAlarm(alarm);  // alarm_id를 명시적으로 설정
            bookmarkAlarm.setLiked(liked ? 1 : 0);
            bookmarkAlarmRepository.save(bookmarkAlarm);  // 저장
            return true;
        } else {
            // 알림이 있다면 좋아요 상태 업데이트
            bookmarkAlarm = bookmarkAlarmOpt.get();
            bookmarkAlarm.setLiked(liked ? 1 : 0);
            bookmarkAlarmRepository.save(bookmarkAlarm);  // 업데이트 저장
            return true;
        }
    }


}
