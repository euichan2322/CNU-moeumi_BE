package bibimping_be.bibimping_be2.dto.Res;

import java.util.List;

public class MypageRes {
    private String accountId;
    private String message;


    public String getAccountId() {return accountId;}
    public void setAccountId(String accountId) {this.accountId = accountId;}
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    public MypageRes(String accountId, String message) {
        this.accountId = accountId;
        this.message = message;
    }
}