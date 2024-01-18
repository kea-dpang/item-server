package kea.dpang.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PopularItemDto {

    private Long itemId;
    private String itemName;
    private Double score;
}
