/*
package bibimping_be.bibimping_be2.entity;

import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash(value = "cookie")
public class Cookie {

    @Id
    private Long id;

    @Column(name = "account_id", nullable = false)
    private String accountId;


    @Indexed
    private String sessoionId;

    @TimeToLive
    private Long expiration;

    //getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessoionId() {
        return sessoionId;
    }

    public void setseSsoionId(String sessoionId) {
        this.sessoionId = sessoionId;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    //기본생성자
    //Lombok의 @NoArgsConstructor(access = AccessLevel.PROTECTED)와 같은 기능?
    protected Cookie() {}

    public Cookie(String accountId, String sessionId, Long expiration) {
        this.sessoionId = sessoionId;
        this.accountId = accountId;
        this.expiration = expiration;
    }


    public static Cookie create(String sessoinId, String accountId, Long expiration) {
        return new Cookie(sessoinId, accountId, expiration);
    }
}
*/
