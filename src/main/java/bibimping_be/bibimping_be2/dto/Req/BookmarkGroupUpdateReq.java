package bibimping_be.bibimping_be2.dto.Req;

import bibimping_be.bibimping_be2.dto.Res.BookmarkGroupMessageDto;

import java.util.List;

public class BookmarkGroupUpdateReq {
    private List<BookmarkGroupMessageDto> message;

    // Getter 및 Setter
    public List<BookmarkGroupMessageDto> getMessage() {
        return message;
    }

    public void setMessage(List<BookmarkGroupMessageDto> message) {
        this.message = message;
    }
}
