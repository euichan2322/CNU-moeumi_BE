package bibimping_be.bibimping_be2.dto;

public class LoginRes {
    private String message;
    private String cookie;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }


    public LoginRes(String message, String cookie) {
        this.message = message;
        this.cookie = cookie;
    }
}


