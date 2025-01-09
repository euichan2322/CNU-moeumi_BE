package bibimping_be.bibimping_be2.controller;

import bibimping_be.bibimping_be2.dto.SignUpReq;
import bibimping_be.bibimping_be2.dto.SignUpRes;

import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/register")
    public ResponseEntity<SignUpRes> registerUser(@RequestBody SignUpReq signUpReq) {
        User user = new User();
        user.setAccount_id(signUpReq.getAccountId());
        user.setPassword(signUpReq.getPassword());

        User savedUser = userRepository.save(user);
        SignUpRes signUpRes = new SignUpRes(savedUser.getId());
        return new ResponseEntity<>(signUpRes, HttpStatus.CREATED);
    }

    /* 아직 미구현 코드입니다.
    @PostMapping("/login")
    public ApiResponse<SignUpRes> register(@RequestBody SignUpReq registerRequest) {
        String email = SignUpReq.getEmail();
        String password = userService.register(email, password);
        return  //
    }
     */

}