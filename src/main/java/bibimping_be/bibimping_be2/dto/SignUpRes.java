package bibimping_be.bibimping_be2.dto;

public class SignUpRes {
    private String message;

    public String getMessage() {
        return message;
    }

    //setter가 굳이 필요한가??
    public void setMessage(String message) {
        this.message = message;
    }

    public SignUpRes(String message) {
        this.message = message;
    }
}