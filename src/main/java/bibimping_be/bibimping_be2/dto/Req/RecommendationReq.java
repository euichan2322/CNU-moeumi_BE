package bibimping_be.bibimping_be2.dto.Req;

import java.util.List;

public class RecommendationReq {

    private List<String> title;  // 제목 리스트
    private List<String> tag;    // 태그 리스트

    // Getter and Setter
    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}
