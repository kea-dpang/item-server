package kea.dpang.item.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * API 응답의 기본 형식을 정의하는 클래스
 * 모든 API 응답은 이 클래스를 상속받아야 하며, 공통적으로 상태 코드와 메시지를 포함한다.
 */

@Getter
@AllArgsConstructor
public class BaseResponse {

    private int status;
    private String message;
}