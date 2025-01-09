package bibimping_be.bibimping_be2.dto;

public class SignUpRes {
    private Long userPk;

    public Long getUserPk() {
        return userPk;
    }

    public void setUserPk(Long userPk) {
        this.userPk = userPk;
    }
    public SignUpRes(Long userPk) {
        this.userPk = userPk;
    }
}