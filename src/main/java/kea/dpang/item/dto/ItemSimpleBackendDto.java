package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemSimpleBackendDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int discountRate;
    private int discountPrice;
    private String itemImage;

    public ItemSimpleBackendDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.itemImage = item.getItemImage();
    }
}

