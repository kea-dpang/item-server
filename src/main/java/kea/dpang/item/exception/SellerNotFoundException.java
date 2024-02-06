package kea.dpang.item.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SellerNotFoundException extends RuntimeException {
    private final Long sellerId;

    public SellerNotFoundException(Long sellerId) {
        super(String.format("판매처를 찾을 수 없음: 판매처 ID - '%s'", sellerId));
        this.sellerId = sellerId;
    }
}
