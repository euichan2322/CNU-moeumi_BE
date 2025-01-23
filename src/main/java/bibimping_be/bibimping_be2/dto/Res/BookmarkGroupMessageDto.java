package bibimping_be.bibimping_be2.dto.Res;

// message DTO. + 이걸 어떻게 분리할지 생각하기. DTO가 컨트롤러 요청 응답, 서비스 요청 응답 이렇게 3개에서 4개씩 쌓임.
public class BookmarkGroupMessageDto {
    private String businessGroupName;
    private int liked;

    public String getBusinessGroupName() {
        return businessGroupName;
    }

    public void setBusinessGroupName(String businessGroupName) {
        this.businessGroupName = businessGroupName;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
    public BookmarkGroupMessageDto(String businessGroupName, int liked) {
        this.businessGroupName = businessGroupName;
        this.liked = liked;
    }
    public BookmarkGroupMessageDto() {}

}