package kea.dpang.item.feign.dto;

import kea.dpang.item.entity.Item;
import lombok.Getter;

@Getter
public class ItemSimpleListDto {

    private final Long itemId; // 상품 ID
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 정가
    private final int discountRate; // 할인율
    private final int discountPrice; // 상품 판매가

    public ItemSimpleListDto(Item item) {
        itemId = item.getItemId();
        name = item.getItemName();
        price = item.getItemPrice();
        discountRate = item.getDiscountRate();
        discountPrice = item.getDiscountPrice();
        image = item.getItemImage();
    }
}

