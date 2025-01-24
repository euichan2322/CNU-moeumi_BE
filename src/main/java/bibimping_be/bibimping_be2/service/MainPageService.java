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

        // 각 비즈니스 그룹에 대해 알림을 처리
        for (BusinessGroup businessGroup : businessGroups) {
            // BookmarkAlarm 테이블에서 사용자의 알림과 좋아요 상태 가져오기
            List<BookmarkAlarm> bookmarkAlarms = bookmarkAlarmRepository.findTop5ByUserIdAndAlarm_BusinessGroupId(userId, businessGroup.getId(), PageRequest.of(0, 5));
            List<AlarmDto> alarmDtos = new ArrayList<>();

            // noneCookieGetAlarms에서 가져온 알림을 사용하여 알림 DTO를 구성
            List<AlarmDto> noneCookieAlarms = getNoneCookieAlarmsForBusinessGroup(businessGroup.getId()); // 이 메서드는 noneCookieGetAlarms에서 가져온 데이터를 처리

            // noneCookieAlarms에서 각 알림의 liked 값을 사용자가 설정한 값으로 변경
            for (AlarmDto noneCookieAlarm : noneCookieAlarms) {
                // 해당 알림에 대한 BookmarkAlarm을 찾아서 사용자의 liked 값을 적용
                Optional<BookmarkAlarm> bookmarkAlarm = bookmarkAlarms.stream()
                        .filter(b -> b.getAlarm().getId().equals(noneCookieAlarm.getAlarmId()))
                        .findFirst();

                // 사용자가 설정한 liked 값을 적용 (없으면 기본값 0)
                Integer liked = bookmarkAlarm.map(BookmarkAlarm::getLiked).orElse(0);

                // 알림 DTO에 liked 값 추가
                alarmDtos.add(new AlarmDto(
                        noneCookieAlarm.getAlarmId(),
                        noneCookieAlarm.getTitle(),
                        noneCookieAlarm.getUrl(),
                        noneCookieAlarm.getTimestamp(),
                        liked // 사용자 설정 liked 값
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

    // noneCookieGetAlarms에서 해당 비즈니스 그룹의 알림 리스트를 가져오는 메서드 (예시)
    private List<AlarmDto> getNoneCookieAlarmsForBusinessGroup(Long businessGroupId) {
        // noneCookieGetAlarms에서 해당 비즈니스 그룹에 해당하는 알림을 가져오는 로직 작성
        List<Alarm> alarms = alarmRepository.findTop5ByBusinessGroupId(businessGroupId, PageRequest.of(0, 5));
        List<AlarmDto> alarmDtos = new ArrayList<>();

        for (Alarm alarm : alarms) {
            alarmDtos.add(new AlarmDto(
                    alarm.getId(),
                    alarm.getTitle(),
                    alarm.getLink(),
                    alarm.getAlarmAt(),
                    0 // 기본 liked 값은 0
            ));
        }

        return alarmDtos;
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
