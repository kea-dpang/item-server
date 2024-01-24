package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemThumbnailDto {

    private Long itemId;
    private Long sellerId;
    private String itemName;
    private int itemPrice;
    private int discountRate;
    private int discountPrice;
    private Boolean wishlistCheck;
    private String itemImage;

    public ItemThumbnailDto(Item item) {
        this.itemId = item.getItemId();
        this.sellerId = item.getSellerId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.wishlistCheck = item.getWishlistCheck();
        this.itemImage = item.getItemImage();
    }
}

