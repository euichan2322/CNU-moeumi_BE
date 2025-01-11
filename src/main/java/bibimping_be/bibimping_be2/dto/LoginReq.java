package bibimping_be.bibimping_be2.dto;

public class LoginReq {
    private String accountId;
    private String password;

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        accountId = this.accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = this.password;
    }

    //Lombok의 AllArgsConstructor과 같은 기능
    public LoginReq(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }
}
