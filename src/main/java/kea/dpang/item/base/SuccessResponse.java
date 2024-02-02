package kea.dpang.item.base;

import lombok.Getter;
import lombok.Setter;

/**
 * SuccessResponse는 API 성공 응답의 기본 형식을 정의하는 클래스
 * 모든 API 성공 응답은 이 클래스를 상속받아야 하며, BaseResponse의 속성에 추가로 성공에 대한 데이터를 포함한다.
 */
@Getter
@Setter
public class SuccessResponse<T> extends BaseResponse {

    private T data;

    /**
     * @param status HTTP 상태 코드
     * @param message 성공 메시지
     * @param data 성공 데이터
     */
    public SuccessResponse(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}

