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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getAccount_id() {
        return accountId;
    }

    public void setAccount_id(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // 기본 생성자
    public User() {
    }

    // Private 생성자 (Builder에서만 호출 가능)
    private User(UserBuilder builder) {
        this.id = builder.id;
        this.accountId = builder.accountId;
        this.password = builder.password;
    }
    // Static inner Builder class
    public static class UserBuilder {
        private Long id;
        private String accountId;
        private String password;

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
