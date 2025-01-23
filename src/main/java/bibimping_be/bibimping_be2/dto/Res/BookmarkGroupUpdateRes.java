package bibimping_be.bibimping_be2.dto.Res;

import bibimping_be.bibimping_be2.dto.AlarmDto;

import java.util.List;

public class BookmarkGroupUpdateRes {
    private List<BookmarkGroupMessageDto> message;

    // Getter 및 Setter
    public List<BookmarkGroupMessageDto> getMessage() {
        return message;
    }

    public void setMessage(List<BookmarkGroupMessageDto> message) {
        this.message = message;
    }

    public BookmarkGroupUpdateRes(List<BookmarkGroupMessageDto> message) {
        this.message = message;
    }
}
