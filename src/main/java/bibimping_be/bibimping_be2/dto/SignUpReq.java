package bibimping_be.bibimping_be2.dto;


public class SignUpReq {
        private String accountId;
        private String password;

        public String getAccountId() {
                return accountId;
        }

        public void setAccountId(String accountId) {
                this.accountId = accountId;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

}