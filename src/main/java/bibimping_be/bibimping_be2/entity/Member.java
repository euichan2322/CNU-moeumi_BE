/*비빔핑 프로젝트와 무관한 파일입니다. 디버깅, 테스트 용도를 위해 만들어졌습니다.*/
package bibimping_be.bibimping_be2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "user")
    private Long id;
    private String name;
}
