package kea.dpang.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kea.dpang.item.entity.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemDetailDto {

    private Long itemId;
    private String itemName;
    private String category;
    private String subCategory;
    private String brand;
    private Long itemPrice;
    private Double averageRating;
    private List<String> reviews;
    private Long discountRate;
    private Long discountPrice;
    private Long eventPrice;
    private String description;
    private String vendor;
    private String tags;
    private String minStock;
    private String maxStock;
    private String itemImage;
    private List<String> images;
    private Boolean wishlistCheck;

    public ItemDetailDto(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.category = item.getCategory();
        this.subCategory = item.getSubCategory();
        this.brand = item.getBrand();
        this.itemPrice = item.getItemPrice();
        this.averageRating = item.getAverageRating();
        this.reviews = item.getReviews();
        this.discountRate = item.getDiscountRate();
        this.discountPrice = item.getDiscountPrice();
        this.eventPrice = item.getEventPrice();
        this.vendor = item.getVendor();
        this.tags = item.getTags();
        this.minStock = item.getMinStock();
        this.maxStock = item.getMaxStock();
        this.description = item.getDescription();
        this.itemImage = item.getItemImage();
        this.images = item.getImages();
        this.wishlistCheck = item.getWishlistCheck();
    }
}

