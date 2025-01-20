package bibimping_be.bibimping_be2.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
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
        ResponseCookie responseCookie =
             ResponseCookie.from("SESSIONID", sessionKey)
                     .secure(true)
                     .maxAge(60 * 60 * 24)
                     .path("/")
                     .httpOnly(true)
                     .domain("localhost")
                     .build();


/*
        //set domain없을때는 브라우저에 실리고 응답에서 쿠키를 못 찾았음.
        Cookie cookie = new Cookie("SESSIONID", sessionKey);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost:5500");
        response.addCookie(cookie);
        return sessionKey;*/

        response.addHeader("Set-Cookie", responseCookie.toString());
        return sessionKey;
    }

    //세션 조회
    @Transactional
    public boolean sessionExists(String sessionId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(sessionId));
    }

    //세션 삭제
    @Transactional
    public void deleteSession(String sessionId, HttpServletResponse response) {
        //세션 삭제
        redisTemplate.delete(sessionId);

        //쿠키 삭제
        Cookie cookie = new Cookie("SESSIONID", null);
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    //세션 유효성 검사
    @Transactional
    public String sessionCheck(Cookie[] cookies, HttpServletResponse response) {
        //없으면
        if (cookies == null || cookies.length == 0) {
            return "쿠키가 없습니다";
        }
        String sessionId = null;
        // 쿠키 배열에서 SESSIONID를 찾기
        for (Cookie cookie : cookies) {
            if ("SESSIONID".equals(cookie.getName())) {
                sessionId = cookie.getValue();
                break;
            }
        }

        if (sessionId == null) {
            return "SESSIONID 쿠키가 없습니다";
        }

        if (!sessionExists(sessionId)) {
            deleteSession(sessionId, response);
            return "유효하지 않은 세션입니다. 다시 로그인 해주세요.";
        }

        String accountId = extractAccountId(sessionId);
        if (accountId == null || accountId.isEmpty()) {
            return "accountId를 찾을 수 없습니다.";
        }

        return accountId;
        /*//잘못된 값이면
        String sessionId = cookie[0].getValue();
        // 쿠키를 배열로 받아오면 아래 코드가 에러 발생.
        // String sessionId = cookie.getAttribute("SESSIONID");
        if (!sessionExists(sessionId)) {
            deleteSession(sessionId, response);
            return "유효하지 않은 세션입니다. 다시 로그인 해주세요.";
        }

        //올바르면 사용자 아이디를 리턴, sessionId가 accountId:~~~SESSION:~~~식이라 스플릿 해줘야함.
        String accountId = extractAccountId(sessionId);
        if (accountId == null || accountId.isEmpty()) {
            return "accountId를 찾을 수 없습니다.";
        }

        return accountId;*/
    }
    @Transactional
    public String extractAccountId(String sessionId) {
        String[] parts = sessionId.split("SESSION:");
        if (parts.length > 0) {
            return parts[0].replace("accountId:", "").trim();
        }
        return null;
    }
}
