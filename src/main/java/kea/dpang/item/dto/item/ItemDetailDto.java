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
public class ItemDetailDto {
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

    public ItemDetailDto(Item item, String sellerName) {
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.sellerId = item.getSellerId();
        this.sellerName = sellerName;
        this.category = item.getCategory();
        this.subCategory = item.getSubCategory();
        this.itemPrice = item.getPrice();
        this.averageRating = item.getAverageRating();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.description = item.getDescription();
        this.stockQuantity = item.getStockQuantity();
        this.itemImage = item.getThumbnailImage();
        this.images = item.getInformationImages();
    }
}

