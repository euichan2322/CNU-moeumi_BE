/* 미구현 코드
package bibimping_be.bibimping_be2.service;

import bibimping_be.bibimping_be2.dto.SignUpRes;
import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public SignUpRes login(String id, String password) {
        Optional<User> user = userRepository.findById(id);

        //사용자 존재 여부 판단
    }
}
*/