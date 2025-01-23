package bibimping_be.bibimping_be2.dto.Req;

import java.util.List;

public class RecoomendationReq {
    private final List<> tag;

    public RecoomendationReq(List<> tag) {
        this.tag = tag;
    }

    public List<> getTag() {
        return tag;
    }
}
