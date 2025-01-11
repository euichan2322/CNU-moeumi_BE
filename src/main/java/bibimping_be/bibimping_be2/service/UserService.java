package bibimping_be.bibimping_be2.service;

import bibimping_be.bibimping_be2.dto.LoginReq;
import bibimping_be.bibimping_be2.dto.LoginRes;
import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<User> login(LoginReq loginReq) {
        return userRepository.findByAccountIdAndPassword(
                loginReq.getAccountId(),
                loginReq.getPassword()
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

}
