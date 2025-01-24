package bibimping_be.bibimping_be2.service;

import bibimping_be.bibimping_be2.config.RestClient;
import bibimping_be.bibimping_be2.entity.BookmarkAlarm;
import bibimping_be.bibimping_be2.repository.BookmarkAlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RestClient restClient;
    private final BookmarkAlarmRepository bookmarkAlarmRepository;


    @Autowired
    public RecommendationService(BookmarkAlarmRepository bookmarkAlarmRepository, RestClient restClient) {
        this.restClient = restClient;
        this.bookmarkAlarmRepository = bookmarkAlarmRepository;
    }

    // 외부 AI 추천 API 호출
    public List<Map<String, Object>> getRecommendations(List<String> titles, List<String> tags) {
        // RestClient를 통해 AI Recommendation API 호출
        return restClient.getRecommendationData(titles, tags);
    }
}
