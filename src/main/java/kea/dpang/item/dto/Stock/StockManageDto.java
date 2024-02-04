package kea.dpang.item.dto.Stock;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockManageDto {
    private Long itemId;
    private int stockQuantity;

    @Builder
    public StockManageDto(Long itemId, int stockQuantity) {
        this.itemId = itemId;
        this.stockQuantity = stockQuantity;
    }
}
