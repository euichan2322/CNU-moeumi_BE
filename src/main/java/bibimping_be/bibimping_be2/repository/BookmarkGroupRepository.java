package bibimping_be.bibimping_be2.repository;

import bibimping_be.bibimping_be2.entity.BookmarkGroup;
import bibimping_be.bibimping_be2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkGroupRepository extends JpaRepository<BookmarkGroup, Long> {
    Optional<BookmarkGroup> findById(Long Id);
    Optional<BookmarkGroup> findByUserId(Long userId);
    List<BookmarkGroup> findAllByUserId(Long userId);
    //에러가 발생해서 카멜케이스 표기법으로 바꿨는데 이거 맞나요...?
    // Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'mypageService' defined in file [C:\Dev\bibimping_be\bibimping_be2\CNU-moeumi_BE\build\classes\java\main\bibimping_be\bibimping_be2\service\MypageService.class]: Unsatisfied dependency expressed through constructor parameter 1: Error creating bean with name 'bookmarkGroupRepository' defined in bibimping_be.bibimping_be2.repository.BookmarkGroupRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Could not create query for public abstract java.util.List bibimping_be.bibimping_be2.repository.BookmarkGroupRepository.findAllByUserId(java.lang.Long); Reason: Failed to create query for method public abstract java.util.List bibimping_be.bibimping_be2.repository.BookmarkGroupRepository.findAllByUserId(java.lang.Long); Cannot compare left expression of type 'bibimping_be.bibimping_be2.entity.User' with right expression of type 'java.lang.Long'
    Optional<BookmarkGroup> findByUserIdAndBusinessGroupId_Name(Long userId, String businessGroupName);


}













