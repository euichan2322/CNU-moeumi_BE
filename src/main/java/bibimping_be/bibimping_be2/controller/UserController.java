package bibimping_be.bibimping_be2.controller;

import bibimping_be.bibimping_be2.dto.*;

//import bibimping_be.bibimping_be2.entity.Cookie;
import bibimping_be.bibimping_be2.dto.Req.BookmarkGroupUpdateReq;
import bibimping_be.bibimping_be2.dto.SignUpReq;
import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.UserRepository;
import bibimping_be.bibimping_be2.service.SessionService;
import bibimping_be.bibimping_be2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

//public class CookieUtil


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final SessionService sessionService;

    public UserController(
            UserRepository userRepository,
            UserService userService,
            SessionService sessionService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.sessionService = sessionService;
    }
    @GetMapping("/debug")
    public String debugEndpoint() {

        return "This is a debug endpoint!";
    }

    @PostMapping("/register")
    public ResponseEntity<SignUpRes> register(@RequestBody SignUpReq signUpReq) {
        try {

            //생성자X 팩토리 메서드로 객체 생성.
            User user = User.create(signUpReq.getAccountId(),signUpReq.getPassword());

            User saveUser = userRepository.save(user);

            SignUpRes successResponse = new SignUpRes("회원가입이 완료되었습니다.");
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            SignUpRes errorResponse = new SignUpRes("회원가입에 실패하였습니다.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Get요청은 json이 아니라 쿼리스트링으로 처리한다아는 사실.
    //query-string ver
    @GetMapping("/id-check")
    public ResponseEntity<IdCheckRes> idCheck(@RequestParam("accountId") String accountId) {
        Optional<User> idCheck = userRepository.findByAccountId(accountId);
        if (idCheck.isEmpty()) {
            IdCheckRes possibleId = new IdCheckRes("사용 가능한 아이디입니다.", true);
            return new ResponseEntity<>(possibleId, HttpStatus.OK);
        }
        IdCheckRes impossibleId = new IdCheckRes("이미 사용중인 아이디입니다.", false);
        return new ResponseEntity<>(impossibleId, HttpStatus.OK);
    }


    //request-rep ver
    /*@GetMapping("/id-check")
    public ResponseEntity<IdCheckRes> idCheck(@RequestBody IdCheckReq idCheckReq) {
        Optional<User> loginUser = userRepository.findByAccountId(idCheckReq.getAccountId());
        if (loginUser.isEmpty()) {
            IdCheckRes possibleId = new IdCheckRes("사용 가능한 아이디입니다.", true);
            return new ResponseEntity<>(possibleId, HttpStatus.OK);
        }
        IdCheckRes impossibleId = new IdCheckRes("이미 사용중인 아이디입니다.", false);
        return new ResponseEntity<>(impossibleId, HttpStatus.OK);
    }*/

    @GetMapping("/iscookie")
    public ResponseEntity<Object> iscookie(
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("쿠키 없음");
        }
        if (session != null) {
            return ResponseEntity.status(HttpStatus.OK).body("쿠키 있음");
        }
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃");
    }
    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq, HttpServletRequest request) {
        Optional<User> loginUser = userService.login(loginReq);

        // 사용자 존재 여부 판단
        if (loginUser.isEmpty()) {
            LoginRes noneId = new LoginRes("없는 아이디입니다.", null);
            return new ResponseEntity<>(noneId, HttpStatus.UNAUTHORIZED);
        }

        User user = loginUser.get();
        // 비밀번호 틀리면
        if (!user.getPassword().equals(loginReq.getPassword())) {
            LoginRes notPassword = new LoginRes("비밀번호가 일치하지 않습니다.", null);
            return new ResponseEntity<>(notPassword, HttpStatus.UNAUTHORIZED);
        }

//        String sessionId = sessionService.createSession(loginReq.getAccountId(), response);
        HttpSession session = request.getSession();
        session.setAttribute("id", user.getId());
        LoginRes ok = new LoginRes("로그인 성공", session.getId());
        return new ResponseEntity<>(ok, HttpStatus.OK);
    }


        // 정상 로그인이면 세션 새성
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 현재 세션 가져오기 (존재하지 않으면 null 반환)
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return ResponseEntity.ok("로그아웃되었습니다.");
    }
}
