package bibimping_be.bibimping_be2.service;

import bibimping_be.bibimping_be2.dto.Res.BookmarkGroupMessageDto;
import bibimping_be.bibimping_be2.dto.Res.MypageReq;
import bibimping_be.bibimping_be2.dto.Res.MypageRes;
import bibimping_be.bibimping_be2.entity.BookmarkGroup;
import bibimping_be.bibimping_be2.entity.BusinessGroup;
import bibimping_be.bibimping_be2.entity.User;
import bibimping_be.bibimping_be2.repository.BookmarkGroupRepository;
import bibimping_be.bibimping_be2.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MypageService {

    private UserRepository userRepository;
    private BookmarkGroupRepository bookmarkGroupRepository;
    private final ObjectMapper objectMapper;


    @Autowired
    public MypageService(UserRepository userRepository, BookmarkGroupRepository bookmarkGroupRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bookmarkGroupRepository = bookmarkGroupRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public List<BookmarkGroupMessageDto> getBookmarkGroup(Long id) {

        Long userId = userRepository.findById(id)
                .map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));

        List<BookmarkGroup> bookmarkGroups = bookmarkGroupRepository.findAllByUserId(userId);

        List<BookmarkGroupMessageDto> message = new ArrayList<>();
        for (BookmarkGroup bookmarkGroup : bookmarkGroups) {
            String businessGroupName = bookmarkGroup.getBusinessGroupId().getName();
            int liked = bookmarkGroup.getLiked();

            message.add(new BookmarkGroupMessageDto(businessGroupName, liked));
        }
        return message;
    }
    @Transactional
    public void updateBookmarkGroupLike(Long userId, String businessGroupName, int liked) {
        BookmarkGroup bookmarkGroup = bookmarkGroupRepository.findByUserIdAndBusinessGroupId_Name(userId, businessGroupName)
                .orElseThrow(() -> new IllegalArgumentException("해당 북마크 그룹이 존재하지 않습니다: " + businessGroupName));


        bookmarkGroup.setLiked(liked);
        bookmarkGroupRepository.save(bookmarkGroup);
    }


}
