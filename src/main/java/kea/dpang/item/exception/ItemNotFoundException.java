package kea.dpang.item.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
    private final Long itemId;

    public ItemNotFoundException(Long itemId) {
        super(String.format("회원을 찾을 수 없음: 회원 ID - '%s'", itemId));
        this.itemId = itemId;
    }
}
