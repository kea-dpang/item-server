package kea.dpang.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemThumbnailDto {

    private Long itemId;
    private String itemName;
    private String brand;
    private Long itemPrice;
    private Long discountPrice;
    private Boolean wishlistCheck;
    private String itemImage;
    private Long numberReview;
}

