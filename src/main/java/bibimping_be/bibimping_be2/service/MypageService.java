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
    public String getBookmarkGroup(String accountId) {

        //accountId로 Id 얻기. 근데 세션 서비스를 설계 잘했으면 accountId를 반환하는게 아니라 바로 Id를 반환하게 해서 불필요한 쿼리를 줄일 수 있을거 같음. 리팩토링 하기.
        //Repository에서 Optional 타입으로 선언해서 아래 코드에서도 옵셔널 처리를 해줘야 에러가 안남. but bookmarkservice는 세션서비스에서 요효성 검사가 끝난 accountId를 리턴 받은거라 굳이 필요한가 싶음... 멘토님들께 자문 구하고 리팩토링 하기.
        //https://velog.io/@hksdpr/JAVA-Optional%EC%9D%98-%EC%B6%A9%EA%B2%A9%EC%A0%81%EC%9D%B8-%EC%82%AC%EC%9A%A9%EB%B2%95-map%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%B2%B4%EC%9D%B4%EB%8B%9D
        Long id = userRepository.findByAccountId(accountId)
                .map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + accountId));

        //findAllByuserId로 사용자와 매핑된 사업단 가져옴.
        List<BookmarkGroup> bookmarkGroups = bookmarkGroupRepository.findAllByUserId(id);



        //message 리스트에 위 값들 넣기.
        List<BookmarkGroupMessageDto> message = new ArrayList<>();
        for (BookmarkGroup bookmarkGroup : bookmarkGroups) {
            // businessGroupName과 liked 값은 엔티티에서 가져옴
            String businessGroupName = bookmarkGroup.getBusinessGroupId().getName();
            int liked = bookmarkGroup.getLiked();  // BookmarkGroup에서 직접 가져옴

            // message 형식으로 데이터 추가
            message.add(new BookmarkGroupMessageDto(businessGroupName, liked));
        }

        //List를 Json String으로 변환해서 리턴
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환에 실패했습니다.", e);
        }
    }
/*
    @Transactional
    public void saveBusinessGroups(String accountId, List<BusinessGroup> businessGroups) {
        for (BookmarkGroupRe.BusinessGroup dto : businessGroups) {
            BusinessGroup entity = new BusinessGroup(accountId, dto.getBusinessGroupName(), dto.getLiked());
            repository.save(entity);
        }

    @Transactional
    public String postBookmarkGroup(String accountId, MypageReq mypageReq) {

    String
    /*public MypageRes SetMypage(Cookie cookie) {
        //쿠키 값으로 사용자 조회
        //조회한 사용자의 mypage를 덮어씀.
        //리턴
    }*/
}
