package kea.dpang.item.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PopularItemDto {

    private Long itemId;
    private String itemName;
    private Double score;
}