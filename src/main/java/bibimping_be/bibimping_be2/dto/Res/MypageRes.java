package bibimping_be.bibimping_be2.dto.Res;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class MypageRes {
    private String accountId;

    @JsonInclude(JsonInclude.Include.NON_NULL) // null일 경우 JSON에서 제외
    private Object message;


    public String getAccountId() {return accountId;}
    public void setAccountId(String accountId) {this.accountId = accountId;}
    public Object getMessage() {return message;}
    public void setMessage(Object message) {this.message = message;}

    public MypageRes(String accountId, Object message) {
        this.accountId = accountId;
        this.message = message;
    }
}