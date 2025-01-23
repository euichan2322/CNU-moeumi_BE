package bibimping_be.bibimping_be2.controller;


import bibimping_be.bibimping_be2.dto.Req.BookmarkGroupUpdateReq;
import bibimping_be.bibimping_be2.dto.Res.BookmarkGroupMessageDto;
import bibimping_be.bibimping_be2.dto.Res.MypageRes;
import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.UserRepository;
import bibimping_be.bibimping_be2.service.MypageService;
import bibimping_be.bibimping_be2.service.SessionService;
import bibimping_be.bibimping_be2.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api")
public class ApiController {
    private final MypageService mypageService;
    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;


    public ApiController(
            MypageService mypageService,
            SessionService sessionService,
            UserRepository userRepository,
            ObjectMapper objectMapper) {
        this.mypageService = mypageService;
        this.sessionService = sessionService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }


    @GetMapping("/mypage")
    public ResponseEntity<MypageRes> getMypage(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키 가져오기
        Cookie[] cookies = request.getCookies();

        // 쿠키가 없으면
        if (cookies == null) {
            MypageRes mypageRes = new MypageRes(null, "쿠키가 없습니다");
            return new ResponseEntity<>(mypageRes, HttpStatus.UNAUTHORIZED);
        }

        String sessionId = null;

        // 쿠키에서 SESSION 찾기
        for (Cookie cookie : cookies) {
            if ("SESSION".equals(cookie.getName())) {
                sessionId = cookie.getValue();
                break;
            }
        }

        // 쿠키에 SESSIONID가 없으면
        if (sessionId == null) {
            MypageRes mypageRes = new MypageRes(null, "로그인 후 이용해 주세요");
            return new ResponseEntity<>(mypageRes, HttpStatus.UNAUTHORIZED);
        }

        // 세션을 확인
        HttpSession session = request.getSession(false); // 기존 세션이 있으면 가져오고 없으면 null 반환



        if (session == null) {
            MypageRes mypageRes = new MypageRes(null, "세션이 없습니다. 다시 로그인 해주세요.");
            return new ResponseEntity<>(mypageRes, HttpStatus.PAYMENT_REQUIRED);
        }

        Object userIdObj = session.getAttribute("id");
        if (userIdObj == null || userIdObj.toString().isEmpty()) {
            throw new IllegalArgumentException("세션에 유효한 userId가 없습니다.");
        }

        Long userId;

        try {
            userId = Long.parseLong(userIdObj.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("세션의 userId 형식이 잘못되었습니다.", e);
        }

        List<BookmarkGroupMessageDto> bookmarkGroups = mypageService.getBookmarkGroup(userId);

        // 사용자 정보 가져오기
        String accountId = userRepository.findById(userId)
                .map(User::getAccountId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));

        return new ResponseEntity<>(new MypageRes(accountId, bookmarkGroups), HttpStatus.OK);



        /*if (session == null || !sessionId.equals(session.getId())) {
            // 세션이 없거나 세션 ID가 맞지 않으면 유효하지 않은 세션
            MypageRes mypageRes = new MypageRes(null, "유효하지 않은 세션입니다. 다시 로그인 해주세요.");
            return new ResponseEntity<>(mypageRes, HttpStatus.PAYMENT_REQUIRED);
        }*/

        // 세션이 유효한 경우, 마이페이지 서비스 호출

        /*String accountId = userRepository.findById(userId)
                .map(User::getAccountId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));


        MypageRes ok = new MypageRes(accountId, message);
        return new ResponseEntity<>(ok, HttpStatus.OK);*/
    }
    /*public ResponseEntity<MypageRes> getMypage(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String sessionResult = sessionService.sessionCheck(cookies, response);
        if (sessionResult == "쿠키가 없습니다") {
            MypageRes mypageRes = new MypageRes(null, sessionResult);
            return new ResponseEntity<>(mypageRes, HttpStatus.UNAUTHORIZED);
        }

        if (sessionResult == "유효하지 않은 세션입니다. 다시 로그인 해주세요.") {
            MypageRes mypageRes = new MypageRes(null, sessionResult);
            return new ResponseEntity<>(mypageRes, HttpStatus.UNAUTHORIZED);
        }

        String message = mypageService.getBookmarkGroup(sessionResult);
        //쿠키 서비스호출해서 유효성 확인 후, 마이페이지 서비스 호출.
        MypageRes ok = new MypageRes(sessionResult, message);
        return new ResponseEntity<>(ok, HttpStatus.OK);
        //리턴값 반환
    }*/
    //sessionResult가 accountId인데 네이밍도 변경하면 이해하기 쉬울거 같고, accountId가 아닌 id를 반환하도록 하면 좋을거 같음.
    //but 시간이 없어 나중으로 미룬이.
    /*@PostMapping("/mypage")
    public ResponseEntity<MypageRes> postMypage(@헤더의 쿠키 + @RequestBody MypageReq mypageReq) {

        Cookie[] cookies = request.getCookies();
        String sessionResult = sessionService.sessionCheck(cookies, response);
        if (sessionResult == "쿠키가 없습니다") {
            MypageRes mypageRes = new MypageRes(null, sessionResult);
            return new ResponseEntity<>(mypageRes, HttpStatus.UNAUTHORIZED);
        }

        if (sessionResult == "유효하지 않은 세션입니다. 다시 로그인 해주세요.") {
            MypageRes mypageRes = new MypageRes(null, sessionResult);
            return new ResponseEntity<>(mypageRes, HttpStatus.UNAUTHORIZED);
        }

        String message = myPageService.postMypage(sessionResult, mypageReq)
        MypageRes ok = new MypageRes(sessionResult, message);
        return new ResponseEntity<>(ok, HttpStatus.OK);
    }*/
    @PostMapping("/mypage")
    public ResponseEntity<Object> updateLikes(
            @RequestBody BookmarkGroupUpdateReq request,
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션이 만료되었습니다.");
        }

        Object userIdObj = session.getAttribute("id");
        Long userId;

        if (userIdObj instanceof Integer) {
            userId = ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            userId = (Long) userIdObj;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 사용자 ID 타입입니다.");
        }

        List<BookmarkGroupMessageDto> messageList = request.getMessage();
        if (messageList == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("message 필드가 누락되었습니다.");
        }

        for (BookmarkGroupMessageDto dto : messageList) {
            mypageService.updateBookmarkGroupLike(userId, dto.getBusinessGroupName(), dto.getLiked());
        }

        return ResponseEntity.ok(request);
    }


}
