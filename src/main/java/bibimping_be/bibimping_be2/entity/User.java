package bibimping_be.bibimping_be2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "users")  // 테이블 이름을 'users'로 설정
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String account_id;
    private String password;
}
