package kea.dpang.item.dto.item;

import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public ItemResponseDto(Item item, String sellerName) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.sellerId = item.getSellerId();
        this.sellerName = sellerName;
        this.category = item.getCategory();
        this.subCategory = item.getSubCategory();
        this.itemPrice = item.getItemPrice();
        this.averageRating = item.getAverageRating();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.description = item.getDescription();
        this.stockQuantity = item.getStockQuantity();
        this.itemImage = item.getItemImage();
        this.images = item.getImages();
    }
}

