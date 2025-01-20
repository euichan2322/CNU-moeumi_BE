package bibimping_be.bibimping_be2.dto.Res;

public class MypageReq {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MypageReq(String message) {
        this.message = message;
    }
}
