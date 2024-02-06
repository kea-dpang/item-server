package kea.dpang.item.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateItemDiscountDto {
    List<Long> ItemIds;
    List<Long> sellerIds;
    Long eventId;
    double discountRate;
}
