package bibimping_be.bibimping_be2.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "users")  // 테이블 이름을 'users'로 설정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;
    private String password;

    //getter and setter
    public Long getId() {
        return id;
    }
    public String getAccountId() {
        return accountId;
    }
    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //Lombok의 @NoArgsConstructor(access = AccessLevel.PROTECTED)와 같은 기능
    protected User() {}

    protected User(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }


    //Lombok의 AllArgsConstructor와 같은 기능은 아닌듯...? id값이 없으니까... 으아ㅏㅏㅏ 헷갈린다. 프로젝트 끝나면 공부하기.
    public static User create(String accountId, String password) {
        return new User(accountId, password);
    }
}
