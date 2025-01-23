package bibimping_be.bibimping_be2.controller;

import bibimping_be.bibimping_be2.dto.Res.MainPageResponseDto;
import bibimping_be.bibimping_be2.dto.Res.MessageResponse;
import bibimping_be.bibimping_be2.repository.UserRepository;
import bibimping_be.bibimping_be2.service.MainPageService;
import bibimping_be.bibimping_be2.service.MypageService;
import bibimping_be.bibimping_be2.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MainPageController {

    private final MainPageService mainPageService;

    public MainPageController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @GetMapping("/alarm")
    public ResponseEntity<Map<String, Object>> getMainPageAlarms(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = null;

        if (session != null) {
            Object idObj = session.getAttribute("id");
            if (idObj instanceof Integer) {
                userId = ((Integer) idObj).longValue(); // Integer -> Long 변환
            } else if (idObj instanceof Long) {
                userId = (Long) idObj;
            } else {
                return ResponseEntity.status(401).body(Map.of(
                        "message", "유효하지 않은 세션 정보입니다.",
                        "response", null
                ));
            }
        }

        List<MainPageResponseDto> response;
        if (userId != null) {
            response = mainPageService.getUserBookmarkAlarms(userId);
        } else {
            response = mainPageService.noneCookieGetAlarms();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("response", response);
        result.put("message", "검색에 성공하였습니다.");

        return ResponseEntity.ok(result);
    }


    @PostMapping("/bookmarks/")
    public ResponseEntity<MessageResponse> updateAlarmLike(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest
    ) {
        // 쿠키에서 사용자 ID 가져오기
        HttpSession session = httpRequest.getSession(false);

        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("세션이 만료되었습니다."));
        }

        Object userIdObj = session.getAttribute("id");
        Long userId;

        if (userIdObj instanceof Integer) {
            userId = ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            userId = (Long) userIdObj;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("유효하지 않은 사용자 ID 타입입니다."));
        }

        // 요청에서 alarmId와 like 값 가져오기
        Object alarmIdObj = request.get("alarmId");
        Object likedObj = request.get("like");

        if (alarmIdObj == null || likedObj == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("alarmId와 liked 값이 필요합니다."));
        }

        Long alarmId;
        Integer liked;

        try {
            alarmId = Long.parseLong(alarmIdObj.toString());
            liked = Integer.parseInt(likedObj.toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("alarmId와 liked 값은 숫자여야 합니다."));
        }

        if (liked != 0 && liked != 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("liked 값은 0 또는 1이어야 합니다."));
        }

        // 서비스 호출
        boolean success = mainPageService.updateBookmarkAlarmLike(userId, alarmId, liked == 1);


        if (success) {
            return ResponseEntity.ok(new MessageResponse("알람 좋아요 상태가 업데이트되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("알람 좋아요 업데이트에 실패했습니다."));
        }
    }



}
