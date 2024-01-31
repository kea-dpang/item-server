package kea.dpang.item.feign.dto;

import kea.dpang.item.entity.Item;

/**
 * 상품 조회를 위한 DTO
 *
 * @property id 조회할 상품의 고유 식별자 ID
 */
public class ItemInquiryDto {
    private Long itemId; // 조회할 상품의 ID
    private String itemName;
    private int itemPrice;
    private int discountRate;
    private int discountPrice;
    private int stockQuantity;
    private String itemImage;

    public ItemInquiryDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.stockQuantity = item.getStockQuantity();
        this.itemImage = item.getItemImage();
    }
}
