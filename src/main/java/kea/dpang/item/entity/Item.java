package kea.dpang.item.entity;

import jakarta.persistence.*;
import kea.dpang.item.base.BaseEntity;
import kea.dpang.item.dto.ItemCreateDto;
import kea.dpang.item.dto.ItemUpdateDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    // 판매처 ID
    @Column(name="seller_id", nullable = false)
    private Long sellerId;

    // 상품명
    @Column(name="name", nullable = false)
    private String itemName;

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

    // 평점
    private float rating;

    // 평균 평점
    private float averageRating;

    // 리뷰 리스트
    @ElementCollection
    private List<String> reviews;

    // 이벤트 할인율
    private int discountRate;

    // 할인가
    private int discountPrice;

    // 재고 수량
    private int stockQuantity;

    // 최소 재고 수량
    private int minStock;

    // 최대 재고 수량
    private int maxStock;

    // 상품 상세정보
    @Column(length = 1000)
    private String description;

    // 상품 사진
    private String itemImage;

    // 상품 썸네일 사진
    private String thumbnailImage;

    // 위시리스트
    private Boolean wishlistCheck;

    // 이미지 리스트
    @ElementCollection
    private List<String> images;

    // 상품 점수
    private Double score;

    public static Item from(ItemCreateDto dto) {
        return Item.builder()
                .sellerId(dto.getSellerId())
                .itemName(dto.getItemName())
                .category(dto.getCategory())
                .subCategory(dto.getSubCategory())
                .itemPrice(dto.getItemPrice())
                .stockQuantity(dto.getStockQuantity())
                .itemImage(dto.getItemImage())
                .images(dto.getImages())
                .build();
    }

    public void updateInformation(ItemUpdateDto dto) {
        this.itemName = dto.getItemName();
        this.category = dto.getCategory();
        this.subCategory = dto.getSubCategory();
        this.itemPrice = dto.getItemPrice();
        this.stockQuantity = dto.getStockQuantity();
        this.itemImage = dto.getItemImage();
        this.images = dto.getImages();
    }

    public void increaseStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void decreaseStock(int quantity) {
        if (this.stockQuantity < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        this.stockQuantity -= quantity;
    }
}

