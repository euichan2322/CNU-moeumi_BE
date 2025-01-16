package bibimping_be.bibimping_be2.service;

import bibimping_be.bibimping_be2.dto.LoginReq;
import bibimping_be.bibimping_be2.dto.LoginRes;
import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private final SessionService sessionService;

    @Autowired
    public UserService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    @Transactional
    public Optional<User> login(LoginReq loginReq) {
        return userRepository.findByAccountId(
                loginReq.getAccountId()
                //,loginReq.getPassword()
        );
    }

    /*public String loginCheck(LoginReq loginReq) {
        Optional<User> loginUser = userRepository.findByAccountId(loginReq.getAccountId());
        // 사용자 존재 여부
        if (loginUser.isEmpty()) {
            Lo
        }
        // 비밀번호 일치 확인
        if (loginUser.get().getPassword().equals(loginReq.getPassword())) {
            return new LoginRes(longinUser.success);
        } else {
            throw UserWrongPasswordException.USER_WRONG_PASSWORD_EXCEPTION;
        }*/

    /*@Transactional
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    String sessionId = cookie.getValue();

                    sessionService.deleteSession(sessionId, response);
                    return true;
                }
            }
        }
        return false;
    }*/


}
