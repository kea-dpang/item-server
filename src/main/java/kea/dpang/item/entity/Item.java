package kea.dpang.item.entity;

import jakarta.persistence.*;
import kea.dpang.item.base.BaseEntity;
import kea.dpang.item.dto.Item.ItemCreateDto;
import kea.dpang.item.dto.Item.ItemResponseDto;
import kea.dpang.item.dto.Item.ItemUpdateDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item extends BaseEntity {
    // 상품 ID
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    // 상품명
    @Column(name="name", nullable = false)
    private String itemName;

    // 판매처 ID
    @Column(name="seller_id", nullable = false)
    private Long sellerId;

    // 판매처명
    @Column(name="seller_name", nullable = false)
    private String sellerName;

    // 상품 회원 할인가
    @Column(name="price", nullable = false)
    private int itemPrice;

    // 상품 분류 카테고리
    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private Category category;

    // 상품 분류 서브카테고리
    @Column(name="sub_category")
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    // 평균 평점
    private float averageRating;

    // 리뷰 리스트
    @OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL)
    private List<Review> reviews;

    // 할인율
    private int discountRate;

    // 할인가
    private int discountPrice;

    // 재고 수량
    private int stockQuantity;

    // 상품 상세정보
    @Column(length = 1000)
    private String description;

    // 상품 사진
    private String itemImage;

    // 이미지 리스트
    @ElementCollection
    private List<String> images;

    // 위시리스트
    private Boolean wishlistCheck;

    public static Item from(ItemCreateDto dto) {
        return Item.builder()
                .sellerId(dto.getSellerId())
                .sellerName(dto.getSellerName())
                .itemName(dto.getItemName())
                .category(dto.getCategory())
                .subCategory(dto.getSubCategory())
                .itemPrice(dto.getItemPrice())
                .stockQuantity(dto.getStockQuantity())
                .itemImage(dto.getItemImage())
                .images(dto.getImages())
                .build();
    }

    public ItemResponseDto toItemResponseDto(String sellerName){
        return ItemResponseDto.builder()
                .itemId(this.getItemId())
                .itemName(this.getItemName())
                .sellerId(this.getSellerId())
                .sellerName(sellerName)
                .category(this.getCategory())
                .subCategory(this.getSubCategory())
                .itemPrice(this.getItemPrice())
                .averageRating(this.getAverageRating())
                .reviewId(this.getReviews().stream().map((Review::getReviewId)).collect(Collectors.toList()))
                .discountRate(this.getDiscountRate())
                .discountPrice(this.getDiscountPrice())
                .description(this.getDescription())
                .stockQuantity(this.getStockQuantity())
                .itemImage(this.getItemImage())
                .images(this.getImages())
                .wishlistCheck(this.getWishlistCheck())
                .build();
    }

    public void updateInformation(ItemUpdateDto dto) {
        this.itemName = dto.getItemName();
        this.category = dto.getCategory();
        this.subCategory = dto.getSubCategory();
        this.itemPrice = dto.getItemPrice();
        this.discountRate = dto.getDiscountRate();
        this.stockQuantity = dto.getStockQuantity();
        this.itemImage = dto.getItemImage();
        this.images = dto.getImages();
    }

    public void changeStock(int quantity) {
        int newStockQuantity = this.stockQuantity + quantity;
        if (newStockQuantity < 0) {
            throw new IllegalArgumentException("Not enough stock");
        }
        this.stockQuantity = newStockQuantity;
    }
}

