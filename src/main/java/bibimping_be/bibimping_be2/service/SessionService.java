package bibimping_be.bibimping_be2.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

import java.util.UUID;

@Service
public class SessionService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public SessionService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //세션 생성
    @Transactional
    public String createSession(String accountId, HttpServletResponse response) {

        String sessionId = UUID.randomUUID().toString();

        // 세션 정보 redis에 저장
        String sessionKey = "accountId:"+ accountId + "SESSION:" + sessionId;
        redisTemplate.opsForValue().set(sessionKey, accountId, 60*60*24, TimeUnit.SECONDS);

        // 세션 ID 쿠키값으로.
        Cookie cookie = new Cookie("SESSIONID", sessionKey);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return sessionKey;
    }

    //세션 조회
    @Transactional
    public boolean sessionExists(String sessionKey) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(sessionKey));
    }

    //세션 삭제
    @Transactional
    public void deleteSession(String sessionKey, HttpServletResponse response) {
        //세션 삭제
        redisTemplate.delete(sessionKey);

        //쿠키 삭제
        Cookie cookie = new Cookie("SESSIONID", null);
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
