package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;

public class ReadWishlistItemDto {

    private final String thumbnailImage; // 상품 이미지 URL
    private final String itemName; // 상품 이름
    private final int itemPrice; // 상품 원가
    private final int discountRate; // 할인율
    private final int discountPrice; // 상품가

    public ReadWishlistItemDto(Item item) {
        this.thumbnailImage = item.getThumbnailImage();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
    }
}
