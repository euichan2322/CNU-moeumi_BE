package bibimping_be.bibimping_be2.controller;


import bibimping_be.bibimping_be2.dto.Res.BookmarkGroupMessageDto;
import bibimping_be.bibimping_be2.dto.Res.MypageRes;
import bibimping_be.bibimping_be2.service.MypageService;
import bibimping_be.bibimping_be2.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiController {
    private final MypageService mypageService;
    private final SessionService sessionService;

    public ApiController(
            MypageService mypageService,
            SessionService sessionService) {
        this.mypageService = mypageService;
        this.sessionService = sessionService;
    }


    @GetMapping("/mypage")
    public ResponseEntity<MypageRes> getMypage(HttpServletRequest request, HttpServletResponse response) {
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
    }
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
}
