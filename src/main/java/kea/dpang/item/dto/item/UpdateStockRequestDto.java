package kea.dpang.item.dto.item;

import lombok.Data;

@Data
public class UpdateStockRequestDto {
    private Long itemId; // 변경할 상품의 ID
    private int quantity; // 변경할 수량
}

