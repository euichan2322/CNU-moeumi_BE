package bibimping_be.bibimping_be2.dto.Req;

import java.util.List;

public class RecoomendationReq {
    private final List<String> tag ;

    public RecoomendationReq(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getTag() {
        return tag;
    }
}
