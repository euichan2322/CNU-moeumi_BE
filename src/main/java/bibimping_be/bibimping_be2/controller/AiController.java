package bibimping_be.bibimping_be2.controller;

import bibimping_be.bibimping_be2.dto.Req.BookmarkGroupUpdateReq;
import bibimping_be.bibimping_be2.dto.Req.RecoomendationReq;
import bibimping_be.bibimping_be2.dto.Res.BookmarkGroupMessageDto;
import bibimping_be.bibimping_be2.dto.Res.RecommendationRes;
import bibimping_be.bibimping_be2.dto.TagDto;
import bibimping_be.bibimping_be2.repository.UserRepository;
import bibimping_be.bibimping_be2.service.MypageService;
import bibimping_be.bibimping_be2.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AiController {
    private final MypageService mypageService;
    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;


    public AiController(
            MypageService mypageService,
            SessionService sessionService,
            UserRepository userRepository,
            ObjectMapper objectMapper) {
        this.mypageService = mypageService;
        this.sessionService = sessionService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }
    @PostMapping("/recommendation")
    public ResponseEntity<RecommendationRes> recommendation(
            @RequestBody RecoomendationReq recoomendationReq,
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RecommendationRes("세션이 만료되었습니다."));
        }

        Object userIdObj = session.getAttribute("id");
        Long userId;

        if (userIdObj instanceof Integer) {
            userId = ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            userId = (Long) userIdObj;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RecommendationRes("유효하지 않은 사용자 ID 타입입니다."));
        }

        List<RecoomendationReq>  = new tagDto(recoomendationReq.getTag())
        List<BookmarkGroupMessageDto> messageList = request.getMessage();
        if (messageList == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RecommendationRes("message 필드가 누락되었습니다."));
        }

        for (BookmarkGroupMessageDto dto : messageList) {
            mypageService.updateBookmarkGroupLike(userId, dto.getBusinessGroupName(), dto.getLiked());
        }

        return ResponseEntity.ok(request);
    }

}
