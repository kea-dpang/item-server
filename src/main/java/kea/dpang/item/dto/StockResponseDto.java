package kea.dpang.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class StockResponseDto {
    private Long itemId;
    private int quantity;
}
