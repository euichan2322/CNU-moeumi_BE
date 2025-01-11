package bibimping_be.bibimping_be2.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

@Service
public class SessionService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public SessionService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public String createSession(String accountId, HttpServletResponse response) {

        String sessionId = UUID.randomUUID().toString();

        // 세션 정보 redis에 저장
        String sessionKey = "SESSION:" + sessionId;
        redisTemplate.opsForValue().set(sessionKey, accountId);

        // 세션 ID 쿠키값으로.
        Cookie cookie = new Cookie("SESSIONID", sessionId);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return sessionId;
    }
}
