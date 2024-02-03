package kea.dpang.item.dto.Item;

import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Review;
import kea.dpang.item.entity.SubCategory;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Getter
public class ItemResponseDto {

    private Long itemId;
    private String itemName;
    private Long sellerId;
    private String sellerName;
    private Category category;
    private SubCategory subCategory;
    private int itemPrice;
    private float averageRating;
    private int discountRate;
    private int discountPrice;
    private String description;
    private int stockQuantity;
    private String itemImage;
    private List<String> images;

    @Builder
    public ItemResponseDto(Long itemId, String itemName, Long sellerId, String sellerName, Category category, SubCategory subCategory, int itemPrice, float averageRating, List<Long> reviewId, int discountRate, int discountPrice, String description, int stockQuantity, String itemImage, List<String> images, Boolean wishlistCheck) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.category = category;
        this.subCategory = subCategory;
        this.itemPrice = itemPrice;
        this.averageRating = averageRating;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.itemImage = itemImage;
        this.images = images;
    }
}

