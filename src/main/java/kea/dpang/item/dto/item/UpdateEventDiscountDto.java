package kea.dpang.item.dto.item;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class UpdateEventDiscountDto {
    private List<Long> itemIds;
    private Long sellerId;
    private Long eventId;
    private int discountRate;
}


