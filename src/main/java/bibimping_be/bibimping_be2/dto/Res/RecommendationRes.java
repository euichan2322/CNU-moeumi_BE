package bibimping_be.bibimping_be2.dto.Res;

import java.util.List;

public class RecommendationRes {
    private final Object response;

    public RecommendationRes(Object response) {
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }

}
