package kea.dpang.item.controller;

import kea.dpang.item.base.ErrorResponse;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.exception.ReviewNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        log.error("상품 정보 조회 실패: 상품 ID - '{}'", ex.getItemId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.name(),
                        request.getDescription(false),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException ex, WebRequest request) {
        log.error("리뷰 정보 조회 실패: 리뷰 ID - '{}'", ex.getReviewId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.name(),
                        request.getDescription(false),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        log.error("Exception caught: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.name(),
                        request.getDescription(false),
                        LocalDateTime.now()
                )
        );
    }
}
