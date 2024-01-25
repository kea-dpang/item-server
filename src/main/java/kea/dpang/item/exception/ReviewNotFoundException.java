package kea.dpang.item.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {
    private final Long reviewId;

    public ReviewNotFoundException(Long reviewId) {
        super(String.format("상품을 찾을 수 없음: 상품 ID - '%s'", reviewId));
        this.reviewId = reviewId;
    }
}