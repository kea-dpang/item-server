package kea.dpang.item.controller;

import kea.dpang.item.dto.ErrorResponseDto;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.exception.ReviewNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleItemNotFoundException(ItemNotFoundException ex) {
        log.error("상품 정보 조회 실패: 상품 ID - '{}'", ex.getItemId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto("존재하지 않는 상품입니다."));
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleReviewNotFoundException(ReviewNotFoundException ex) {
        log.error("리뷰 정보 조회 실패: 리뷰 ID - '{}'", ex.getReviewId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto("존재하지 않는 리뷰입니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex) {
        log.error("Exception caught: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto("예기치 않은 오류가 발생했습니다."));
    }
}
