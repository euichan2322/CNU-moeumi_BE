package bibimping_be.bibimping_be2.controller;

import bibimping_be.bibimping_be2.dto.Req.BookmarkGroupUpdateReq;
import bibimping_be.bibimping_be2.dto.Req.RecommendationReq;
import bibimping_be.bibimping_be2.dto.Res.BookmarkGroupMessageDto;
import bibimping_be.bibimping_be2.dto.Res.RecommendationRes;
import bibimping_be.bibimping_be2.dto.TagDto;
import bibimping_be.bibimping_be2.repository.UserRepository;
import bibimping_be.bibimping_be2.service.MypageService;
import bibimping_be.bibimping_be2.service.RecommendationService;
import bibimping_be.bibimping_be2.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AiController {
    private final MypageService mypageService;
    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final RecommendationService recommendationService;


    public AiController(
            MypageService mypageService,
            SessionService sessionService,
            UserRepository userRepository,
            ObjectMapper objectMapper,
            RecommendationService recommendationService) {
        this.mypageService = mypageService;
        this.sessionService = sessionService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.recommendationService = recommendationService;
    }
    @PostMapping("/recommendation")
    public ResponseEntity<List<Map<String, Object>>> handleTags(@RequestBody RecommendationReq recommendationReq) {
        // 클라이언트에서 받은 제목과 태그 정보를 가져옴
        List<String> titles = recommendationReq.getTitle();
        List<String> tags = recommendationReq.getTag();

        // 서비스에서 추천 결과를 받아옴
        List<Map<String, Object>> recommendedAlarms = recommendationService.getRecommendations(titles, tags);

        // 추천 결과를 응답으로 반환
        return ResponseEntity.ok(recommendedAlarms);
    }


}