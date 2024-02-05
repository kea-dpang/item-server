package kea.dpang.item.dto.item;

import kea.dpang.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long itemId; // 상품 ID
    private String image; // 상품 이미지 URL
    private String name; // 상품 이름
    private int price; // 상품 정가
    private int quantity; // 상품 재고 수량
    private int discountRate; // 할인율
    private int discountPrice; // 상품 판매가

    public ItemDto(Item item) {
        this.itemId = item.getId();
        this.image = item.getThumbnailImage();
        this.name = item.getName();
        this.price = item.getPrice();
        this.quantity = item.getStockQuantity();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
    }
}
