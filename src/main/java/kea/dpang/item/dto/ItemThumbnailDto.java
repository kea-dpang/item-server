package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemThumbnailDto {

    private Long itemId;
    private String itemName;
    private String brand;
    private Long itemPrice;
    private Long discountRate;
    private Long discountPrice;
    private Boolean wishlistCheck;
    private String itemImage;

    public ItemThumbnailDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.brand = item.getBrand();
        this.itemPrice = item.getItemPrice();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.wishlistCheck = item.getWishlistCheck();
        this.itemImage = item.getItemImage();
    }
}

