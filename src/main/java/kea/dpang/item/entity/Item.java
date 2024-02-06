package kea.dpang.item.entity;

import jakarta.persistence.*;
import kea.dpang.item.base.BaseEntity;
import kea.dpang.item.dto.item.UpdateItemRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 상품명
    @Column(name = "item_name", nullable = false)
    private String name;

    // 판매처 ID
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    // 상품 회원 할인가
    @Column(name = "item_price", nullable = false)
    private int price;

    // 상품 분류 카테고리
    @Column(name = "item_category")
    @Enumerated(EnumType.STRING)
    private Category category;

    // 상품 분류 서브카테고리
    @Column(name = "item_sub_category")
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    // 평균 평점
    private float averageRating;

    // 리뷰 리스트
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
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

    // 상품 썸네일 사진
    private String thumbnailImage;

    // 상품 정보 이미지 리스트
    @ElementCollection
    private List<String> informationImages;

    public void update(UpdateItemRequestDto dto) {
        this.name = dto.getItemName();
        this.category = dto.getCategory();
        this.subCategory = dto.getSubCategory();
        this.price = dto.getItemPrice();
        this.discountRate = dto.getDiscountRate();
        this.stockQuantity = dto.getStockQuantity();
        this.thumbnailImage = dto.getItemImage();
        this.informationImages = dto.getImages();
    }

    public void increaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 양수여야 합니다.");
        }
        this.stockQuantity += quantity;
    }

    public void decreaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 양수여야 합니다.");
        }
        if (this.stockQuantity < quantity) {
            throw new IllegalArgumentException("재고가 충분하지 않습니다.");
        }
        this.stockQuantity -= quantity;
    }

    public void updateAverageRating() {
        this.averageRating = (float) this.reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public int getReviewCount() {
        return this.reviews.size();
    }

}

