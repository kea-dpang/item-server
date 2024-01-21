package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;

public class ReadCartItemDto {

    private final String thumbnailImage; // 상품 이미지 URL
    private final String itemName; // 상품 이름
    private final Long discountPrice; // 상품 가격

    public ReadCartItemDto(Item item) {
        this.thumbnailImage = item.getThumbnailImage();
        this.itemName = item.getItemName();
        this.discountPrice = item.getDiscountPrice();
    }
}
