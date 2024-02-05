package kea.dpang.item.dto.item;

import kea.dpang.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemCardDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int discountRate;
    private Boolean wishlistCheck;
    private String itemImage;

    public ItemCardDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.discountRate = item.getDiscountRate();
        this.wishlistCheck = item.getWishlistCheck();
        this.itemImage = item.getItemImage();
    }
}

