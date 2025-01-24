package bibimping_be.bibimping_be2.config;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class RestClient {

    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // AI Recommendation API 호출
    public List<Map<String, Object>> getRecommendationData(List<String> titles, List<String> tags) {
        // 요청 데이터 설정
        Map<String, Object> requestData = Map.of(
                "title", titles,
                "tag", tags
        );

        // 요청 URL
        String url = "http://3.34.42.29/ai-recommendation";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // HTTP 엔티티 설정
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestData, headers);

        // REST API 호출
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        // 응답 데이터에서 필요한 "response" 필드를 반환
        return (List<Map<String, Object>>) response.getBody().get("response");
    }
}
