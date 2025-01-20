package bibimping_be.bibimping_be2.dto.Res;

// message DTO. + 이걸 어떻게 분리할지 생각하기. DTO가 컨트롤러 요청 응답, 서비스 요청 응답 이렇게 3개에서 4개씩 쌓임.
public class BookmarkGroupMessageDto {
    private String business_group_name;
    private int liked;

    public String getBusiness_group_name() {
        return business_group_name;
    }

    public void setBusiness_group_name(String business_group_name) {
        this.business_group_name = business_group_name;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
    public BookmarkGroupMessageDto(String business_group_name, int liked) {
        this.business_group_name = business_group_name;
        this.liked = liked;
    }
}