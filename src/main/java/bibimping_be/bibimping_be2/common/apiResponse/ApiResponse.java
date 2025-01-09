/* 구현 실패! 예외 처리 등등은 다음에 하기
package bibimping_be.bibimping_be2.common.apiResponse;

public record ApiResponse<T>(
        @JsonIgnore HttpStatus httpStatus,
        boolean success,
        @Nullable T response,
        @Nullable ExceptionDto error)
{
}
*/