package bibimping_be.bibimping_be2.dto;

import java.util.ArrayList;
import java.util.List;

public class TagDto {
    private final List<String> tag ;

    public TagDto(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getTag() {
        return tag;
    }
}
