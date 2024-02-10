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
    private Long id;
    private String name;
    private Long sellerId;
    private String sellerName;
    private Category category;
    private SubCategory subCategory;
    private int price;
    private float averageRating;
    private int reviewCount;
    private int discountRate;
    private int discountPrice;
    private String description;
    private int stockQuantity;
    private String thumbnailImage;
    private List<String> informationImages;

    public ItemDetailDto(Item item, String sellerName) {
        this.id = item.getId();
        this.name = item.getName();
        this.sellerId = item.getSellerId();
        this.sellerName = sellerName;
        this.category = item.getCategory();
        this.subCategory = item.getSubCategory();
        this.price = item.getPrice();
        this.averageRating = item.getAverageRating();
        this.reviewCount = item.getReviewCount();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.description = item.getDescription();
        this.stockQuantity = item.getStockQuantity();
        this.thumbnailImage = item.getThumbnailImage();
        this.informationImages = item.getInformationImages();
    }
}

