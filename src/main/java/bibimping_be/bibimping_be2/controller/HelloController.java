/*비빔핑 프로젝트와 무관한 파일입니다. 디버깅, 테스트 용도를 위해 만들어졌습니다.*/
package bibimping_be.bibimping_be2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
