package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemSimpleFrontendDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int discountRate;
    private int discountPrice;
    private Boolean wishlistCheck;
    private String itemImage;

    public ItemSimpleFrontendDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.wishlistCheck = item.getWishlistCheck();
        this.itemImage = item.getItemImage();
    }
}

