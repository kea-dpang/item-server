package kea.dpang.item.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * BaseErrorResponse는 API 에러 응답의 기본 형식을 정의하는 클래스
 * 모든 API 에러 응답은 이 클래스를 상속받아야 하며, BaseResponse의 속성에 추가로 에러에 대한 상세 정보를 포함한다.
 */
@Getter
@Setter
public class ErrorResponse extends BaseResponse {

    private String error;
    private String path;
    private LocalDateTime timestamp;

    /**
     * @param status HTTP 상태 코드
     * @param message 에러 메시지
     * @param error 발생한 에러의 이름
     * @param path 에러가 발생한 요청 경로
     * @param timestamp 에러 발생 시간
     */
    public ErrorResponse(int status, String message, String error, String path, LocalDateTime timestamp) {
        super(status, message);
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
    }
}
