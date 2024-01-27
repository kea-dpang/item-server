package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class StockResponseDto {
    private Long stockId;
    private Item itemId;
    private int quantity;
}
