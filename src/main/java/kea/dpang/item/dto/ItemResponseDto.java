package kea.dpang.item.dto;

import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Review;
import kea.dpang.item.entity.SubCategory;
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
    private List<Long> reviewId;
    private int discountRate;
    private int discountPrice;
    private String description;
    private int stockQuantity;
//    private int minStock;
//    private int maxStock;
    private String itemImage;
    private List<String> images;
    private Boolean wishlistCheck;

    public ItemResponseDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.sellerId = item.getSellerId();
        this.category = item.getCategory();
        this.subCategory = item.getSubCategory();
        this.itemPrice = item.getItemPrice();
        this.averageRating = item.getAverageRating();
        this.reviewId = item.getReviews().stream().map(Review::getReviewId).collect(Collectors.toList());
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.stockQuantity = item.getStockQuantity();
//        this.minStock = item.getMinStock();
//        this.maxStock = item.getMaxStock();
        this.description = item.getDescription();
        this.itemImage = item.getItemImage();
        this.images = item.getImages();
        this.wishlistCheck = item.getWishlistCheck();
    }

    public ItemResponseDto(Item item, String sellerName) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.sellerId = item.getSellerId();
        this.sellerName = sellerName;
        this.category = item.getCategory();
        this.subCategory = item.getSubCategory();
        this.itemPrice = item.getItemPrice();
        this.averageRating = item.getAverageRating();
        this.reviewId = item.getReviews().stream().map(Review::getReviewId).collect(Collectors.toList());
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.stockQuantity = item.getStockQuantity();
//        this.minStock = item.getMinStock();
//        this.maxStock = item.getMaxStock();
        this.description = item.getDescription();
        this.itemImage = item.getItemImage();
        this.images = item.getImages();
        this.wishlistCheck = item.getWishlistCheck();
    }
}

