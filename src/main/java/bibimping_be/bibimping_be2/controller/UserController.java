package bibimping_be.bibimping_be2.controller;

import bibimping_be.bibimping_be2.dto.LoginReq;
import bibimping_be.bibimping_be2.dto.LoginRes;
import bibimping_be.bibimping_be2.dto.SignUpReq;
import bibimping_be.bibimping_be2.dto.SignUpRes;

//import bibimping_be.bibimping_be2.entity.Cookie;
import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.UserRepository;
import bibimping_be.bibimping_be2.service.SessionService;
import bibimping_be.bibimping_be2.service.UserService;
import jakarta.persistence.Access;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


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


    @PostMapping("/register")
    public ResponseEntity<SignUpRes> register(@RequestBody SignUpReq signUpReq) {
        try {

            //생성자가 아닌 팩토리 메서드를 호출해 객체 생성.
            User user = User.create(signUpReq.getAccountId(),signUpReq.getPassword());

            User saveUser = userRepository.save(user);

            SignUpRes successResponse = new SignUpRes("회원가입이 완료되었습니다.");
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            SignUpRes errorResponse = new SignUpRes("회원가입에 실패하였습니다.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq, HttpServletResponse response) {
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


        // 정상 로그인이면 세션 새성
        String sessionId = sessionService.createSession(loginReq.getAccountId(), response);
        LoginRes ok = new LoginRes("로그인 성공", sessionId);
        return new ResponseEntity<>(ok, HttpStatus.OK);

    }

}