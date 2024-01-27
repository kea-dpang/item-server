package kea.dpang.item.dto;

import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.SubCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemResponseDto {

    private Long itemId;
    private Long sellerId;
    private String itemName;
    private Category category;
    private SubCategory subCategory;
    private int itemPrice;
    private float averageRating;
    private List<String> reviews;
    private int discountRate;
    private int discountPrice;
    private String description;
    private int stockQuantity;
    private int minStock;
    private int maxStock;
    private String itemImage;
    private List<String> images;
    private Boolean wishlistCheck;

    public ItemResponseDto(Item item) {
        this.itemId = item.getItemId();
        this.sellerId = item.getSellerId();
        this.itemName = item.getItemName();
        this.category = item.getCategory();
        this.subCategory = item.getSubCategory();
        this.itemPrice = item.getItemPrice();
        this.averageRating = item.getAverageRating();
        this.reviews = item.getReviews();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.stockQuantity = item.getStockQuantity();
        this.minStock = item.getMinStock();
        this.maxStock = item.getMaxStock();
        this.description = item.getDescription();
        this.itemImage = item.getItemImage();
        this.images = item.getImages();
        this.wishlistCheck = item.getWishlistCheck();
    }
}

